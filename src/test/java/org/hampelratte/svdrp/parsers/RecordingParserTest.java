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
package org.hampelratte.svdrp.parsers;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.responses.R215;
import org.hampelratte.svdrp.responses.R250;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class RecordingParserTest {

    private Recording recording;

    @BeforeEach
    void parseRecording() throws IOException, ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Connection.setVersion(new Version("1.0.0"));
        Connection conn = mock(Connection.class);
        //@formatter:off
        when(conn.send(isA(LSTR.class))).thenReturn(
                new R250("""
                        1 29.08.10 19:55* Tagesschau
                        2 02.03.09 00:38  Der Teufel trägt Prada
                        3 08.09.10 18:45X %Aktuelle Stunde
                        4 09.03.07 16:12 %Tagesthemen
                        5 09.03.07 16:12* %%Zweimal geschnitten
                        6 09.03.07 16:12* %Folder~Title
                        7 09.03.07 16:12  Parent~Child~%Title""")
                );
        //@formatter:on

        List<Recording> recordings = RecordingListParser.parse(conn.send(new LSTR()).getMessage());
        recording = recordings.get(2);

        LSTR lstr2 = new LSTR(recording.getId());
        //@formatter:off
        when(conn.send(lstr2)).thenReturn(
                new R215("""
                        C S19.2E-1-1201-28306 WDR Bielefeld
                        E 54090 1283964600 2400 4E FF
                        T Aktuelle Stunde
                        S Moderation: Catherine Vogel und Thomas Heyer
                        D Themen u.a.:|* Suche nach Mirco wird fortgesetzt|* Amerikanischer Pastor will Koran verbrennen|* Aus für staatliches Glücksspiel-Monopol|* Frau gewürgt und vergewaltigt|* Glückwunsch Mario Adorf|* Wetten, wir kriegen's entspannter?|* NRW kompakt
                        X 2 03 deu
                        X 2 03 2ch
                        P 50
                        L 99
                        V 1283964600
                        @ Themen u.a.:|* Suche nach Mirco wird fortgesetzt|* Amerikanischer Pastor will Koran verbrennen|* Aus für staatliches Glücksspiel-Monopol|* Frau gewürgt und vergewaltigt|* Glückwunsch Mario Adorf|* Wetten, wir kriegen's entspannter?|* NRW kompakt
                        End of recording information""")
                );
        //@formatter:on

        String recordingData = conn.send(lstr2).getMessage();
        new RecordingParser().parseRecording(recording, recordingData);
    }

    @Test
    void testTitle() {
        assertEquals("%Aktuelle Stunde", recording.getTitle());
    }

    @Test
    void testDisplayTitle() {
        assertEquals("Aktuelle Stunde", recording.getDisplayTitle());
    }

    @Test
    void testIsNew() {
        assertFalse(recording.isNew());
    }

    @Test
    void testIsCut() {
        assertTrue(recording.isCut());
    }

    @Test
    void testNumber() {
        assertEquals(3, recording.getId());
    }

    @Test
    void testDescription() {
        assertEquals("Themen u.a.:\n* Suche nach Mirco wird fortgesetzt\n* Amerikanischer Pastor will Koran verbrennen\n* Aus für staatliches Glücksspiel-Monopol\n* Frau gewürgt und vergewaltigt\n* Glückwunsch Mario Adorf\n* Wetten, wir kriegen's entspannter?\n* NRW kompakt", recording.getDescription());
    }

    @Test
    void testShortText() {
        assertEquals("Moderation: Catherine Vogel und Thomas Heyer", recording.getShortText());
    }

    @Test
    void testStreams() {
        assertEquals(2, recording.getStreams().size());
        assertEquals(2, recording.getAudioStreams().size());

        Stream deu = recording.getStreams().getFirst();
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
    void testVps() {
        assertEquals(1283964600000L, recording.getVpsTime().getTimeInMillis());
    }

    @Test
    void testStarttime() {
        Calendar starttime = recording.getStartTime();
        assertEquals(8, starttime.get(Calendar.DAY_OF_MONTH));
        assertEquals(8, starttime.get(Calendar.MONTH));
        assertEquals(2010, starttime.get(Calendar.YEAR));
        assertEquals(16, starttime.get(Calendar.HOUR_OF_DAY));
        assertEquals(50, starttime.get(Calendar.MINUTE));
    }

    @Test
    void testChannelId() {
        assertEquals("S19.2E-1-1201-28306", recording.getChannelID());
    }

    @Test
    void testChannelName() {
        assertEquals("WDR Bielefeld", recording.getChannelName());
    }

    @Test
    void testEndTime() {
        Calendar end = recording.getEndTime();
        assertEquals(8, end.get(Calendar.DAY_OF_MONTH));
        assertEquals(8, end.get(Calendar.MONTH));
        assertEquals(2010, end.get(Calendar.YEAR));
        assertEquals(17, end.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, end.get(Calendar.MINUTE));
    }

    @Test
    void testTableId() {
        assertEquals(0x4E, recording.getTableID());
    }

    @Test
    void testVersion() {
        assertEquals(0xFF, recording.getVersion());
    }

    @Test
    void testPriority() {
        assertEquals(50, recording.getPriority());
    }

    @Test
    void testLifetime() {
        assertEquals(99, recording.getLifetime());
    }
}

