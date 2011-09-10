package org.hampelratte.svdrp.commands;

import static junit.framework.Assert.*;

import org.junit.Test;

public class PLUGTest {

    @Test
    public void testPLUG() {
        PLUG p = new PLUG();
        assertEquals("PLUG", p.getCommand());
    }
    
    @Test
    public void testWithPlugin() {
        PLUG p = new PLUG("dummy", "");
        assertEquals("PLUG dummy", p.getCommand());
    }
    
    @Test
    public void testWithPluginAndCommand() {
        PLUG p = new PLUG("dummy", "hello");
        assertEquals("PLUG dummy hello", p.getCommand());
    }
    
    @Test
    public void testWithPluginAndCommandAndOptions() {
        PLUG p = new PLUG("dummy", false, false, "hello", "funny world");
        assertEquals("PLUG dummy hello funny world", p.getCommand());
    }
    
    @Test
    public void testHelpSwitch() {
        PLUG p = new PLUG("dummy", false, true, "hello", "funny world");
        assertEquals("PLUG dummy HELP hello", p.getCommand());
        assertTrue(p.isHelpSwitch());
        
        p.setMainSwitch(true);
        assertEquals("PLUG dummy MAIN", p.getCommand());
        assertFalse(p.isHelpSwitch());
        
        p.setHelpSwitch(true);
        assertTrue(p.isHelpSwitch());
        assertEquals("PLUG dummy HELP hello", p.getCommand());
    }
    
    @Test
    public void testMainSwitch() {
        PLUG p = new PLUG("dummy", true, true, "hello", "funny world");
        assertEquals("PLUG dummy MAIN", p.getCommand());
        assertTrue(p.isMainSwitch());
        
        p.setHelpSwitch(true);
        assertEquals("PLUG dummy HELP hello", p.getCommand());
        assertFalse(p.isMainSwitch());
        
        p.setMainSwitch(true);
        assertEquals("PLUG dummy MAIN", p.getCommand());
        assertTrue(p.isMainSwitch());
    }
    
    @Test
    public void testToString() {
        PLUG p = new PLUG("dummy", true, true, "hello", "funny world");
        assertEquals("PLUG", p.toString());
    }
    
    @Test 
    public void testPluginName() {
        PLUG p = new PLUG("dummy", false, false, "hello", "funny world");
        assertEquals("dummy", p.getPluginName());
        assertEquals("PLUG dummy hello funny world", p.getCommand());
        
        p.setPluginName("schwubbi");
        assertEquals("schwubbi", p.getPluginName());
        assertEquals("PLUG schwubbi hello funny world", p.getCommand());
        
        p.setPluginName(null);
        assertEquals("PLUG", p.getCommand());
    }
    
    @Test 
    public void testPluginCommand() {
        PLUG p = new PLUG("dummy", false, false, "hello", "funny world");
        assertEquals("dummy", p.getPluginName());
        assertEquals("PLUG dummy hello funny world", p.getCommand());
        
        p.setPluginCommand("schwubbi");
        assertEquals("schwubbi", p.getPluginCommand());
        assertEquals("PLUG dummy schwubbi funny world", p.getCommand());
    }
    
    @Test 
    public void testOptions() {
        PLUG p = new PLUG("dummy", false, false, "hello", "funny world");
        assertEquals("dummy", p.getPluginName());
        assertEquals("PLUG dummy hello funny world", p.getCommand());
        
        p.setOptions("schwubbi dubbi");
        assertEquals("schwubbi dubbi", p.getOptions());
        assertEquals("PLUG dummy hello schwubbi dubbi", p.getCommand());
        
        p.setOptions(null);
        assertEquals("PLUG dummy hello", p.getCommand());
    }
}
