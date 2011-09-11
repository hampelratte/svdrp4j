package org.hampelratte.svdrp.commands;

import static org.junit.Assert.*;
import org.junit.Test;

public class LSTTTest {
    
    @Test
    public void testDefaultConstructor() {
        LSTT lstt = new LSTT();
        assertEquals("LSTT", lstt.getCommand());
    }
    
    @Test
    public void testIntConstructors() {
        LSTT lstt = new LSTT(1);
        assertEquals("LSTT 1", lstt.getCommand());
    }
    
    @Test 
    public void testSetNumber() {
        LSTT lstt = new LSTT();
        lstt.setNumber(1);
        
        assertEquals(1, lstt.getNumber());
        assertEquals("LSTT 1", lstt.getCommand());
    }
    
    @Test
    public void testToString() {
        LSTT lstt = new LSTT();
        assertEquals("LSTT", lstt.toString());
    }
}
