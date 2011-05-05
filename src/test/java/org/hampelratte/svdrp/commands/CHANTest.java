package org.hampelratte.svdrp.commands;
import static org.junit.Assert.*;
import org.junit.Test;

public class CHANTest {
    @Test
    public void testChan() {
        CHAN chan = new CHAN();
        assertEquals("CHAN", chan.getCommand());
    }
    
    @Test
    public void testChanWithParam() {
        CHAN chan = new CHAN("+");
        assertEquals("CHAN +", chan.getCommand());
        
        chan = new CHAN("  -  ");
        assertEquals("CHAN -", chan.getCommand());
        
        chan = new CHAN("ZDF");
        assertEquals("CHAN ZDF", chan.getCommand());
        
        chan = new CHAN("Das Erste");
        assertEquals("CHAN Das Erste", chan.getCommand());
        
        chan = new CHAN("S19.2E-1-1101-28106");
        assertEquals("CHAN S19.2E-1-1101-28106", chan.getCommand());
    }
    
    @Test
    public void testSetParam() {
        CHAN chan = new CHAN("+");
        assertEquals("CHAN +", chan.getCommand());
        
        chan.setParameter("Das Erste");
        assertEquals("CHAN Das Erste", chan.getCommand());

        chan.setParameter(null);
        assertEquals("CHAN", chan.getCommand());
        
        chan.setParameter(" ");
        assertEquals("CHAN", chan.getCommand());
    }
    
    @Test
    public void testGetParam() {
        CHAN chan = new CHAN("+");
        assertEquals("CHAN +", chan.getCommand());
        
        assertEquals("+", chan.getParameter());

        chan.setParameter(null);
        assertNull(chan.getParameter());
        
        chan.setParameter(" ");
        assertEquals(" ", chan.getParameter());
    }
}
