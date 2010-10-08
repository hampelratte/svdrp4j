package org.hampelratte.svdrp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;
import org.junit.Test;

public class VDRTimerStateTest {
    
    private VDRTimer timer = new VDRTimer();
    
    @Test
    public void testChangeState() {
        timer.changeStateTo(VDRTimer.ACTIVE, true);
        timer.changeStateTo(VDRTimer.RECORDING, true);
        timer.changeStateTo(VDRTimer.VPS, true);
        
        assertTrue(timer.hasState(VDRTimer.ACTIVE));
        assertTrue(timer.hasState(VDRTimer.RECORDING));
        assertTrue(timer.hasState(VDRTimer.VPS));
        
        timer.changeStateTo(VDRTimer.RECORDING, false);
        
        assertTrue(timer.hasState(VDRTimer.ACTIVE));
        assertFalse(timer.hasState(VDRTimer.RECORDING));
        assertTrue(timer.hasState(VDRTimer.VPS));
        
        timer.changeStateTo(VDRTimer.ACTIVE, false);
        timer.changeStateTo(VDRTimer.VPS, false);
        timer.changeStateTo(VDRTimer.INSTANT_TIMER, true);
        
        assertFalse(timer.hasState(VDRTimer.ACTIVE));
        assertFalse(timer.hasState(VDRTimer.RECORDING));
        assertFalse(timer.hasState(VDRTimer.VPS));
        assertTrue(timer.hasState(VDRTimer.INSTANT_TIMER));
    }
    
    @Test
    public void testSetState() {
        timer.setState(VDRTimer.ACTIVE | VDRTimer.VPS);
        
        assertTrue(timer.hasState(VDRTimer.ACTIVE));
        assertFalse(timer.hasState(VDRTimer.RECORDING));
        assertTrue(timer.hasState(VDRTimer.VPS));
        assertFalse(timer.hasState(VDRTimer.INSTANT_TIMER));
    }
    
    @Test
    public void testIsActive() {
        timer.setState(VDRTimer.ACTIVE);
        assertTrue(timer.isActive());
    }
    
    @Test
    public void testIsRecording() {
        timer.setState(VDRTimer.ACTIVE | VDRTimer.RECORDING);
        assertTrue(timer.isRecording());
        
        timer.setState(VDRTimer.ACTIVE);
        assertFalse(timer.isRecording());
        
        timer.setState(VDRTimer.RECORDING);
        assertFalse(timer.isRecording());
        
        timer.setState(VDRTimer.ACTIVE);
        assertFalse(timer.isRecording());
        Calendar start = Calendar.getInstance();
        start.add(Calendar.HOUR, -1);
        timer.setStartTime(start);
        Calendar stop = Calendar.getInstance();
        stop.add(Calendar.HOUR, 1);
        timer.setEndTime(stop);
        assertTrue(timer.isRecording());
    }
}
