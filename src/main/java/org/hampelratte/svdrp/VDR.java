/*
 * Copyright (c) Henrik Niehaus
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.hampelratte.svdrp.commands.DELR;
import org.hampelratte.svdrp.commands.DELT;
import org.hampelratte.svdrp.commands.LSTC;
import org.hampelratte.svdrp.commands.LSTE;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.commands.LSTT;
import org.hampelratte.svdrp.commands.MODT;
import org.hampelratte.svdrp.commands.NEWT;
import org.hampelratte.svdrp.commands.QUIT;
import org.hampelratte.svdrp.commands.STAT;
import org.hampelratte.svdrp.parsers.ChannelParser;
import org.hampelratte.svdrp.parsers.EPGParser;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.parsers.RecordingParser;
import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VDR {
    private static transient Logger logger = LoggerFactory.getLogger(VDR.class);

    private final String host;
    private final int port;
    private final int connectTimeout;
    private Connection connection = null;
    // private ConnectionTester tester = null;
    private boolean vdrAvailable = false;

    /**
     * If set, the connection will be kept open for some time, so that consecutive request will be much faster
     */
    public static boolean persistentConnection;

    private static java.util.Timer timer;

    private static long lastTransmissionTime = 0;

    /**
     * The time in ms, the connection will be kept alive after the last request. {@link #persistentConnection} has to be set to true.
     */
    private static final int CONNECTION_KEEP_ALIVE = 15000;

    public VDR(String host, int port, int connectTimeout) {
        this.host = host;
        this.port = port;
        this.connectTimeout = connectTimeout;
        // tester = new ConnectionTester();
        // tester.start();
    }

    public VDR(String host, int port, int connectTimeout, Connection conn) {
        this(host, port, connectTimeout);
        this.connection = conn;
    }

    /**
     * Requests a list of all defined timers.
     *
     * @return A list of all defined timers.
     * @throws UnknownHostException
     * @throws IOException
     */
    public List<Timer> getTimers() throws UnknownHostException, IOException {
        return getTimers(new LSTT());
    }

    /**
     * Request a single timer.
     *
     * @param number
     *            The number of the timer to erturn.
     * @return A Timer object, which represents the timer settings.
     * @throws UnknownHostException
     * @throws IOException
     */
    public Timer getTimer(int number) throws UnknownHostException, IOException {
        List<Timer> timers = getTimers(new LSTT(number));
        if (timers.size() > 0) {
            return timers.get(0);
        } else {
            throw new RuntimeException("Timer " + number + " is not defined");
        }
    }

    private List<Timer> getTimers(LSTT lstt) throws UnknownHostException, IOException {
        List<Timer> timers = null;
        Response res = send(lstt);

        if (res != null) {
            if (res.getCode() == 250) {
                timers = TimerParser.parse(res.getMessage());
            } else if (res != null && (res.getCode() == 550 || res.getCode() == 501)) {
                // no timers defined, return an empty list
                timers = new ArrayList<Timer>();
            } else {
                // something went wrong
                throw new RuntimeException(res.getMessage());
            }
        } else {
            throw new RuntimeException("Response object is null");
        }

        return timers;
    }

    /**
     * Modifies an existing timer.
     *
     * @param number
     *            The number of the timer to modify.
     * @param timer
     *            A Timer, which represents the new timer settings.
     * @return The response from VDR.
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public Response modifyTimer(int number, Timer timer) throws UnknownHostException, IOException {
        return send(new MODT(number, timer));
    }

    /**
     * Deletes an existing timer.
     *
     * @param number
     *            The number of the timer to delete.
     * @return The response from VDR.
     * @throws UnknownHostException
     * @throws IOException
     */
    public Response deleteTimer(int number) throws UnknownHostException, IOException {
        return send(new DELT(number));
    }

    /**
     * Creates a new timer.
     *
     * @param timer
     *            A {@link Timer}, which represents the timer settings.
     * @return The response from VDR.
     * @throws UnknownHostException
     * @throws IOException
     */
    public Response newTimer(Timer timer) throws UnknownHostException, IOException {
        return send(new NEWT(timer));
    }

    /**
     * Requests the list of all channels.
     *
     * @return A list of all channels.
     * @throws UnknownHostException
     * @throws IOException
     * @throws ParseException
     */
    public List<Channel> getChannels() throws UnknownHostException, IOException, ParseException {
        List<Channel> channels = null;
        Response res = send(new LSTC());

        if (res != null) {
            if (res.getCode() == 250) {
                channels = ChannelParser.parse(res.getMessage(), true);
            } else {
                // something went wrong
                throw new RuntimeException(res.getMessage());
            }
        } else {
            throw new RuntimeException("Response object is null");
        }

        return channels;
    }

    /**
     * Requests a list of all recordings.
     *
     * @return A list of all recordings.
     * @throws UnknownHostException
     * @throws IOException
     */
    public List<Recording> getRecordings() throws UnknownHostException, IOException {
        List<Recording> recordings = null;
        Response res = send(new LSTR());

        if (res != null) {
            if (res.getCode() == 250) {
                recordings = RecordingListParser.parse(res.getMessage());
            } else if (res.getCode() == 550) {
                // no recordings, return an empty list
                recordings = new ArrayList<Recording>();
            } else {
                // something went wrong
                throw new RuntimeException(res.getMessage());
            }
        } else {
            throw new RuntimeException("Response object is null");
        }

        return recordings;
    }

    /**
     * This method returns the details of one recording. You first have to obtain a Recording by calling {@link #getRecordings()} and then pass this object as
     * parameter.
     *
     * @param rec
     *            The recording to load the details for.
     * @return The same object with the details loaded.
     * @throws UnknownHostException
     * @throws IOException
     * @throws ParseException
     */
    public Recording getRecordingDetails(Recording rec) throws UnknownHostException, IOException, ParseException {
        Response res = send(new LSTR(rec.getNumber()));
        if (res != null) {
            if (res.getCode() == 215) {
                new RecordingParser().parseRecording(rec, res.getMessage());
            } else {
                // something went wrong
                throw new RuntimeException(res.getMessage());
            }
        } else {
            throw new RuntimeException("Response object is null");
        }

        return rec;
    }

    /**
     * Deletes a recording. You first have to obtain a Recording by calling {@link #getRecordings()} and then pass this object as parameter.
     *
     * @param rec
     *            The recording to delete.
     * @return The response from VDR.
     * @throws UnknownHostException
     * @throws IOException
     */
    public Response deleteRecording(Recording rec) throws UnknownHostException, IOException {
        return send(new DELR(rec.getNumber()));
    }

    /**
     * Get the whole EPG. To get the epg of one channel, call {@link #getEpg(int)} or filter this list manually.
     *
     * @return A list of all epg entries of all channels.
     * @throws UnknownHostException
     * @throws IOException
     */
    public List<EPGEntry> getEpg() throws UnknownHostException, IOException {
        return getEpg(new LSTE());
    }

    /**
     * Request the EPG of a single channel.
     *
     * @param channelNumber
     *            The channel number of the channel.
     * @return A list of all EPG entries available for the given channel.
     * @throws UnknownHostException
     * @throws IOException
     */
    public List<EPGEntry> getEpg(int channelNumber) throws UnknownHostException, IOException {
        return getEpg(new LSTE(channelNumber));
    }

    private List<EPGEntry> getEpg(LSTE lste) throws UnknownHostException, IOException {
        List<EPGEntry> epg = null;
        Response res = send(lste);

        if (res != null) {
            if (res.getCode() == 215) {
                epg = new EPGParser().parse(res.getMessage());
            } else {
                // something went wrong
                throw new RuntimeException(res.getMessage());
            }
        } else {
            throw new RuntimeException("Response object is null");
        }

        return epg;
    }

    private Response send(Command cmd) throws UnknownHostException, IOException {
        Response res = null;

        if (connection == null) {
            logger.trace("New connection");
            connection = new Connection(host, port, connectTimeout);
        } else {
            logger.trace("old connection");
        }

        try {
            res = connection.send(cmd);
        } finally {
            lastTransmissionTime = System.currentTimeMillis();
            if (!persistentConnection) {
                connection.close();
                connection = null;
            } else {
                if (timer == null) {
                    logger.debug("Starting connection closer");
                    timer = new java.util.Timer("SVDRP connection closer");
                    timer.schedule(new ConnectionCloser(), 0, 100);
                }
            }
        }

        return res;
    }

    /**
     * Close the connection to VDR.
     */
    public void close() throws IOException {
        send(new QUIT());
        connection = null;
    }

    class ConnectionCloser extends TimerTask {
        @Override
        public void run() {
            if (connection != null && (System.currentTimeMillis() - lastTransmissionTime) > CONNECTION_KEEP_ALIVE) {
                logger.debug("Closing connection");
                try {
                    connection.close();
                    connection = null;
                    timer.cancel();
                    timer = null;
                } catch (IOException e) {
                    logger.error("Couldn't close connection", e);
                }
            }
        }
    }

    public boolean isAvailable() {
        return vdrAvailable;
    }

    public class ConnectionTester extends Thread {

        private boolean running = false;

        @Override
        public void run() {
            running = true;
            while (running) {
                try {
                    send(new STAT());
                    vdrAvailable = true;
                } catch (UnknownHostException e1) {
                    vdrAvailable = false;
                } catch (IOException e1) {
                    vdrAvailable = false;
                }

                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(30));
                } catch (InterruptedException e) {
                    logger.warn("ConnectionTester interrupted while sleeping. Will stop now!");
                    running = false;
                }
            }
        }

        public boolean isRunning() {
            return running;
        }

        public void stopNow() {
            running = false;
            interrupt();
        }
    }
}
