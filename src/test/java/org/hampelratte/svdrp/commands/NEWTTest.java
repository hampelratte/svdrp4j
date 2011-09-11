package org.hampelratte.svdrp.commands;
import static org.junit.Assert.assertEquals;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;
import org.hampelratte.svdrp.responses.highlevel.VDRTimerTest;
import org.junit.Test;

public class NEWTTest {

    String settings = "1:7:8:0704:0938:50:50:Quarks & Co:";
    
    @Test
    public void testStringConstructor() {
        NEWT newt = new NEWT(settings);
        assertEquals("NEWT " + settings, newt.getCommand());
    }
    
    @Test 
    public void testTimerConstructor() {
        VDRTimer timer = new VDRTimerTest().createTimer();
        NEWT newt = new NEWT(timer);
        assertEquals("NEWT " + timer.toNEWT(), newt.getCommand());
    }
    
    @Test
    public void testSetSettings() {
        NEWT newt = new NEWT(settings);
        String newSettings = "1:3:9:0704:0938:50:50:Quarks & Co:";
        newt.setSettings(newSettings);
        assertEquals(newSettings, newt.getSettings());
        assertEquals("NEWT " + newSettings, newt.getCommand());
    }
    
    
    @Test 
    public void testToString() {
        NEWT newt = new NEWT(settings);
        assertEquals("NEWT", newt.toString());
    }
}
