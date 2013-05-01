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
package org.hampelratte.svdrp.parser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.util.List;

import org.hampelratte.svdrp.parsers.ChannelLineParser;
import org.hampelratte.svdrp.parsers.DVBChannelLineParser;
import org.hampelratte.svdrp.responses.highlevel.DVBChannel;
import org.junit.Before;
import org.junit.Test;

// TODO add different channel lines (especially new HD formats)
public class DVBChannelLineParserTest {

    private String channelData = "1 Das Erste:11836:B8C23D12M64T2G32Y0:S19.2E:27500:101:102=deu,103=2ch;106=deu:104:0:28106:1:1101:0";

    private ChannelLineParser parser = new DVBChannelLineParser();

    private DVBChannel chan;

    @Before
    public void parseLine() {
        chan = (DVBChannel) parser.parse(channelData);
    }

    @Test
    public void testChannelNumber() {
        assertEquals(1, chan.getChannelNumber());
    }

    @Test
    public void testFrequency() {
        assertEquals(11836, chan.getFrequency());
    }

    @Test
    public void testParameters() {
        assertEquals(8, chan.getBandwidth());
        assertEquals(23, chan.getCodeRateHP());
        assertEquals(12, chan.getCodeRateLP());
        assertEquals(32, chan.getGuardInterval());
        assertFalse(chan.isHorizontalPolarization());
        assertEquals(64, chan.getModulation());
        assertEquals(2, chan.getTransmissionMode());
        assertFalse(chan.isVerticalPolarization());
        assertEquals(0, chan.getHierarchy());
    }

    @Test
    public void testSource() {
        assertEquals("S19.2E", chan.getSource());
    }

    @Test
    public void testSymbolRate() {
        assertEquals(27500, chan.getSymbolRate());
    }

    @Test
    public void testVPID() {
        assertEquals("101", chan.getVPID());
    }

    @Test
    public void testAPID() {
        assertEquals("102=deu,103=2ch;106=deu", chan.getAPID());
    }

    @Test
    public void testTPID() {
        assertEquals("104", chan.getTPID());
    }

    @Test
    public void testConditionalAccess() {
        assertEquals(new Integer(0), chan.getConditionalAccess().get(0));

        String channelData = "1 Sky Sport 1,Sport1;SKY:12031:HC34M2S0:S19.2E:27500:2047=2:2048=deu@3,2049=deu@3:32:1702,1722,1833,1834,9C4,9C7,9AF,98C,1861:221:133:4:0";
        DVBChannel chan = (DVBChannel) parser.parse(channelData);

        List<Integer> caList = chan.getConditionalAccess();
        assertEquals(0x1702, caList.get(0).intValue());
        assertEquals(0x1722, caList.get(1).intValue());
        assertEquals(0x1833, caList.get(2).intValue());
        assertEquals(0x1834, caList.get(3).intValue());
        assertEquals(0x9C4, caList.get(4).intValue());
        assertEquals(0x9C7, caList.get(5).intValue());
        assertEquals(0x9AF, caList.get(6).intValue());
        assertEquals(0x98C, caList.get(7).intValue());
        assertEquals(0x1861, caList.get(8).intValue());
    }

    @Test
    public void testReelboxConditionalAccess() {
        String channelData = "1 SF 1 HD;Schweizer Fernsehen:10971:HC23M5O35S1:S13.0E:29700:502=27:503=deu@3,504=eng@3;505=mul@106:507:S11:17201:318:12300:0";
        DVBChannel chan = (DVBChannel) parser.parse(channelData);
        assertEquals(new Integer(17), chan.getConditionalAccess().get(0));
    }

    @Test
    public void testSID() {
        assertEquals(28106, chan.getSID());
    }

    @Test
    public void testNID() {
        assertEquals(1, chan.getNID());
    }

    @Test
    public void testTID() {
        assertEquals(1101, chan.getTID());
    }

    @Test
    public void testRID() {
        assertEquals(0, chan.getRID());
    }
}
