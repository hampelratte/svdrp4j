package org.hampelratte.svdrp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.hampelratte.svdrp.commands.PUTE;
import org.hampelratte.svdrp.mock.Server;
import org.hampelratte.svdrp.responses.R451;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConnectionTest {
    
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
    public void testVersion() throws IOException {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);
            VDRVersion v = Connection.getVersion();
            assertEquals(1, v.getMajor());
            assertEquals(6, v.getMinor());
            assertEquals(0, v.getRevision());
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
    public void testNoVersionInWelcome() throws IOException {
        Connection con = null;
        try {
            server.loadWelcome("welcome-1.6.0_2-noversion.txt");
            con = new Connection("localhost", 2001, 100);
            VDRVersion v = Connection.getVersion();
            assertEquals(1, v.getMajor());
            assertEquals(0, v.getMinor());
            assertEquals(0, v.getRevision());
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
    
    @Test(expected = SocketTimeoutException.class)
    public void testReadTimeout() throws UnknownHostException, IOException {
        server.setResponseDelay(200);
        try {
            new Connection("localhost", 2001, 100, 100, "UTF-8", false);
        } finally {
            server.setResponseDelay(0);
        }
    }
    
    @Test
    public void testSendPUTE() throws IOException {
        Connection con = null;
        try {
            server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
            con = new Connection("localhost", 2001, 100);
            Response res = con.send(new PUTE("dummy"));
            assertTrue(res instanceof R451);
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
    
    @Test(expected = IOException.class)
    public void testAccessDenied() throws IOException {
        Connection con = null;
        try {
            server.setAccessDenied(true);
            con = new Connection("localhost", 2001, 100);
        } finally {
            server.setAccessDenied(false);
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
