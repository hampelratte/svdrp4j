package org.hampelratte.svdrp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.hampelratte.svdrp.commands.LSTC;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.commands.LSTT;
import org.hampelratte.svdrp.parsers.ChannelParser;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.VDRTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VDR {
    private static transient Logger logger = LoggerFactory.getLogger(VDR.class);

    private final String host;
    private final int port;
    private final int connectTimeout;
    private Connection connection = null;

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
    }

    public List<VDRTimer> getTimers() throws UnknownHostException, IOException {
        List<VDRTimer> timers = null;
        Response res = send(new LSTT());

        if (res != null) {
            if (res.getCode() == 250) {
                timers = TimerParser.parse(res.getMessage());
            } else if (res != null && res.getCode() == 550) {
                // no timers defined, return an empty list
                timers = new ArrayList<VDRTimer>();
            } else {
                // something went wrong
                throw new RuntimeException(res.getMessage());
            }
        } else {
            throw new RuntimeException("Response object is null");
        }

        return timers;
    }

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

    private Response send(Command cmd) throws UnknownHostException, IOException {
        Response res = null;

        if (connection == null) {
            logger.trace("New connection");
            connection = new Connection(host, port, connectTimeout);
        } else {
            logger.trace("old connection");
        }
        logger.debug("-->{}", cmd.getCommand());

        res = connection.send(cmd);
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
        logger.debug("<--{}", res.getMessage());

        return res;
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

    public static void main(String[] args) throws UnknownHostException, IOException, ParseException {
        VDR.persistentConnection = false;
        VDR vdr = new VDR("localhost", 2001, 5000);
        // List<VDRTimer> timers = vdr.getTimers();
        // if(timers.size() > 0) {
        // for (VDRTimer timer : timers) {
        // System.out.println(timer.getID() + " " + timer.getStartTime().getTime() + " " + timer.getTitle());
        // }
        // } else {
        // System.out.println("No timers defined");
        // }
        //
        // List<Channel> channels = vdr.getChannels();
        // if(channels.size() > 0) {
        // for (Channel channel : channels) {
        // System.out.println(channel.getChannelNumber() + " " + channel.getName());
        // }
        // } else {
        // System.out.println("No channels defined");
        // }

        List<Recording> recs = vdr.getRecordings();
        for (Recording rec : recs) {
            System.out.println(rec.getNumber() + " " + rec.getDisplayTitle());
        }
    }
}
