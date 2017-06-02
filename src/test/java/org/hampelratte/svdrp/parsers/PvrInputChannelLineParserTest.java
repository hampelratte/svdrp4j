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

import static org.junit.Assert.assertEquals;

import org.hampelratte.svdrp.parsers.ChannelLineParser;
import org.hampelratte.svdrp.parsers.PvrInputChannelLineParser;
import org.hampelratte.svdrp.responses.highlevel.PvrInputChannel;
import org.junit.Before;
import org.junit.Test;

public class PvrInputChannelLineParserTest {

    private String channelData = "1 ARD;w_pvrscan:224250:TV|PAL|CARD0:V:0:301+101=2:300=@4:305:0:3588:0:0:0";

    private ChannelLineParser parser = new PvrInputChannelLineParser();

    private PvrInputChannel chan;

    @Before
    public void parseLine() {
        chan = (PvrInputChannel) parser.parse(channelData);
    }

    @Test
    public void testChannelNumber() {
        assertEquals(1, chan.getChannelNumber());
    }

    @Test
    public void testFrequency() {
        assertEquals(224250, chan.getFrequency());
    }

    @Test
    public void testSource() {
        assertEquals("V", chan.getSource());
    }

    @Test
    public void testVPID() {
        assertEquals("301+101=2", chan.getVPID());
    }

    @Test
    public void testAPID() {
        assertEquals("300=@4", chan.getAPID());
    }

    @Test
    public void testTPID() {
        assertEquals("305", chan.getTPID());
    }

    @Test
    public void testConditionalAccess() {
        assertEquals(Integer.valueOf(0), chan.getConditionalAccess().get(0));
    }

    @Test
    public void testSID() {
        assertEquals(3588, chan.getSID());
    }

    @Test
    public void testNID() {
        assertEquals(0, chan.getNID());
    }

    @Test
    public void testTID() {
        assertEquals(0, chan.getTID());
    }

    @Test
    public void testRID() {
        assertEquals(0, chan.getRID());
    }

    @Test
    public void testVideoNorm() {
        assertEquals("PAL", chan.getVideoNorm());
    }

    @Test
    public void testCard() {
        assertEquals("CARD0", chan.getCard());
    }

    @Test
    public void testType() {
        assertEquals("TV", chan.getType());
    }
}
