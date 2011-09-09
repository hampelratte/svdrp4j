package org.hampelratte.svdrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;

import org.hampelratte.svdrp.mock.Server;
import org.hampelratte.svdrp.responses.NotImplementedBySVDRP4J;
import org.hampelratte.svdrp.responses.R502;
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
    public void testTooShortLinesLineFeeds() throws UnknownHostException, IOException {
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
    public void testTooShortLinesCarriageReturns() throws UnknownHostException, IOException {
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
    public void testTooShortLinesLineCarriageReturnsLineFeed() throws UnknownHostException, IOException {
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
    public void testResponseNotImplemented() throws UnknownHostException, IOException {
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
                    return "not_implemented";
                }
            });
            assertTrue(resp instanceof NotImplementedBySVDRP4J);
            assertEquals("123 - This response code is not supported by SVDRP4J", resp.toString());
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
    public void testCommandNotImplemented() throws UnknownHostException, IOException {
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
                    return "dfhsdhgjfhgsjdgk";
                }
            });
            assertTrue(resp instanceof R502);
            assertEquals("502 - Command not implemented", resp.toString());
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
