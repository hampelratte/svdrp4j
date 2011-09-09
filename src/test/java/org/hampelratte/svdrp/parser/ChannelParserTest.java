/* $Id$
 * 
 * Copyright (c) Henrik Niehaus & Lazy Bones development team
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

import static junit.framework.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.hampelratte.svdrp.parsers.ChannelParser;
import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.responses.highlevel.DVBChannel;
import org.hampelratte.svdrp.responses.highlevel.PvrInputChannel;
import org.junit.Test;


public class ChannelParserTest {

    @Test
    public void testNameParsing() throws ParseException {
        String channelData = 
              "1 Das Erste:11836:hC34:S19.2E:27500:101:102=deu,103=2ch;106=deu:104:0:28106:1:1101:0\n"
            + "2 Das Erste HD;ARD:11361:HC23M5O35S1:S19.2E:22000:6010=27:6020=deu@3,6021=mis@3;6022=deu@106:6030:0:11100:1:1011:0\n"
            + "3 ZDF;ZDFvision:11953:hC34:S19.2E:27500:110:120=deu,121=2ch;125=deu:130:0:28006:1:1079:0\n"
            + "4 WDR Bielefeld,WDR;ARD:12421:hC34:S19.2E:27500:101:102=deu,103=2ch:104:0:28306:1:1201:0";
        
        List<Channel> channels = ChannelParser.parse(channelData, false);

        Channel ard = channels.get(0);
        assertEquals("Das Erste", ard.getName());
        assertEquals("", ard.getShortName());
        assertEquals("", ard.getServiceProviderName());
        
        Channel ardhd = channels.get(1);
        assertEquals("Das Erste HD", ardhd.getName());
        assertEquals("", ardhd.getShortName());
        assertEquals("ARD", ardhd.getServiceProviderName());
        
        Channel zdf = channels.get(2);
        assertEquals("ZDF", zdf.getName());
        assertEquals("", zdf.getShortName());
        assertEquals("ZDFvision", zdf.getServiceProviderName());
        
        Channel wdr = channels.get(3);
        assertEquals("WDR Bielefeld", wdr.getName());
        assertEquals("WDR", wdr.getShortName());
        assertEquals("ARD", wdr.getServiceProviderName());
    }

    @Test
    public void testDifferentInputDevices() throws ParseException {
        String channelData = 
                  "1 NICK AUSTRIA;MTV Networks:362000:M64:C:6900:513=2:661=deu:577:0:28640:1:1091:0\n"
                + "2 DSF:783250:PVRINPUT|TV|PAL:P:0:301:300:305:0:1:0:3499:0\n"
                + "3 Composite 0;pvrinput:1:COMPOSITE0:V:0:301=2:300=@4:0:0:9000:0:0:0\n"
                + "4 S-Video 0;pvrinput:1:SVIDEO0:V:0:301=2:300=@4:0:0:9005:0:0:0\n"
                + "5 DMAX;w_pvrscan:343250:PVRINPUT|TV|PAL:P:0:301=2:300:305:0:5492:0:0:0";
        
        List<Channel> channels = ChannelParser.parse(channelData, false);
        assertTrue(channels.get(0) instanceof DVBChannel);
        assertTrue(channels.get(1) instanceof PvrInputChannel);
        assertTrue(channels.get(2) instanceof PvrInputChannel);
        assertTrue(channels.get(3) instanceof PvrInputChannel);
        assertTrue(channels.get(4) instanceof PvrInputChannel);
    }
    
    @Test
    public void testConditionalAccess() throws ParseException {
        String channelData = 
                  "114 Sky Cinema,Cinema;SKY:11798:hC34:S19.2E:27500:511:512=deu:32:1702,1722,1833,1834,1836,9C4,9C7:10:133:2:0\n" + 
                  "115 Sky Cinema +1,Cinema1;SKY:11798:hC34:S19.2E:27500:1791:1792=deu,1793=eng;1795=deu:32:1702:11:133:2:0";
        
        List<Channel> channels = ChannelParser.parse(channelData, false);
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x1702));
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x1722));
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x1833));
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x1834));
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x1836));
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x9c4));
        assertTrue(((DVBChannel)channels.get(0)).getConditionalAccess().contains(0x9c7));
        
        assertTrue(((DVBChannel)channels.get(1)).getConditionalAccess().contains(0x1702));
    }
    
    @Test(expected = ParseException.class)
    public void testInvalidFormat() throws ParseException {
        ChannelParser.parse("Invalid Data", false);
    }
    
    @Test(expected = ParseException.class)
    public void testInvalidvalues() throws ParseException {
        ChannelParser.parse("1 Das Erste:11836:hC12345:S19.2E:27500:101:102=deu,103=2ch;106=deu:104:0:28106:1:1101:0", false);
    }
    
    @Test
    public void testIgnoreErrors() throws ParseException {
        ChannelParser.parse("Invalid Data", true);
        ChannelParser.parse("1 Das Erste:11836:hC12345:S19.2E:27500:101:102=deu,103=2ch;106=deu:104:0:28106:1:1101:0", true);
    }
}
