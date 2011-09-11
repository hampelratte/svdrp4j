package org.hampelratte.svdrp.commands;

import static org.junit.Assert.*;
import org.junit.Test;

public class LSTETest {
    
    private static final String CHANNEL_ID = "S19.2E-1-1011-11110";
    
    @Test
    public void testDefaultConstructor() {
        LSTE lste = new LSTE();
        assertEquals("LSTE", lste.getCommand());
    }
    
    @Test
    public void testChannelConstructors() {
        LSTE lste = new LSTE(1);
        assertEquals("LSTE 1", lste.getCommand());
        
        lste = new LSTE(CHANNEL_ID);
        assertEquals("LSTE " + CHANNEL_ID, lste.getCommand());
    }
    
    @Test
    public void testChannelAndTimeConstructors() {
        // channel numer and time as string
        LSTE lste = new LSTE(1, "now");
        assertEquals("LSTE 1 now", lste.getCommand());
        lste = new LSTE(1, "next");
        assertEquals("LSTE 1 next", lste.getCommand());
        long time = System.currentTimeMillis() / 1000;
        lste = new LSTE(1, "at " + time);
        assertEquals("LSTE 1 at " + time, lste.getCommand());

        // channel id and time as string
        lste = new LSTE(CHANNEL_ID, "now");
        assertEquals("LSTE "+CHANNEL_ID+" now", lste.getCommand());
        lste = new LSTE(CHANNEL_ID, "next");
        assertEquals("LSTE "+CHANNEL_ID+" next", lste.getCommand());
        lste = new LSTE(CHANNEL_ID, "at " + time);
        assertEquals("LSTE "+CHANNEL_ID+" at " + time, lste.getCommand());
        
        // channel number and time as long
        lste = new LSTE(1, time);
        assertEquals("LSTE 1 at " + time, lste.getCommand());
    }
    
    @Test 
    public void testSetChannel() {
        LSTE lste = new LSTE();
        lste.setChannel("1");
        
        assertEquals("1", lste.getChannel());
        assertEquals("LSTE 1", lste.getCommand());
    }
    
    @Test 
    public void testSetTime() {
        LSTE lste = new LSTE();
        lste.setTime("next");
        
        assertEquals("next", lste.getTime());
        assertEquals("LSTE next", lste.getCommand());
        
        long time = System.currentTimeMillis() / 1000;
        lste = new LSTE(1, time);
        assertEquals("at " + time, lste.getTime());
    }
    
    @Test
    public void testToString() {
        LSTE lste = new LSTE();
        assertEquals("LSTE", lste.toString());
    }
}
