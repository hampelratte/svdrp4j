package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MOVCTest {

    @Test
    public void testConstructor() {
        MOVC movc = new MOVC(1, 20);
        assertEquals("MOVC 1 20", movc.getCommand());
    }
    
    @Test
    public void testSetChannel() {
        MOVC movc = new MOVC(1, 20);
        movc.setChannel(2);
        assertEquals(2, movc.getChannel());
        assertEquals("MOVC 2 20", movc.getCommand());
    }
    
    @Test
    public void testSetPosition() {
        MOVC movc = new MOVC(1, 20);
        movc.setPosition(23);
        assertEquals(23, movc.getPosition());
        assertEquals("MOVC 1 23", movc.getCommand());
    }

    @Test 
    public void testToString() {
        MOVC movc = new MOVC(1, 23);
        assertEquals("MOVC", movc.toString());
    }
}
