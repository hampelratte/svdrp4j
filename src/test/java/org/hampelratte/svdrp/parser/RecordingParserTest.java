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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.commands.QUIT;
import org.hampelratte.svdrp.mock.Server;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.parsers.RecordingParser;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Stream;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RecordingParserTest {
    
    private static Server server;
    
    private List<Recording> recordings;
    private Recording recording;
    private static Connection conn;
    
    @BeforeClass
    public static void startMockServer() throws IOException, InterruptedException {
        server = new Server();
        server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
        new Thread(server).start();
        
        // wait for the server
        Thread.sleep(1000);
        
        conn = new Connection("localhost", 2001, 5000);
    }
    
    @Before
    public void parseRecording() throws IOException, ParseException {
        recordings = RecordingListParser.parse(conn.send(new LSTR()).getMessage());
        recording = recordings.get(2);
        String recordingData = conn.send(new LSTR(recording.getNumber())).getMessage();
        new RecordingParser().parseRecording(recording, recordingData);
    }
    
    @Test
    public void testTitle() {
        assertEquals("%Aktuelle Stunde", recording.getTitle());
    }
        
    @Test
    public void testDisplayTitle() {
        assertEquals("Aktuelle Stunde", recording.getDisplayTitle());
    }
    
    @Test
    public void testIsNew() {
        assertTrue(recording.isNew());
    }
    
    @Test
    public void testIsCut() {
        assertTrue(recording.isCut());
    }
    
    @Test
    public void testNumber() {
        assertEquals(3, recording.getNumber());
    }
    
    @Test
    public void testDescription() {
        assertEquals("Themen u.a.:\n* Suche nach Mirco wird fortgesetzt\n* Amerikanischer Pastor will Koran verbrennen\n* Aus für staatliches Glücksspiel-Monopol\n* Frau gewürgt und vergewaltigt\n* Glückwunsch Mario Adorf\n* Wetten, wir kriegen's entspannter?\n* NRW kompakt", recording.getDescription());
    }
    
    @Test
    public void testShortText() {
        assertEquals("Moderation: Catherine Vogel und Thomas Heyer", recording.getShortText());
    }
    
    @Test
    public void testStreams() {
        assertEquals(2, recording.getStreams().size());
        assertEquals(2, recording.getAudioStreams().size());
        
        Stream deu = recording.getStreams().get(0);
        assertEquals("deu", deu.getLanguage());
        assertEquals(Stream.CONTENT.MP2A, deu.getContent());
        assertEquals(3, deu.getType());
        assertEquals("N/A", deu.getDescription());
        
        Stream twoch = recording.getStreams().get(1);
        assertEquals("2ch", twoch.getLanguage());
        assertEquals(Stream.CONTENT.MP2A, twoch.getContent());
        assertEquals(3, twoch.getType());
        assertEquals("N/A", twoch.getDescription());
    }
    
    @Test
    public void testVps() {
        assertEquals(1283964600000l, recording.getVpsTime().getTimeInMillis());
    }
    
    @Test
    public void testStarttime() {
        Calendar starttime = recording.getStartTime();
        assertEquals(8, starttime.get(Calendar.DAY_OF_MONTH));
        assertEquals(8, starttime.get(Calendar.MONTH));
        assertEquals(2010, starttime.get(Calendar.YEAR));
        assertEquals(18, starttime.get(Calendar.HOUR_OF_DAY));
        assertEquals(50, starttime.get(Calendar.MINUTE));
    }
    
    @Test
    public void testChannelId() {
        assertEquals("S19.2E-1-1201-28306", recording.getChannelID());
    }
    
    @Test
    public void testChannelName() {
        assertEquals("WDR Bielefeld", recording.getChannelName());
    }
    
    @Test
    public void testEndTime() {
        Calendar end = recording.getEndTime();
        assertEquals(8, end.get(Calendar.DAY_OF_MONTH));
        assertEquals(8, end.get(Calendar.MONTH));
        assertEquals(2010, end.get(Calendar.YEAR));
        assertEquals(19, end.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, end.get(Calendar.MINUTE));
    }
    
    @Test
    public void testTableId() {
        assertEquals(0x4E, recording.getTableID());
    }

    @Test
    public void testVersion() {
        assertEquals(0xFF, recording.getVersion());
    }
    
    @Test
    public void testPriority() {
        assertEquals(50, recording.getPriority());
    }
    
    @Test
    public void testLifetime() {
        assertEquals(99, recording.getLifetime());
    }
    
    @AfterClass 
    public static void shutdownServer() throws IOException, InterruptedException {
        conn.send(new QUIT());
        server.shutdown();
    }
}

