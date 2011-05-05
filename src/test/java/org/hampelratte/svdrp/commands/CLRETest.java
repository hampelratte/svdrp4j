package org.hampelratte.svdrp.commands;

import static org.junit.Assert.*;
import org.junit.Test;

public class CLRETest {
    @Test
    public void testClearEntire() {
        CLRE clre = new CLRE();
        assertEquals("CLRE", clre.getCommand());
        
        clre = new CLRE(null);
        assertEquals("CLRE", clre.getCommand());
        
        clre = new CLRE("");
        assertEquals("CLRE", clre.getCommand());
        
        clre = new CLRE("   ");
        assertEquals("CLRE", clre.getCommand());
    }
    
    @Test
    public void testClearChannelId() {
        CLRE clre = new CLRE(1);
        assertEquals("CLRE 1", clre.getCommand());
    }
    
    @Test
    public void testClearChannelName() {
        CLRE clre = new CLRE("ZDF");
        assertEquals("CLRE ZDF", clre.getCommand());
        
        clre = new CLRE("ZDF ");
        assertEquals("CLRE ZDF", clre.getCommand());
    }
}
