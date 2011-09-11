package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MOVTTest {

    @Test
    public void testConstructor() {
        MOVT movt = new MOVT(1, 20);
        assertEquals("MOVT 1 20", movt.getCommand());
    }
    
    @Test
    public void testSetTimer() {
        MOVT movt = new MOVT(1, 20);
        movt.setTimer(2);
        assertEquals(2, movt.getTimer());
        assertEquals("MOVT 2 20", movt.getCommand());
    }
    
    @Test
    public void testSetPosition() {
        MOVT movt = new MOVT(1, 20);
        movt.setPosition(23);
        assertEquals(23, movt.getPosition());
        assertEquals("MOVT 1 23", movt.getCommand());
    }

    @Test 
    public void testToString() {
        MOVT movt = new MOVT(1, 23);
        assertEquals("MOVT", movt.toString());
    }
}
