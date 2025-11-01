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

import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.hampelratte.svdrp.responses.highlevel.Genre;
import org.hampelratte.svdrp.responses.highlevel.Stream;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EpgParserTest {

    public static final String EPG_DATA = """
            C S19.2E-133-5-1793 Channel Name
            E 12667 1274605200 7200 50 FF
            T Program Title
            D Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.|Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..
            S Lorem ipsum dolor sit amet...
            G 20 21 80 81
            X 1 03 deu 16:9
            X 2 03 deu stereo
            X 3 01 deu
            X 2 03 deu ohne Audiodeskription
            X 5 0B deu 16:9
            X 4 2C deu Deutsch
            V 1274605200
            e
            c""";

    @Test
    void testChannelParsing() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry first = entries.getFirst();

        assertEquals("S19.2E-133-5-1793", first.getChannelID());
        assertEquals("Channel Name", first.getChannelName());

        String epgDataWithoutChannelName = """
                C S19.2E-133-5-1793
                E 12667 1274605200 7200 50 FF
                T Program Title
                D Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..
                e
                c""";

        entries = new EPGParser().parse(epgDataWithoutChannelName);
        first = entries.getFirst();

        assertEquals("S19.2E-133-5-1793", first.getChannelID());
        assertEquals("", first.getChannelName());
    }

    @Test
    void testEntryParsing() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry first = entries.getFirst();

        assertEquals(12667, first.getEventID());                    // event id
        Calendar calStartTime = Calendar.getInstance();    // start time
        calStartTime.setTimeInMillis(1274605200 * 1000L);
        assertEquals(calStartTime, first.getStartTime());
        Calendar calEndTime = (Calendar) calStartTime.clone();      // end time
        calEndTime.add(Calendar.SECOND, 7200);
        assertEquals(calEndTime, first.getEndTime());
        assertEquals(80, first.getTableID());                       // table id
        assertEquals(255, first.getVersion());                      // version

        String epgDataWithoutVersion = """
                C S19.2E-133-5-1793
                E 12667 1274605200 7200 50
                T Program Title
                D Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..
                e
                c""";

        entries = new EPGParser().parse(epgDataWithoutVersion);
        first = entries.getFirst();
        assertEquals(0, first.getVersion());
    }

    @Test
    void testTitleParsing() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry first = entries.getFirst();
        assertEquals("Program Title", first.getTitle());
    }

    @Test
    void testVpsTime() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry first = entries.getFirst();
        Calendar vpsTime = Calendar.getInstance();
        vpsTime.setTimeInMillis(1274605200 * 1000L);
        assertEquals(vpsTime, first.getVpsTime());
    }

    @Test
    void testDescription() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry first = entries.getFirst();
        String expected = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..";
        assertEquals(expected, first.getDescription());

    }

    @Test
    void testShortDescription() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry first = entries.getFirst();
        String expected = "Lorem ipsum dolor sit amet...";
        assertEquals(expected, first.getShortText());
    }

    @Test
    void testStreamTypes() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry entry = entries.getFirst();

        assertEquals(6, entry.getStreams().size());
        assertEquals(3, entry.getAudioStreams().size());

        Stream video16to9 = entry.getStreams().getFirst();
        assertEquals(Stream.CONTENT.MP2V, video16to9.getContent());
        assertEquals(3, video16to9.getType());
        assertEquals("deu", video16to9.getLanguage());
        assertEquals("16:9", video16to9.getDescription());

        Stream audioStereo = entry.getStreams().get(1);
        assertEquals(Stream.CONTENT.MP2A, audioStereo.getContent());
        assertEquals(3, audioStereo.getType());
        assertEquals("deu", audioStereo.getLanguage());
        assertEquals("stereo", audioStereo.getDescription());

        Stream audioNoDesc = entry.getStreams().get(2);
        assertEquals(Stream.CONTENT.SUBTITLE, audioNoDesc.getContent());
        assertEquals(1, audioNoDesc.getType());
        assertEquals("deu", audioNoDesc.getLanguage());
        assertEquals("N/A", audioNoDesc.getDescription());

        Stream audioWoDesc = entry.getStreams().get(3);
        assertEquals(Stream.CONTENT.MP2A, audioWoDesc.getContent());
        assertEquals(3, audioWoDesc.getType());
        assertEquals("deu", audioWoDesc.getLanguage());
        assertEquals("ohne Audiodeskription", audioWoDesc.getDescription());

        video16to9 = entry.getStreams().get(4);
        assertEquals(Stream.CONTENT.H264, video16to9.getContent());
        assertEquals(0x0B, video16to9.getType());
        assertEquals("deu", video16to9.getLanguage());
        assertEquals("16:9", video16to9.getDescription());

        Stream ac3 = entry.getStreams().get(5);
        assertEquals(Stream.CONTENT.AC3, ac3.getContent());
        assertEquals(0x2C, ac3.getType());
        assertEquals("deu", ac3.getLanguage());
        assertEquals("Deutsch", ac3.getDescription());
    }

    @Test
    void testGenres() {
        List<EPGEntry> entries = new EPGParser().parse(EPG_DATA);
        EPGEntry entry = entries.getFirst();
        assertEquals(4, entry.getGenres().size());

        Genre genre = entry.getGenres().getFirst();
        assertEquals(0x20, genre.code());
        assertEquals("News/Current affairs", genre.category());
        assertEquals("news/current affairs (general)", genre.description());

        genre = entry.getGenres().get(1);
        assertEquals(0x21, genre.code());
        assertEquals("News/Current affairs", genre.category());
        assertEquals("news/weather report", genre.description());

        genre = entry.getGenres().get(2);
        assertEquals(0x80, genre.code());
        assertEquals("Social/Political issues/Economics", genre.category());
        assertEquals("social/political issues/economics (general)", genre.description());

        genre = entry.getGenres().get(3);
        assertEquals(0x81, genre.code());
        assertEquals("Social/Political issues/Economics", genre.category());
        assertEquals("magazines/reports/documentary", genre.description());
    }
}

