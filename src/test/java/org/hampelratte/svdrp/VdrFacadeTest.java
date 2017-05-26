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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.hampelratte.svdrp.commands.DELR;
import org.hampelratte.svdrp.commands.DELT;
import org.hampelratte.svdrp.commands.LSTC;
import org.hampelratte.svdrp.commands.LSTE;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.commands.LSTT;
import org.hampelratte.svdrp.commands.MODT;
import org.hampelratte.svdrp.commands.NEWT;
import org.hampelratte.svdrp.commands.QUIT;
import org.hampelratte.svdrp.responses.AccessDenied;
import org.hampelratte.svdrp.responses.R215;
import org.hampelratte.svdrp.responses.R221;
import org.hampelratte.svdrp.responses.R250;
import org.hampelratte.svdrp.responses.R451;
import org.hampelratte.svdrp.responses.R501;
import org.hampelratte.svdrp.responses.R550;
import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.hampelratte.svdrp.responses.highlevel.Genre;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Stream;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.hampelratte.svdrp.util.IOUtil;
import org.junit.BeforeClass;
import org.junit.Test;



public class VdrFacadeTest {

    private static VDR vdr;

    @BeforeClass
    public static void setProtocolVersion() {
        Connection.setVersion(new Version("1.7.0"));
    }

