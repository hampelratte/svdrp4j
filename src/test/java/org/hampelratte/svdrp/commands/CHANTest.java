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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CHANTest {
    @Test
    void testChan() {
        CHAN chan = new CHAN();
        assertEquals("CHAN", chan.getCommand());
    }

    @Test
    void testChanWithParam() {
        CHAN chan = new CHAN("+");
        assertEquals("CHAN +", chan.getCommand());

        chan = new CHAN("  -  ");
        assertEquals("CHAN -", chan.getCommand());

        chan = new CHAN("ZDF");
        assertEquals("CHAN ZDF", chan.getCommand());

        chan = new CHAN("Das Erste");
        assertEquals("CHAN Das Erste", chan.getCommand());

        chan = new CHAN("S19.2E-1-1101-28106");
        assertEquals("CHAN S19.2E-1-1101-28106", chan.getCommand());

        chan = new CHAN(1);
        assertEquals("CHAN 1", chan.getCommand());
    }

    @Test
    void testSetParam() {
        CHAN chan = new CHAN("+");
        assertEquals("CHAN +", chan.getCommand());

        chan.setParameter("Das Erste");
        assertEquals("CHAN Das Erste", chan.getCommand());

        chan.setParameter(null);
        assertEquals("CHAN", chan.getCommand());

        chan.setParameter(" ");
        assertEquals("CHAN", chan.getCommand());
    }

    @Test
    void testGetParam() {
        CHAN chan = new CHAN("+");
        assertEquals("CHAN +", chan.getCommand());

        assertEquals("+", chan.getParameter());

        chan.setParameter(null);
        assertNull(chan.getParameter());

        chan.setParameter(" ");
        assertEquals(" ", chan.getParameter());
    }

    @Test
    void testToString() {
        assertEquals("CHAN", new CHAN().toString());
        assertEquals("CHAN", new CHAN("ZDF").toString());
        assertEquals("CHAN", new CHAN(1).toString());
    }
}
