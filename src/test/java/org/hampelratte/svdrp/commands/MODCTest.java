package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MODCTest {

    String settings = "Das Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0";
    
    @Test
    public void testStringConstructor() {
        MODC modc = new MODC(1, settings);
        assertEquals("MODC 1 " + settings, modc.getCommand());
    }
    
    @Test
    public void testSetNumber() {
        MODC modc = new MODC(1, settings);
        modc.setNumber(2);
        assertEquals(2, modc.getNumber());
        assertEquals("MODC 2 " + settings, modc.getCommand());
    }
    
    @Test
    public void testSetSettings() {
        MODC modc = new MODC(1, settings);
        String newSettings = "ZDF HD;ZDF:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0";
        modc.setSettings(newSettings);
        assertEquals(newSettings, modc.getSettings());
        assertEquals("MODC 1 " + newSettings, modc.getCommand());
    }

    @Test 
    public void testToString() {
        MODC modc = new MODC(1, settings);
        assertEquals("MODC", modc.toString());
    }
}
