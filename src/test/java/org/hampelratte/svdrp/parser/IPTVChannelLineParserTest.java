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
 * 3. Neither the name of the project nor the names of its
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
package org.hampelratte.svdrp.parser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import org.hampelratte.svdrp.parsers.IPTVChannelLineParser;
import org.hampelratte.svdrp.responses.highlevel.IPTVChannel;
import org.junit.Before;
import org.junit.Test;

public class IPTVChannelLineParserTest {

    private String channelData = "1 Das Erste HD;IPTV:1:S=0|P=0|F=UDP|U=239.35.10.1|A=10000:I:0:256=27:257=deu@4;258=AC3@106:2321:0:28106:0:0:0";

    private IPTVChannelLineParser parser = new IPTVChannelLineParser();

    private IPTVChannel chan;

    @Before
    public void parseLine() {
        chan = (IPTVChannel) parser.parse(channelData);
    }

    @Test
    public void testChannelNumber() {
        assertEquals(1, chan.getChannelNumber());
    }

    @Test
    public void testNameParsing() {
        assertEquals("Das Erste HD", chan.getName());
        assertEquals("", chan.getShortName());
        assertEquals("IPTV", chan.getServiceProviderName());
    }

    @Test
    public void testStreamParsing() {
        assertEquals("239.35.10.1", chan.getStreamAddress());
        assertEquals("10000", chan.getStreamParameters());
        assertEquals("UDP", chan.getProtocol());
        assertFalse(chan.isSectionIdScanner());
        assertFalse(chan.isPidScanner());
    }
}
