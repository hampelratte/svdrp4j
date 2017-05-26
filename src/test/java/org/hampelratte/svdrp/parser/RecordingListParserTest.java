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
import java.util.Calendar;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.mock.TestData;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.responses.R250;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.junit.Before;
import org.junit.Test;


public class RecordingListParserTest {

    private List<Recording> recordings;

    @Before
    public void parseRecordings() throws IOException {
        Connection.setVersion(new Version("1.0.0"));
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(new R250(TestData.readFile("unittests/lstr.txt")));

        recordings = RecordingListParser.parse(conn.send(new LSTR()).getMessage());
    }

    @Test
    public void testListSize() {
        assertEquals(7, recordings.size());
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
        assertEquals(1, recordings.get(0).getId());
        assertEquals(2, recordings.get(1).getId());
        assertEquals(3, recordings.get(2).getId());
        assertEquals(4, recordings.get(3).getId());
        assertEquals(5, recordings.get(4).getId());
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

    @Test
    public void testGetFolder() {
        Recording rec6 = recordings.get(5);
        assertEquals("Folder", rec6.getFolder());
        assertTrue(rec6.isCut());
        assertTrue(rec6.isNew());
        assertEquals("Title", rec6.getDisplayTitle());

        Recording rec7 = recordings.get(6);
        assertEquals("Parent/Child", rec7.getFolder());
        assertTrue(rec7.isCut());
        assertFalse(rec7.isNew());
        assertEquals("Title", rec7.getDisplayTitle());
    }
}

