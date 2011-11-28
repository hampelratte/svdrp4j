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