    @Test
    public void testGetTimers() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R250(IOUtil.readFile("unittests/lstt.txt")));
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<Timer> timers = vdr.getTimers();
        assertEquals(4, timers.size());

        conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R550("No timers defined"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R501("Syntax error in arguments"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        timers = vdr.getTimers();
        assertEquals(0, timers.size());
    }

    @Test(expected = RuntimeException.class)
    public void testGetTimersException() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R451("Fake error"));
        vdr = new VDR("localhost", 2001, 5000, conn);
        vdr.getTimers();
    }

    @Test(expected = RuntimeException.class)
    public void testGetTimersError() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(null);
        vdr = new VDR("localhost", 2001, 5000, conn);
        vdr.getTimers();
    }

    @Test
    public void testGetTimer() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R250(IOUtil.readFile("unittests/lstt.txt")));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Timer timer = vdr.getTimer(1);
        assertEquals("Chronik einer Entmietüng", timer.getTitle());
    }

    @Test(expected = RuntimeException.class)
    public void testGetTimerException() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R550("No timers defined"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        vdr.getTimer(1);
    }

    @Test
    public void testNewTimer() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(NEWT.class))).thenReturn(new R250("Timer has been created"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Timer timer = new Timer();
        Response response = vdr.newTimer(timer);
        assertEquals(250, response.getCode());
    }

    @Test
    public void testModifyTimer() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTT.class))).thenReturn(new R250(IOUtil.readFile("unittests/lstt.txt")));
        when(conn.send(isA(MODT.class))).thenReturn(new R250("Timer has been changed"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Timer timer = vdr.getTimer(1);
        timer.setTitle("ABCDEF");
        vdr = new VDR("localhost", 2001, 5000, conn);
        Response response = vdr.modifyTimer(timer.getID(), timer);
        assertEquals(250, response.getCode());
    }

    @Test
    public void testDeleteTimer() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(DELT.class))).thenReturn(new R250("Timer deleted"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Response response = vdr.deleteTimer(1);
        assertEquals(250, response.getCode());
    }

    @Test
    public void testGetRecordings() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(new R250(IOUtil.readFile("unittests/lstr.txt")));
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<Recording> recordings = vdr.getRecordings();
        assertEquals(7, recordings.size());

        conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(new R550("No recordings available"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        recordings = vdr.getRecordings();
        assertEquals(0, recordings.size());
    }


    @Test(expected = RuntimeException.class)
    public void testGetRecordingException() throws IOException, ParseException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(new AccessDenied("You are not allowed"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        vdr.getRecordings();
    }

    @Test(expected = RuntimeException.class)
    public void testGetRecordingError() throws IOException, ParseException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(null);
        vdr = new VDR("localhost", 2001, 5000, conn);

        vdr.getRecordings();
    }

    @Test
    public void testGetRecordingDetails() throws UnknownHostException, IOException, ParseException {
        //@formatter:off
        String recordingData =
            "C S19.2E-1-1101-28106 Das Erste\n" +
            "E 30780 1283104800 900 4E FF\n" +
            "T Test title\n" +
            "D Test description\n" +
            "P 23\n" +
            "L 24\n" +
            "X 2 03 deu\n" +
            "X 2 03 2ch\n" +
            "X 2 05 deu\n" +
            "V 1283104800\n" +
            "End of recording information";
        //@formatter:on

        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(new R215(recordingData));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Recording rec = createRecording();
        rec = vdr.getRecordingDetails(rec);
        assertEquals("Test title", rec.getTitle());
        assertEquals("Test description", rec.getDescription());
        assertEquals(23, rec.getPriority());
        assertEquals(24, rec.getLifetime());

        Calendar vpsTime = Calendar.getInstance();
        vpsTime.setTimeInMillis(1283104800000L);
        assertEquals(vpsTime, rec.getVpsTime());

        assertEquals(3, rec.getStreams().size());
        assertEquals(3, rec.getAudioStreams().size());
    }

    private Recording createRecording() {
        Recording rec = new Recording();
        rec.setChannelID("");
        rec.setChannelName("");
        rec.setDescription("Test description");
        rec.setDisplayTitle("");
        rec.setDuration(0);
        rec.setEndTime(0);
        rec.setEventID(0);
        rec.setGenres(Collections.<Genre> emptyList());
        rec.setLifetime(0);
        rec.setNew(true);
        rec.setId(1);
        rec.setPriority(0);
        rec.setShortText("");
        rec.setStartTime(0);
        rec.setStreams(Collections.<Stream> emptyList());
        rec.setTableID(0);
        rec.setTitle("Test title");
        rec.setVersion(0);
        rec.setVpsTime(0);
        return rec;
    }

    @Test(expected = RuntimeException.class)
    public void testGetRecordingDetailsException() throws UnknownHostException, IOException, ParseException {
        int fakeRecordingNumber = 123456;
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(new R550("Recording " + fakeRecordingNumber + " not found"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Recording fake = new Recording();
        fake.setId(fakeRecordingNumber);
        vdr.getRecordingDetails(fake);
    }

    @Test(expected = RuntimeException.class)
    public void testGetRecordingDetailsError() throws UnknownHostException, IOException, ParseException {
        int fakeRecordingNumber = 123456;
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTR.class))).thenReturn(null);
        vdr = new VDR("localhost", 2001, 5000, conn);

        Recording fake = new Recording();
        fake.setId(fakeRecordingNumber);
        vdr.getRecordingDetails(fake);
    }

    @Test
    public void testDeleteRecording() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(DELR.class))).thenReturn(new R250("Recording deleted"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        Recording fakeRecording = createRecording();
        Response response = vdr.deleteRecording(fakeRecording);
        assertEquals(250, response.getCode());
    }

    @Test
    public void testGetEpg() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTE.class))).thenReturn(new R215(IOUtil.readFile("unittests/lste.txt")));
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<EPGEntry> epg = vdr.getEpg();
        assertEquals(18557, epg.size());
    }

    @Test
    public void testGetEpgForChannel() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTE.class))).thenReturn(new R215(IOUtil.readFile("unittests/lste.txt")));
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<EPGEntry> epg = vdr.getEpg(1);
        assertEquals(18557, epg.size());
    }

    @Test(expected = RuntimeException.class)
    public void testGetEpgForChannelException() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTE.class))).thenReturn(new R451("EPG file not found"));
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<EPGEntry> epg = vdr.getEpg(1);
        assertEquals(18557, epg.size());
    }

    @Test(expected = RuntimeException.class)
    public void testGetEpgForChannelError() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTE.class))).thenReturn(null);
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<EPGEntry> epg = vdr.getEpg(1);
        assertEquals(18557, epg.size());
    }

    @Test
    public void testGetChannels() throws IOException, ParseException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTC.class))).thenReturn(new R250(IOUtil.readFile("unittests/lstc.txt")));
        vdr = new VDR("localhost", 2001, 5000, conn);

        List<Channel> channels = vdr.getChannels();
        assertEquals(57, channels.size());
    }

    @Test(expected = RuntimeException.class)
    public void testGetChannelsException() throws IOException, ParseException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTC.class))).thenReturn(new R550("No channels defined."));
        vdr = new VDR("localhost", 2001, 5000, conn);

        vdr.getChannels();
    }

    @Test(expected = RuntimeException.class)
    public void testGetChannelsError() throws IOException, ParseException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(LSTC.class))).thenReturn(null);
        vdr = new VDR("localhost", 2001, 5000, conn);

        vdr.getChannels();
    }

    @Test
    public void testClose() throws IOException {
        Connection conn = mock(Connection.class);
        when(conn.send(isA(QUIT.class))).thenReturn(new R221("vdr closing connection"));
        vdr = new VDR("localhost", 2001, 5000, conn);
        vdr.close();
    }

    // @Test(expected = ConnectException.class)
    // public void testConnectionEstablishment() throws IOException {
    // vdr = new VDR("localhost", 2001, 1, null);
    // vdr.close();
    // }
}

