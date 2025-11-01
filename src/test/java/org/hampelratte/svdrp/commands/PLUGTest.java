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
package org.hampelratte.svdrp.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PLUGTest {

    @Test
    void testPLUG() {
        PLUG p = new PLUG();
        assertEquals("PLUG", p.getCommand());
    }

    @Test
    void testWithPlugin() {
        PLUG p = new PLUG("dummy", "");
        assertEquals("PLUG dummy", p.getCommand());
    }

    @Test
    void testWithPluginAndCommand() {
        PLUG p = new PLUG("dummy", "hello");
        assertEquals("PLUG dummy hello", p.getCommand());
    }

    @Test
    void testWithPluginAndCommandAndOptions() {
        PLUG p = new PLUG("dummy", false, false, "hello", "funny world");
        assertEquals("PLUG dummy hello funny world", p.getCommand());
    }

    @Test
    void testHelpSwitch() {
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
    void testMainSwitch() {
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
    void testToString() {
        PLUG p = new PLUG("dummy", true, true, "hello", "funny world");
        assertEquals("PLUG", p.toString());
    }

    @Test
    void testPluginName() {
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
    void testPluginCommand() {
        PLUG p = new PLUG("dummy", false, false, "hello", "funny world");
        assertEquals("dummy", p.getPluginName());
        assertEquals("PLUG dummy hello funny world", p.getCommand());

        p.setPluginCommand("schwubbi");
        assertEquals("schwubbi", p.getPluginCommand());
        assertEquals("PLUG dummy schwubbi funny world", p.getCommand());
    }

    @Test
    void testOptions() {
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
