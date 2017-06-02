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
 * 3. Neither the name of the project (Lazy Bones) nor the names of its
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
package org.hampelratte.svdrp.mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.Response;
import org.hampelratte.svdrp.responses.R250;
import org.hampelratte.svdrp.responses.R501;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.hampelratte.svdrp.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements Runnable {

    private static transient Logger logger = LoggerFactory.getLogger(Server.class);

    private final int port = 2001;

    private BufferedReader br;

    private BufferedWriter bw;

    private ServerSocket serverSocket;
    private Socket socket;

    private String welcome;
    // private String timers;
    private String channels = "";
    //private String recordings;

    private long responseDelay = 0;
    private boolean accessDenied = false;
    private boolean inPUTEmode = false;

    private List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();
    private TimerManager timerManager;
    private RecordingManager recordingManager;

    public Server() {
        logger.info("Running in {}", System.getProperty("user.dir"));
        timerManager = new TimerManager();
        addRequestHandler(new NewtHandler(timerManager));
        addRequestHandler(new ModtHandler(timerManager));
        addRequestHandler(new DeltHandler(timerManager));

        recordingManager = new RecordingManager();
        addRequestHandler(new DelrHandler(recordingManager, timerManager));
        addRequestHandler(new MovrHandler(recordingManager));
    }

    private void addRequestHandler(RequestHandler handler) {
        requestHandlers.add(handler);
    }

    @Override
    public void run() {
        serveClients();
    }

    public void loadWelcome(String welcomeTextFile) throws IOException {
        welcome = IOUtil.readFile(welcomeTextFile);
    }

    public void loadTimers(String timersFile) throws IOException {
        String timerData = IOUtil.readFile(timersFile);
        timerManager.parseData(timerData);
    }

    public void loadRecordings(String recordingsFile) throws IOException {
        String recordingsData = IOUtil.readFile(recordingsFile);
        recordingManager.parseData(recordingsData);

        Timer timer = timerManager.getTimer(1);
        if(timer != null) {
            Timer newTimer = (Timer) timer.clone();
            newTimer.setID(999);
            newTimer.setState(Timer.ACTIVE | Timer.RECORDING);
            newTimer.setTitle("RunningRecordingTimer");
            timerManager.addTimer(newTimer);
            recordingManager.addRecording(new RunningRecording());
        }
    }

    public void loadChannelsConf(String channelsFile) throws IOException {
        channels = "";
        String channelsConf = IOUtil.readFile(channelsFile);
        StringTokenizer st = new StringTokenizer(channelsConf, "\n");
        int channelNumber = 0;
        while(st.hasMoreElements()) {
            String line = st.nextToken();

            // ignore groups
            if (line.startsWith(":")) {
                continue;
            }

            char delim = st.hasMoreElements() ? '-' : ' ';
            channels += "250" + delim + Integer.toString(++channelNumber) + ' ' + line;
            if(st.hasMoreElements()) {
                channels += '\n';
            }
        }

        if (channels.isEmpty()) {
            channels = "550 No channels defined.";
        }
    }

    private void serveClients() {
        try {
            serverSocket = new ServerSocket(port);
            logger.debug("Listening on port {}", port);
            while (!serverSocket.isClosed()) {
                logger.info("Waiting for connection from client");
                socket = serverSocket.accept();
                socket.setSoTimeout((int) TimeUnit.MINUTES.toMillis(3));
                logger.info("Connection from {}", socket.getRemoteSocketAddress());

                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                logger.debug("Access denied: {}", accessDenied);
                if(accessDenied) {
                    logger.debug("Access denied!");
                    sendResponse("Access denied!");
                    socket.close();
                    return;
                } else {
                    logger.debug("Sending welcome message.");
                    sendWelcomeMessage();
                }

                try {
                    while (!socket.isClosed()) {
                        try {
                            if (!readRequest()) {
                                break;
                            }
                        } catch (SocketTimeoutException e) {
                            logger.error("Read timeout. Closing connection {}", socket.getRemoteSocketAddress());
                            socket.close();
                        }
                    }
                } catch (SocketException e) {
                    if (serverSocket != null && !serverSocket.isClosed()) {
                        logger.error("Error while serving clients", e);
                    }
                } catch (IOException e) {
                    logger.error("Error while serving clients", e);
                }
            }
        } catch (SocketException e) {
            if(serverSocket != null && !serverSocket.isClosed()) {
                logger.error("Error while serving clients", e);
            }
        } catch (IOException e) {
            logger.error("Error while serving clients", e);
        }
    }

    private boolean readRequest() throws IOException {
        if(socket.isClosed()) {
            return false;
        }

        String request = br.readLine();
        if(request == null) {
            logger.info("Connection closed by client");
            return false;
        }

        logger.debug("<-- {}", request);
        request = request.trim();

        if(inPUTEmode) {
            sendResponse("451 Error while processing EPG data");
        }

        if("quit".equalsIgnoreCase(request)) {
            sendResponse("221 vdr closing connection");
            socket.close();
        } else if ("lstc".equalsIgnoreCase(request)) {
            printChannelList();
        } else if ("lstt".equalsIgnoreCase(request)) {
            sendResponse(timerManager.printTimersList());
        } else if ("lstr".equalsIgnoreCase(request)) {
            sendResponse(recordingManager.printRecordingsList());
        } else if ("pute".equalsIgnoreCase(request)) {
            inPUTEmode = true;
            sendResponse("354 Enter EPG data, end with \".\" on a line by itself");
        } else if (request.toLowerCase().matches("lstr \\d+.*")) {
            Matcher m = Pattern.compile("lstr (\\d+).*").matcher(request.toLowerCase());
            if(m.matches()) {
                printRecording(Integer.parseInt(m.group(1)));
            }
        } else if (request.toLowerCase().matches("lste \\d+.*")) {
            printEpg();
        } else if ("test_charset".equalsIgnoreCase(request)) {
            sendResponse("221 öüäß");
        } else if ("too_short_lines_ll".equalsIgnoreCase(request)) {
            sendResponse("920-S: 1 Programm\n\n920-I: 2 Kanäle\n\n920 I: 3 Befehle\n\n");
        } else if ("too_short_lines_rr".equalsIgnoreCase(request)) {
            sendResponse("920-S: 1 Programm\r\r920-I: 2 Kanäle\r\r920 I: 3 Befehle\r\r");
        } else if ("too_short_lines_rrl".equalsIgnoreCase(request)) {
            sendResponse("920-S: 1 Programm\r\r\n920-I: 2 Kanäle\r\r\n920 I: 3 Befehle\r\r\n");
        } else if ("not_implemented".equalsIgnoreCase(request)) {
            sendResponse("123 Kuddelmuddel");
        } else if ("stat disk".equalsIgnoreCase(request)) {
            sendResponse("250 1855618MB 393490MB 78%");
        } else if (request.matches("[Ll][Ss][Tt][Tt] (.*)")) {
            Matcher m = Pattern.compile("[Ll][Ss][Tt][Tt] (.*)").matcher(request);
            if (m.matches()) {
                try {
                    int id = Integer.parseInt(m.group(1));
                    Timer timer = timerManager.getTimer(id);
                    if (timer != null) {
                        sendResponse(new R250(id + " " + timer.toNEWT()));
                    } else {
                        sendResponse(new R501("Timer \"" + id + "\" not defined"));
                    }
                } catch (NumberFormatException e) {
                    sendResponse(new R501("Given argument is not a number"));
                }
            }
        } else {
            boolean hasBeenHandled = false;
            for (RequestHandler handler : requestHandlers) {
                if (handler.accept(request)) {
                    hasBeenHandled = true;
                    sendResponse(handler.process(request));
                }
            }

            if (!hasBeenHandled) {
                sendResponse("502 Not implemented");
            }
        }

        return true;
    }

    private void printEpg() throws IOException {
        //String lste = readFile("lste_1.txt");
        String lste = IOUtil.readFile("lste_empty.txt");
        sendResponse(lste);
    }

    private void sendResponse(String resp) throws IOException {
        if(responseDelay > 0) { try { Thread.sleep(responseDelay); } catch (InterruptedException e) { /* fail silently */ } }
        logger.debug("--> {}", resp);
        bw.write(resp);
        bw.write('\n');
        bw.flush();
    }

    private void sendResponse(Response resp) throws IOException {
        if(responseDelay > 0) { try { Thread.sleep(responseDelay); } catch (InterruptedException e) { /* fail silently */ } }
        logger.debug("--> {}", resp);
        bw.write(resp.getCode() + " " + resp.getMessage());
        bw.write('\n');
        bw.flush();
    }

    private void printRecording(int i) throws IOException {
        String filename = "lstr_" + i + ".txt";
        String lstr = "";
        try {
            lstr = IOUtil.readFile(filename);
            sendResponse(lstr);
        } catch (Exception e) {
            logger.warn("Recordings file {} does not exist", filename);
            sendResponse("550 Recording " + i + " not found");
        }
    }

    private void printChannelList() throws IOException {
        sendResponse(channels);
    }

    private void sendWelcomeMessage() throws IOException {
        sendResponse(welcome);
    }

    public void setResponseDelay(long responseDelay) {
        this.responseDelay = responseDelay;
    }

    public void setAccessDenied(boolean accessDenied) {
        this.accessDenied = accessDenied;
    }

    public void shutdown() throws IOException, InterruptedException {
        logger.info("Shutting down server...");
        if(serverSocket!= null) {
            serverSocket.close();
        }
        if(socket != null) {
            socket.close();
        }
        logger.info("Shutdown successful.");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        // server.responseDelay = 2000;
        server.loadWelcome("welcome-2.3.4-utf_8.txt");
        server.setAccessDenied(false);
        server.loadTimers("lstt.txt");
        server.loadRecordings("lstr_data.txt");
        server.loadChannelsConf("channels-mixed.conf");
        new Thread(server).start();
    }
}