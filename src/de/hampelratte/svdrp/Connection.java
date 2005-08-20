package de.hampelratte.svdrp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import de.hampelratte.svdrp.commands.QUIT;
import de.hampelratte.svdrp.responses.AccessDenied;
import de.hampelratte.svdrp.responses.R214;
import de.hampelratte.svdrp.responses.R215;
import de.hampelratte.svdrp.responses.R220;
import de.hampelratte.svdrp.responses.R221;
import de.hampelratte.svdrp.responses.R250;
import de.hampelratte.svdrp.responses.R354;
import de.hampelratte.svdrp.responses.R451;
import de.hampelratte.svdrp.responses.R500;
import de.hampelratte.svdrp.responses.R501;
import de.hampelratte.svdrp.responses.R502;
import de.hampelratte.svdrp.responses.R550;
import de.hampelratte.svdrp.responses.R554;


/**
 * The connection to VDR. I recommend to use only one connection at a time, 
 * since VDR just accepts one client.
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class Connection {
  	/**
  	 * The socket used to talk to VDR
  	 */
    private Socket socket;
    /**
     * The PrintStream to send commands to VDR
     */
    private PrintStream out;
    /**
     * The BufferedReader to read responses from VDR
     */
    private BufferedReader in;
    

    private static VDRVersion version;
    
    public static boolean DEBUG = false;
    
    /**
     * Creates a new new connection to host:port
     * @param host The host name or IP-address of the VDR
     * @param port The port of the SVDRP-server. Default is 2001
     * @throws UnknownHostException
     * @throws IOException
     */
    public Connection(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // read the welcome message
        Response res = readResponse();
        if(res.getCode() == 220) {
          String msg = res.getMessage();
          int start = msg.indexOf("VideoDiskRecorder") + 17;
          int stop = msg.indexOf(";");
          String vers = msg.substring(start, stop).trim(); 
          version = new VDRVersion(vers);
        } else {
          throw new IOException(res.getMessage());
        }
    }
    
    /**
     * Sends a command to VDR and returns the response from VDR.
     * @param cmd The Command, which should be sent to VDR
     * @return A Response object
     * @throws IOException
     * @see de.hampelratte.svdrp.Command
     * @see de.hampelratte.svdrp.Response
     */
    public synchronized Response send(Command cmd) throws IOException {
        // send the command
        out.println(cmd.getCommand());
        if(DEBUG)
          System.out.println("-->"+cmd.getCommand());
        
        // read the response
        Response response = readResponse();
        if(DEBUG)
          System.out.println("<--"+response.getCode()+" "+response.getMessage());
        
        /*
        // TODO parse the response and create an adequate object
        if(cmd instanceof LSTE) {
          List epgdata = EPGParser.parse(response.getMessage());
          for (Iterator iter = epgdata.iterator(); iter.hasNext();) {
            EPGEntry element = (EPGEntry) iter.next();
            System.out.println(element);
          }
        }*/
        
        // return the response 
        return response;
    }
    
    /**
     * Reads the response for a sent command.
     * Used by send()
     * @return A Response object
     * @see Response
     */
    private Response readResponse() {
        Response response = null;
        try {
            String line = "";
            StringBuffer msg = new StringBuffer();
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
                    msg.append("\n");
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
                    }
                    line = "";
                    msg = new StringBuffer();
                } else {
                    msg.append(line);
                    msg.append("\n");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return response;
    }
    
    /**
     * Closes the connection to VDR.
     * After closing a connection you have to create a new connection to talk to VDR.
     * @throws IOException
     */
    public void close() throws IOException {
        send(new QUIT());
    }
    
    /**
     * Returns the version of the VDR we are talking to.
     * @return The version of the VDR we are talking to.
     */
    public static VDRVersion getVersion() {
      return version;
    }
}
