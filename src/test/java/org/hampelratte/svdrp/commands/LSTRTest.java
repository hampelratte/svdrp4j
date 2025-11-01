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

class LSTRTest {

    @Test
    void testDefaultConstructor() {
        LSTR lstr = new LSTR();
        assertEquals("LSTR", lstr.getCommand());
    }

    @Test
    void testIntConstructors() {
        LSTR lstr = new LSTR(1);
        assertEquals("LSTR 1", lstr.getCommand());
        lstr = new LSTR(1, false);
        assertEquals("LSTR 1", lstr.getCommand());
        lstr = new LSTR(1, true);
        assertEquals("LSTR 1 path", lstr.getCommand());
    }

    @Test
    void testStringConstructors() {
        LSTR lstr = new LSTR("2");
        assertEquals("LSTR 2", lstr.getCommand());
        lstr = new LSTR("2", false);
        assertEquals("LSTR 2", lstr.getCommand());
        lstr = new LSTR("2", true);
        assertEquals("LSTR 2 path", lstr.getCommand());
    }

    @Test
    void testSetNumber() {
        LSTR lstr = new LSTR();

        lstr.setNumber(2);
        assertEquals(2, lstr.getNumber());
        assertEquals("LSTR 2", lstr.getCommand());

        lstr.setNumber(-1);
        assertEquals(-1, lstr.getNumber());
        assertEquals("LSTR", lstr.getCommand());
    }

    @Test
    void testToString() {
        LSTR lstr = new LSTR();
        assertEquals("LSTR", lstr.toString());
    }
}
