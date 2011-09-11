/* $Id$
 * 
 * Copyright (c) Henrik Niehaus & Lazy Bones development team
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
package org.hampelratte.svdrp.responses.highlevel;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.hampelratte.svdrp.parser.EpgParserTest;
import org.hampelratte.svdrp.parsers.EPGParser;
import org.junit.Test;

/** 
 * Most of the methods are covered by RecordingParserTest, so we just test the rest
 *
 * @author <a href="mailto:hampelratte@users.berlios.de">hampelratte@users.berlios.de</a>
 */
public class RecordingTest {

    @Test
    public void testEpgConstructor() {
        EPGEntry entry = new EPGParser().parse(EpgParserTest.epgData).get(0);
        Recording rec = new Recording(entry);
        
        assertEquals("S19.2E-133-5-1793", rec.getChannelID());
        assertEquals("Channel Name", rec.getChannelName());
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..", rec.getDescription());
        assertEquals("Program Title", rec.getDisplayTitle());
        assertEquals(1274612400000L, rec.getEndTime().getTimeInMillis());
        assertEquals(12667, rec.getEventID());
        assertEquals("Lorem ipsum dolor sit amet...", rec.getShortText());
        assertEquals(1274605200000L, rec.getStartTime().getTimeInMillis());
        assertEquals(6, rec.getStreams().size());
        assertEquals(80, rec.getTableID());
        assertEquals(255, rec.getVersion());
        assertEquals(1274605200000L, rec.getVpsTime().getTimeInMillis());
    }
    
    @Test
    public void testToString() {
        Recording rec = new Recording();
        rec.setTitle("Test");
        rec.setNumber(1);
        rec.setNew(true);
        rec.setStartTime(1315761692053L);
        rec.setEndTime(rec.getStartTime().getTimeInMillis() + TimeUnit.MINUTES.toMillis(60));
        assertEquals("1 11.09.2011 19:21:32* Test starts: 19:21 ends: 20:21", rec.toString());
        
        rec.setNew(false);
        assertEquals("1 11.09.2011 19:21:32 Test starts: 19:21 ends: 20:21", rec.toString());
    }
    
    @Test
    public void testCompareTo() {
        Recording r1 = new Recording();
        r1.setTitle("A");
        Recording r2 = new Recording();
        r2.setTitle("B");
        
        assertEquals(-1, r1.compareTo(r2));
        assertEquals(1, r2.compareTo(r1));
        r2.setTitle("A");
        assertEquals(0, r2.compareTo(r1));
        assertEquals(0, r1.compareTo(r2));
    }
}
