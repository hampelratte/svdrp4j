/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
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
package org.hampelratte.svdrp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.commands.PUTE;
import org.hampelratte.svdrp.commands.QUIT;
import org.hampelratte.svdrp.responses.AccessDenied;
import org.hampelratte.svdrp.responses.NotImplementedBySVDRP4J;
import org.hampelratte.svdrp.responses.PluginResponse;
import org.hampelratte.svdrp.responses.R214;
import org.hampelratte.svdrp.responses.R215;
import org.hampelratte.svdrp.responses.R216;
import org.hampelratte.svdrp.responses.R220;
import org.hampelratte.svdrp.responses.R221;
import org.hampelratte.svdrp.responses.R250;
import org.hampelratte.svdrp.responses.R354;
import org.hampelratte.svdrp.responses.R451;
import org.hampelratte.svdrp.responses.R500;
import org.hampelratte.svdrp.responses.R501;
import org.hampelratte.svdrp.responses.R502;
import org.hampelratte.svdrp.responses.R550;
import org.hampelratte.svdrp.responses.R554;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The connection to VDR. I recommend to use only one connection at a time,
 * since VDR just accepts one client.
 * 
 * You may set Connection.DEBUG to true to get debug output on System.out
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class Connection {
    private static transient Logger logger = LoggerFactory.getLogger(Connection.class);
    
    /**
     * The socket used to talk to VDR
     */
    private Socket socket;
    
    /**
     * The BufferedWriter to send commands to VDR
     */
    private BufferedWriter out;

    /**
     * The BufferedReader to read responses from VDR
     */
    private BufferedReader in;

    private static VDRVersion version;

    private String encoding;
    
    /**
     * Creates a new connection to host:port with timeout
     * 
     * @param host
     *            The host name or IP-address of the VDR
     * @param port
     *            The port of the SVDRP-server. Default is 2001
     * @param timeout
     *            The timeout for this connection
     * @throws UnknownHostException
     * @throws IOException
     */
    public Connection(String host, int port, int timeout)
            throws UnknownHostException, IOException {
        this(host, port, timeout, "UTF-8");
    }
    
    /**
     * Creates a new connection to host:port with timeout
     * 
     * @param host
     *            The host name or IP-address of the VDR
     * @param port
     *            The port of the SVDRP-server. Default is 2001
     * @param timeout
     *            The timeout for this connection
     * @param encoding
     *            The charset encoding used to talk to VDR
     * @throws UnknownHostException
     * @throws IOException
     */
    public Connection(String host, int port, int timeout, String encoding) 
        throws UnknownHostException, IOException {
        this.encoding = encoding;
        socket = new Socket();
        InetSocketAddress sa = new InetSocketAddress(host, port);
        socket.connect(sa, timeout);
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), encoding));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), encoding));

        // read the welcome message
        Response res = readResponse();
        if (res.getCode() == 220) {
            String msg = res.getMessage().trim();

            // try to parse the version
            try {
                Pattern pattern = Pattern.compile(".*((?:\\d)+\\.(?:\\d)+\\.(?:\\d)+).*");
                Matcher m = pattern.matcher(msg);
                if (m.matches()) {
                    version = new VDRVersion(m.group(1));
                } else {
                    throw new Exception("No Version String found in welcome message");
                }
            } catch (Exception e) {
                version = new VDRVersion("1.0.0");
            }
            
            // try to parse the encoding
            try {
                int lastSemicolon = msg.lastIndexOf(';');
                if(lastSemicolon > 0) {
                    String lastSegment = msg.substring(lastSemicolon + 1).trim();
                    if(Charset.availableCharsets().containsKey(lastSegment)) {
                        logger.debug("VDR is running with charset {}", lastSegment);
                        this.encoding = lastSegment;
                        if(!this.encoding.equalsIgnoreCase(encoding)) {
                            logger.debug("Connection has been established with encoding {}. Now switching to {}", encoding, this.encoding);
                            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), this.encoding));
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), this.encoding));
                        }
                    }
                }
            } catch (Exception e) { 
                // fail silently 
            }
        } else {
            throw new IOException(res.getMessage());
        }
    }

    /**
     * 
     * @param host
     *            The host name or IP-address of the VDR
     * @param port
     *            The port of the SVDRP-server. Default is 2001
     * @throws UnknownHostException
     * @throws IOException
     */
    public Connection(String host, int port) throws UnknownHostException,
            IOException {
        this(host, port, 500);
    }

    /**
     * Sends a command to VDR and returns the response from VDR.
     * 
     * @param cmd
     *            The {@link Command}, which should be sent to VDR
     * @return A {@link Response} object
     * @throws IOException
     * @see Command
     * @see Response
     */
    public synchronized Response send(Command cmd) throws IOException {
        // send the command
        if(cmd instanceof PUTE) {
            out.write("PUTE"); out.newLine(); out.flush();
            Response res = readResponse();
            if(res.getCode() != 354) {
                return res;
            }
        }
        
        out.write(cmd.getCommand()); out.newLine(); out.flush();
        logger.debug("--> {}", cmd.getCommand());

        // read the response
        Response response = readResponse();
        logger.debug("<-- {} {}", response.getCode(), response.getMessage());

        // return the response
        return response;
    }

    /**
     * Reads the response for a sent command. Used by send()
     * 
     * @return A Response object
     * @see Response
     */
    private Response readResponse() throws IOException {
        Response response = null;
        String line = "";
        StringBuilder msg = new StringBuilder();
        boolean running = true;
        while (running && (line = in.readLine()) != null) {
            char fourthChar = line.charAt(3);
            int code = -1;
            try {
                code = Integer.parseInt(line.substring(0, 3));
                line = line.substring(4);
            } catch (Exception e) {
                code = -1;
            }

            if (fourthChar != '-') {
                running = false;
                msg.append(line);
                msg.append('\n');
                switch (code) {
                case -1:
                    response = new AccessDenied(line);
                    break;
                case 214:
                    response = new R214(msg.toString());
                    break;
                case 215:
                    response = new R215(msg.toString());
                    break;
                case 216:
                    response = new R216(msg.toString());
                    break;
                case 220:
                    response = new R220(msg.toString());
                    break;
                case 221:
                    response = new R221(msg.toString());
                    break;
                case 250:
                    response = new R250(msg.toString());
                    break;
                case 354:
                    response = new R354(msg.toString());
                    break;
                case 451:
                    response = new R451(msg.toString());
                    break;
                case 500:
                    response = new R500(msg.toString());
                    break;
                case 501:
                    response = new R501(msg.toString());
                    break;
                case 502:
                    response = new R502(msg.toString());
                    break;
                case 550:
                    response = new R550(msg.toString());
                    break;
                case 554:
                    response = new R554(msg.toString());
                    break;
                default:
                    response = new NotImplementedBySVDRP4J(code, msg.toString());
                    break;
                }
                
                // special case: plugin responses in the range between 900 and 999
                if(code >= 900 && code <= 999) {
                    response = new PluginResponse(code, msg.toString());
                }
                
                line = "";
                msg = new StringBuilder();
            } else {
                msg.append(line);
                msg.append("\n");
            }
        }
        return response;
    }

    /**
     * Closes the connection to VDR. After closing a connection you have to
     * create a new connection to talk to VDR.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        send(new QUIT());
    }

    /**
     * Returns the version of the VDR we are talking to.
     * 
     * @return The version of the VDR we are talking to.
     */
    public static VDRVersion getVersion() {
        return version;
    }
    
    public String getEncoding() {
        return encoding;
    }
}
