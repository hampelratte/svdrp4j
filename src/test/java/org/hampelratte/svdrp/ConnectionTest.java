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
 * IMPLIED WARRANTIES OF MERDELTTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.hampelratte.svdrp.commands.PUTE;
import org.hampelratte.svdrp.mock.Server;
import org.hampelratte.svdrp.responses.R451;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Connection test disabled for now, because it crashes the build on jenkins")
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
            Version v = Connection.getVersion();
            assertEquals(1, v.getMajor());
            assertEquals(6, v.getMinor());
            assertEquals(0, v.getRevision());
        } finally {
            if (con != null) {
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
            Version v = Connection.getVersion();
            assertEquals(1, v.getMajor());
            assertEquals(0, v.getMinor());
            assertEquals(0, v.getRevision());
        } finally {
            if (con != null) {
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
            if (con != null) {
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
            if (con != null) {
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
