/*
 * Copyright (c) Henrik Niehaus
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
 * IMPLIED WARRANTIES OF MERDELTTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp.commands;

import static org.junit.Assert.assertEquals;

import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.hampelratte.svdrp.responses.highlevel.TimerTest;
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
        Timer timer = new TimerTest().createTimer();
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
    public void testActivateDeactivate() {
        MODT modt = new MODT(1, "off");
        assertEquals("off", modt.getSettings());
        assertEquals("MODT 1 off", modt.getCommand());

        modt = new MODT(1, "on");
        assertEquals("on", modt.getSettings());
        assertEquals("MODT 1 on", modt.getCommand());
    }

    @Test
    public void testToString() {
        MODT modt = new MODT(1, settings);
        assertEquals("MODT", modt.toString());
    }
}
