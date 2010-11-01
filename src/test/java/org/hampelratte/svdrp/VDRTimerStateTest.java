/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project (Lazy Bones) nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
