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
package org.hampelratte.svdrp.parsers;

import org.hampelratte.svdrp.responses.highlevel.PvrInputChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PvrInputChannelLineParserTest {

    private final ChannelLineParser parser = new PvrInputChannelLineParser();

    private PvrInputChannel chan;

    @BeforeEach
    void parseLine() {
        String channelData = "1 ARD;w_pvrscan:224250:TV|PAL|CARD0:V:0:301+101=2:300=@4:305:0:3588:0:0:0";
        chan = (PvrInputChannel) parser.parse(channelData);
    }

    @Test
    void testChannelNumber() {
        assertEquals(1, chan.getChannelNumber());
    }

    @Test
    void testFrequency() {
        assertEquals(224250, chan.getFrequency());
    }

    @Test
    void testSource() {
        assertEquals("V", chan.getSource());
    }

    @Test
    void testVPID() {
        assertEquals("301+101=2", chan.getVPID());
    }

    @Test
    void testAPID() {
        assertEquals("300=@4", chan.getAPID());
    }

    @Test
    void testTPID() {
        assertEquals("305", chan.getTPID());
    }

    @Test
    void testConditionalAccess() {
        assertEquals(Integer.valueOf(0), chan.getConditionalAccess().getFirst());
    }

    @Test
    void testSID() {
        assertEquals(3588, chan.getSID());
    }

    @Test
    void testNID() {
        assertEquals(0, chan.getNID());
    }

    @Test
    void testTID() {
        assertEquals(0, chan.getTID());
    }

    @Test
    void testRID() {
        assertEquals(0, chan.getRID());
    }

    @Test
    void testVideoNorm() {
        assertEquals("PAL", chan.getVideoNorm());
    }

    @Test
    void testCard() {
        assertEquals("CARD0", chan.getCard());
    }

    @Test
    void testType() {
        assertEquals("TV", chan.getType());
    }
}
