package org.hampelratte.svdrp;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.hampelratte.svdrp.mock.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConnectionReadResponseTest {
    
    private static Server server;
    
    @BeforeClass
    public static void startMockServer() throws IOException, InterruptedException {
        server = new Server();
        server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
        new Thread(server).start();
        
        // wait for the server
        Thread.sleep(1000);
    }
    
    @Test
    public void testTooShortLinesLineFeeds() {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);
            
            Response resp = con.send(new Command() {
                @Override
                public String toString() {
                    return getCommand();
                }

                @Override
                public String getCommand() {
                    return "too_short_lines_ll";
                }
            });
            assertEquals("S: 1 Programm\nI: 2 Kanäle\nI: 3 Befehle\n", resp.getMessage());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con != null) {
                try {
                    con.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void testTooShortLinesCarriageReturns() {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);
            
            Response resp = con.send(new Command() {
                @Override
                public String toString() {
                    return getCommand();
                }

                @Override
                public String getCommand() {
                    return "too_short_lines_rr";
                }
            });
            assertEquals("S: 1 Programm\nI: 2 Kanäle\nI: 3 Befehle\n", resp.getMessage());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con != null) {
                try {
                    con.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void testTooShortLinesLineCarriageReturnsLineFeed() {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);
            
            Response resp = con.send(new Command() {
                @Override
                public String toString() {
                    return getCommand();
                }

                @Override
                public String getCommand() {
                    return "too_short_lines_rrl";
                }
            });
            assertEquals("S: 1 Programm\nI: 2 Kanäle\nI: 3 Befehle\n", resp.getMessage());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con != null) {
                try {
                    con.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @AfterClass 
    public static void shutdownServer() throws IOException, InterruptedException {
        server.shutdown();
    }
}
