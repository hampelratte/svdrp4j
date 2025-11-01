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
import static org.junit.jupiter.api.Assertions.assertThrows;

class REMOTest {
    @Test
    void testConstructor() {
        assertEquals("REMO", new REMO().getCommand());
    }

    @Test
    void testConstructorIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> new REMO("foo"));
    }

    @Test
    void testStateConstructor() {
        assertEquals("REMO on", new REMO(REMO.ON).getCommand());
        assertEquals("REMO off", new REMO(REMO.OFF).getCommand());

        assertEquals("REMO on", new REMO(true).getCommand());
        assertEquals("REMO off", new REMO(false).getCommand());
    }

    @Test
    void testSetState() {
        REMO remo = new REMO();
        remo.setState(REMO.OFF);
        assertEquals("off", remo.getState());
        assertEquals("REMO off", remo.getCommand());

        remo.setState(REMO.ON);
        assertEquals("on", remo.getState());
        assertEquals("REMO on", remo.getCommand());

        remo.setState("");
        assertEquals("", remo.getState());
        assertEquals("REMO", remo.getCommand());

        remo.setState(false);
        assertEquals("off", remo.getState());
        assertEquals("REMO off", remo.getCommand());

        remo.setState(true);
        assertEquals("on", remo.getState());
        assertEquals("REMO on", remo.getCommand());
    }

    @Test
    void testSetStateIllegalArgument() {
        REMO remo = new REMO();
        assertThrows(IllegalArgumentException.class, () -> remo.setState("foo"));
    }

    @Test
    void testToString() {
        assertEquals("REMO", new REMO().toString());
        assertEquals("REMO", new REMO(REMO.ON).toString());
    }
}
