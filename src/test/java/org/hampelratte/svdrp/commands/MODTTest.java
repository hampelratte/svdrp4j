package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;
import org.hampelratte.svdrp.responses.highlevel.VDRTimerTest;
import org.junit.Test;

public class MODTTest {

    String settings = "1:7:8:0704:0938:50:50:Quarks & Co:";
    
    @Test
    public void testStringConstructor() {
        MODT modt = new MODT(1, settings);
        assertEquals("MODT 1 " + settings, modt.getCommand());
    }
    
    @Test 
    public void testTimerConstructor() {
        VDRTimer timer = new VDRTimerTest().createTimer();
        MODT modt = new MODT(1, timer);
        assertEquals("MODT 1 " + timer.toNEWT(), modt.getCommand());
    }
    
    @Test
    public void testSetNumber() {
        MODT modt = new MODT(1, settings);
        modt.setNumber(2);
        assertEquals(2, modt.getNumber());
        assertEquals("MODT 2 " + settings, modt.getCommand());
    }
    
    @Test
    public void testSetSettings() {
        MODT modt = new MODT(1, settings);
        String newSettings = "1:3:9:0704:0938:50:50:Quarks & Co:";
        modt.setSettings(newSettings);
        assertEquals(newSettings, modt.getSettings());
        assertEquals("MODT 1 " + newSettings, modt.getCommand());
    }
    
    
    @Test 
    public void testToString() {
        MODT modt = new MODT(1, settings);
        assertEquals("MODT", modt.toString());
    }
}
