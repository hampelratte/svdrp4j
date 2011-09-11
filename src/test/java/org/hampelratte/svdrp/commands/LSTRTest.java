package org.hampelratte.svdrp.commands;

import static org.junit.Assert.*;
import org.junit.Test;

public class LSTRTest {
    
    @Test
    public void testDefaultConstructor() {
        LSTR lstr = new LSTR();
        assertEquals("LSTR", lstr.getCommand());
    }
    
    @Test
    public void testIntConstructors() {
        LSTR lstr = new LSTR(1);
        assertEquals("LSTR 1", lstr.getCommand());
    }
    
    @Test
    public void testStringConstructors() {
        LSTR lstr = new LSTR("2");
        assertEquals("LSTR 2", lstr.getCommand());
    }
    
    @Test 
    public void testSetNumber() {
        LSTR lstr = new LSTR();
        
        lstr.setNumber(2);
        assertEquals(2, lstr.getNumber());
        assertEquals("LSTR 2", lstr.getCommand());

        lstr.setNumber(-1);
        assertEquals(-1, lstr.getNumber());
        assertEquals("LSTR", lstr.getCommand());
    }
    
    @Test
    public void testToString() {
        LSTR lstr = new LSTR();
        assertEquals("LSTR", lstr.toString());
    }
}
