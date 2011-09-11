package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VOLUTest {

    @Test
    public void testStringConstructor() {
        VOLU volu = new VOLU("1");
        assertEquals("VOLU 1", volu.getCommand());
    }
    
    @Test
    public void testSetVolume() {
        VOLU volu = new VOLU("1");
        volu.setVolume(2);
        assertEquals("2", volu.getVolume());
        assertEquals("VOLU 2", volu.getCommand());
        
        volu.setVolume("+");
        assertEquals("+", volu.getVolume());
        assertEquals("VOLU +", volu.getCommand());
    }

    @Test 
    public void testToString() {
        VOLU volu = new VOLU("255");
        assertEquals("VOLU", volu.toString());
    }
}
