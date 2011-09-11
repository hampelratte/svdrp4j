package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;
import org.hampelratte.svdrp.responses.highlevel.VDRTimerTest;
import org.junit.Test;

public class UPDTTest {

    String settings = "1:7:8:0704:0938:50:50:Quarks & Co:";
    
    @Test
    public void testStringConstructor() {
        UPDT updt = new UPDT(settings);
        assertEquals("UPDT " + settings, updt.getCommand());
    }
    
    @Test 
    public void testTimerConstructor() {
        VDRTimer timer = new VDRTimerTest().createTimer();
        UPDT updt = new UPDT(timer);
        assertEquals("UPDT " + timer.toNEWT(), updt.getCommand());
    }

    @Test
    public void testSetSettings() {
        UPDT updt = new UPDT(settings);
        String newSettings = "1:3:9:0704:0938:50:50:Quarks & Co:";
        updt.setSettings(newSettings);
        assertEquals(newSettings, updt.getSettings());
        assertEquals("UPDT " + newSettings, updt.getCommand());
    }
    
    
    @Test 
    public void testToString() {
        UPDT updt = new UPDT(settings);
        assertEquals("UPDT", updt.toString());
    }
}
