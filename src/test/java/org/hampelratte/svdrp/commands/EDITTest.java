package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EDITTest {

    @Test
    public void testConstructor() {
        assertEquals("EDIT 1", new EDIT(1).getCommand());
        assertEquals("EDIT 1", new EDIT().getCommand());
    }
    
    @Test
    public void testSetNumber() {
        EDIT edit = new EDIT(1);
        edit.setNumber(2);
        assertEquals(2, edit.getNumber());
        assertEquals("EDIT 2", edit.getCommand());
    }

    @Test 
    public void testToString() {
        EDIT edit = new EDIT(1);
        assertEquals("EDIT", edit.toString());
    }
}
