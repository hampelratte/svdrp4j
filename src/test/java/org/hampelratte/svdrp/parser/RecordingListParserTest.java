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
package org.hampelratte.svdrp.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.commands.QUIT;
import org.hampelratte.svdrp.mock.Server;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RecordingListParserTest {

    private static Server server;

    private List<Recording> recordings;

    private static Connection conn;

    @BeforeClass
    public static void startMockServer() throws IOException, InterruptedException {
        server = new Server();
        server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
        server.loadRecordings("lstr.txt");
        new Thread(server).start();

        // wait for the server
        Thread.sleep(1000);

        conn = new Connection("localhost", 2001, 5000);
    }

    @Before
    public void parseRecordings() throws IOException {
        recordings = RecordingListParser.parse(conn.send(new LSTR()).getMessage());
    }

    @Test
    public void testListSize() {
        assertEquals(5, recordings.size());
    }

    @Test
    public void testTitle() {
        assertEquals("%Tagesthemen", recordings.get(3).getTitle());
        assertEquals("%%Zweimal geschnitten", recordings.get(4).getTitle());
    }

    @Test
    public void testDisplayTitle() {
        assertEquals("Tagesthemen", recordings.get(3).getDisplayTitle());
        assertEquals("Zweimal geschnitten", recordings.get(4).getDisplayTitle());
    }

    @Test
    public void testIsNew() {
        assertTrue(recordings.get(0).isNew());
        assertFalse(recordings.get(1).isNew());
        assertFalse(recordings.get(2).isNew());
        assertTrue(recordings.get(3).isNew());
        assertTrue(recordings.get(4).isNew());
    }

    @Test
    public void testIsCut() {
        assertFalse(recordings.get(0).isCut());
        assertFalse(recordings.get(1).isCut());
        assertTrue(recordings.get(2).isCut());
        assertTrue(recordings.get(3).isCut());
        assertTrue(recordings.get(4).isCut());
    }

    @Test
    public void testGetNumber() {
        assertEquals(1, recordings.get(0).getNumber());
        assertEquals(2, recordings.get(1).getNumber());
        assertEquals(3, recordings.get(2).getNumber());
        assertEquals(4, recordings.get(3).getNumber());
        assertEquals(5, recordings.get(4).getNumber());
    }

    @Test
    public void testGetStarttime() {
        Calendar starttime = recordings.get(0).getStartTime();
        assertEquals(29, starttime.get(Calendar.DAY_OF_MONTH));
        assertEquals(7, starttime.get(Calendar.MONTH));
        assertEquals(2010, starttime.get(Calendar.YEAR));
        assertEquals(19, starttime.get(Calendar.HOUR_OF_DAY));
        assertEquals(55, starttime.get(Calendar.MINUTE));
    }

    @AfterClass
    public static void shutdownServer() throws IOException, InterruptedException {
        conn.send(new QUIT());
        server.shutdown();
    }
}

