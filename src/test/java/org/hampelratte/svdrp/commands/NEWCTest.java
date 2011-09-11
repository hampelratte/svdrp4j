package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NEWCTest {

    String settings = "Das Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0";
    
    @Test
    public void testStringConstructor() {
        NEWC newc = new NEWC(settings);
        assertEquals("NEWC " + settings, newc.getCommand());
    }
    
    @Test
    public void testSetSettings() {
        NEWC newc = new NEWC(settings);
        String newSettings = "ZDF HD;ZDF:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0";
        newc.setSettings(newSettings);
        assertEquals(newSettings, newc.getSettings());
        assertEquals("NEWC " + newSettings, newc.getCommand());
    }

    @Test 
    public void testToString() {
        NEWC newc = new NEWC(settings);
        assertEquals("NEWC", newc.toString());
    }
}
