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
