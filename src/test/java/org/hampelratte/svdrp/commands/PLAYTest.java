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
package org.hampelratte.svdrp.commands;

import static org.junit.Assert.*; 
import org.junit.Test;

public class PLAYTest {

    @Test
    public void testSimple() {
        PLAY play = new PLAY(1);
        assertEquals("PLAY 1", play.getCommand());
    }
    
    @Test
    public void testWithFrame() {
        PLAY play = new PLAY(2, 50);
        assertEquals("PLAY 2 50", play.getCommand());
    }
    
    @Test
    public void testWithTime() {
        PLAY play = new PLAY(23, "01:20:15");
        assertEquals("PLAY 23 01:20:15", play.getCommand());
    }
    
    @Test
    public void testWithTimePlusFrame() {
        PLAY play = new PLAY(23, "00:01:02.10");
        assertEquals("PLAY 23 00:01:02.10", play.getCommand());
    }

    @Test
    public void testSetTime() {
        PLAY play = new PLAY(23);
        play.setStartTime("00:01:02.10");
        assertEquals("PLAY 23 00:01:02.10", play.getCommand());
        
        try {
            play.setStartTime("00:01");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testSetFrame() {
        PLAY play = new PLAY(23);
        play.setStartFrame(345);
        assertEquals("PLAY 23 345", play.getCommand());
    }
    
    @Test
    public void testWithTimeBegin() {
        PLAY play = new PLAY(23);
        play.setStartTime(PLAY.BEGIN);
        assertEquals("PLAY 23 begin", play.getCommand());
    }
    
    @Test
    public void testResetTime() {
        PLAY play = new PLAY(23);
        play.setStartTime(PLAY.BEGIN);
        assertEquals("begin", play.getStartTime());
        play.setStartFrame(653);
        assertEquals(653, play.getStartFrame());
        assertNull(play.getStartTime());
    }
    
    @Test
    public void testResetFrame() {
        PLAY play = new PLAY(23);
        play.setStartFrame(653);
        assertEquals(653, play.getStartFrame());
        play.setStartTime(PLAY.BEGIN);
        assertEquals("begin", play.getStartTime());
        assertEquals(-1, play.getStartFrame());
    }
}
