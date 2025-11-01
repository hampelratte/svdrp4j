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
package org.hampelratte.svdrp;

import org.hampelratte.svdrp.mock.Server;
import org.hampelratte.svdrp.responses.NotImplementedBySVDRP4J;
import org.hampelratte.svdrp.responses.R502;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.Serial;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionReadResponseTest {

    private static Server server;

    @BeforeAll
    static void startMockServer() throws InterruptedException {
        server = new Server();
        server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
        new Thread(server).start();

        // wait for the server
        Thread.sleep(1000); // NOSONAR
    }

    @AfterAll
    static void shutdownServer() throws IOException {
        server.shutdown();
    }

    @ParameterizedTest
    @ValueSource(strings = {"too_short_lines_ll", "too_short_lines_rr", "too_short_lines_rrl"})
    void testTooShortLines(String command) throws IOException {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);

            Response resp = con.send(new Command() {
                @Serial
                private static final long serialVersionUID = 1L;

                @Override
                public String toString() {
                    return getCommand();
                }

                @Override
                public String getCommand() {
                    return command;
                }
            });
            assertEquals("S: 1 Programm\nI: 2 Kanäle\nI: 3 Befehle\n", resp.getMessage());
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
    void testResponseNotImplemented() throws IOException {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);

            Response resp = con.send(new Command() {
                @Serial
                private static final long serialVersionUID = 1L;

                @Override
                public String toString() {
                    return getCommand();
                }

                @Override
                public String getCommand() {
                    return "not_implemented";
                }
            });
            assertInstanceOf(NotImplementedBySVDRP4J.class, resp);
            assertEquals("123 - This response code is not supported by SVDRP4J", resp.toString());
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
    void testCommandNotImplemented() throws IOException {
        Connection con = null;
        try {
            con = new Connection("localhost", 2001, 100);

            Response resp = con.send(new Command() {
                @Serial
                private static final long serialVersionUID = 1L;

                @Override
                public String toString() {
                    return getCommand();
                }

                @Override
                public String getCommand() {
                    return "dfhsdhgjfhgsjdgk";
                }
            });
            assertInstanceOf(R502.class, resp);
            assertEquals("502 - Command not implemented", resp.toString());
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
}
