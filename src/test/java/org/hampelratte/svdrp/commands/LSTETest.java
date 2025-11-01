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

class LSTETest {

    private static final String CHANNEL_ID = "S19.2E-1-1011-11110";

    @Test
    void testDefaultConstructor() {
        LSTE lste = new LSTE();
        assertEquals("LSTE", lste.getCommand());
    }

    @Test
    void testChannelConstructors() {
        LSTE lste = new LSTE(1);
        assertEquals("LSTE 1", lste.getCommand());

        lste = new LSTE(CHANNEL_ID);
        assertEquals("LSTE " + CHANNEL_ID, lste.getCommand());
    }

    @Test
    void testChannelAndTimeConstructors() {
        // channel number and time as string
        LSTE lste = new LSTE(1, "now");
        assertEquals("LSTE 1 now", lste.getCommand());
        lste = new LSTE(1, "next");
        assertEquals("LSTE 1 next", lste.getCommand());
        long time = System.currentTimeMillis() / 1000;
        lste = new LSTE(1, "at " + time);
        assertEquals("LSTE 1 at " + time, lste.getCommand());

        // channel id and time as string
        lste = new LSTE(CHANNEL_ID, "now");
        assertEquals("LSTE " + CHANNEL_ID + " now", lste.getCommand());
        lste = new LSTE(CHANNEL_ID, "next");
        assertEquals("LSTE " + CHANNEL_ID + " next", lste.getCommand());
        lste = new LSTE(CHANNEL_ID, "at " + time);
        assertEquals("LSTE " + CHANNEL_ID + " at " + time, lste.getCommand());

        // channel number and time as long
        lste = new LSTE(1, time);
        assertEquals("LSTE 1 at " + time, lste.getCommand());
    }

    @Test
    void testSetChannel() {
        LSTE lste = new LSTE();
        lste.setChannel("1");

        assertEquals("1", lste.getChannel());
        assertEquals("LSTE 1", lste.getCommand());
    }

    @Test
    void testSetTime() {
        LSTE lste = new LSTE();
        lste.setTime("next");

        assertEquals("next", lste.getTime());
        assertEquals("LSTE next", lste.getCommand());

        long time = System.currentTimeMillis() / 1000;
        lste = new LSTE(1, time);
        assertEquals("at " + time, lste.getTime());
    }

    @Test
    void testToString() {
        LSTE lste = new LSTE();
        assertEquals("LSTE", lste.toString());
    }
}
