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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.hampelratte.svdrp.mock.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DetectEncodingTest {
    
    private static Server server;
    
    @BeforeClass
    public static void startMockServer() throws IOException, InterruptedException {
        server = new Server();
        new Thread(server).start();
        
        // wait for the server
        Thread.sleep(1000);
    }
    
    @Test
    public void testCharsetDetectionUtf8() {
        Connection con = null;
        try {
            server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
            con = new Connection("localhost", 2001, 100);
            assertEquals("UTF-8", con.getEncoding());
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
    public void testCharsetDetectionLatin1() {
        Connection con = null;
        try {
            server.loadWelcome("welcome-1.6.0_2-iso_8859_1.txt");
            con = new Connection("localhost", 2001, 100);
            assertEquals("ISO-8859-1", con.getEncoding());
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
    public void testCharsetDetectionUnknownCharset() {
        Connection con = null;
        try {
            server.loadWelcome("welcome-1.6.0_2-fantasy.txt");
            con = new Connection("localhost", 2001, 100);
            assertEquals("UTF-8", con.getEncoding());
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
    public void testCharsetSwitch() {
        Connection con = null;
        try {
            server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
            con = new Connection("localhost", 2001, 100, "ISO-8859-1");
            Response resp = con.send(new Command() {
                
                @Override
                public String toString() {
                    return "Charset Test";
                }
                
                @Override
                public String getCommand() {
                    return "test_charset";
                }
            });
            assertEquals("öüäß", resp.getMessage().trim());
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
