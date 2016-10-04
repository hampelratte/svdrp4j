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
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.parsers.RecordingParser;
import org.hampelratte.svdrp.responses.R215;
import org.hampelratte.svdrp.responses.R250;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Stream;
import org.junit.Before;
import org.junit.Test;


public class RecordingParserTest {

    private List<Recording> recordings;
    private Recording recording;

    @Before
    public void parseRecording() throws IOException, ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Connection.setVersion(new Version("1.0.0"));
        Connection conn = mock(Connection.class);
        //@formatter:off
        when(conn.send(isA(LSTR.class))).thenReturn(
                new R250("1 29.08.10 19:55* Tagesschau\n" +
                        "2 02.03.09 00:38  Der Teufel trägt Prada\n" +
                        "3 08.09.10 18:45X %Aktuelle Stunde\n" +
                        "4 09.03.07 16:12 %Tagesthemen\n" +
                        "5 09.03.07 16:12* %%Zweimal geschnitten\n" +
                        "6 09.03.07 16:12* %Folder~Title\n" +
                        "7 09.03.07 16:12  Parent~Child~%Title")
                );
        //@formatter:on

        recordings = RecordingListParser.parse(conn.send(new LSTR()).getMessage());
        recording = recordings.get(2);

        LSTR lstr2 = new LSTR(recording.getNumber());
        //@formatter:off
        when(conn.send(lstr2)).thenReturn(
                new R215("C S19.2E-1-1201-28306 WDR Bielefeld\n" +
                        "E 54090 1283964600 2400 4E FF\n" +
                        "T Aktuelle Stunde\n" +
                        "S Moderation: Catherine Vogel und Thomas Heyer\n" +
                        "D Themen u.a.:|* Suche nach Mirco wird fortgesetzt|* Amerikanischer Pastor will Koran verbrennen|* Aus für staatliches Glücksspiel-Monopol|* Frau gewürgt und vergewaltigt|* Glückwunsch Mario Adorf|* Wetten, wir kriegen's entspannter?|* NRW kompakt\n" +
                        "X 2 03 deu \n" +
                        "X 2 03 2ch\n" +
                        "P 50\n" +
                        "L 99\n" +
                        "V 1283964600\n" +
                        "@ Themen u.a.:|* Suche nach Mirco wird fortgesetzt|* Amerikanischer Pastor will Koran verbrennen|* Aus für staatliches Glücksspiel-Monopol|* Frau gewürgt und vergewaltigt|* Glückwunsch Mario Adorf|* Wetten, wir kriegen's entspannter?|* NRW kompakt\n" +
                        "End of recording information")
                );
        //@formatter:on

        String recordingData = conn.send(lstr2).getMessage();
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
        assertFalse(recording.isNew());
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
        assertEquals(16, starttime.get(Calendar.HOUR_OF_DAY));
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
        assertEquals(17, end.get(Calendar.HOUR_OF_DAY));
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
}

