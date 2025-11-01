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

import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.responses.highlevel.DVBChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DVBChannelLineParser implements ChannelLineParser {

    private DVBChannel channel;

    @Override
    public Channel parse(String chanConfLine) {
        if (ChannelLineParser.hasId(chanConfLine)) {
            chanConfLine = ChannelLineParser.removeId(chanConfLine);
        }

        channel = new DVBChannel();
        String line = chanConfLine;
        // parse channelNumber
        channel.setChannelNumber(Integer.parseInt(line.substring(0, line.indexOf(' '))));
        // remove channelNumber
        line = line.substring(line.indexOf(' ') + 1);

        StringTokenizer st = new StringTokenizer(line, ":");
        channel.setName(st.nextToken().replace("|", ":"));
        channel.setFrequency(Integer.parseInt(st.nextToken()));
        parseParameters(st.nextToken());
        channel.setSource(st.nextToken());
        channel.setSymbolRate(Integer.parseInt(st.nextToken()));
        channel.setVPID(st.nextToken());
        channel.setAPID(st.nextToken());
        channel.setTPID(st.nextToken());
        channel.setConditionalAccess(parseConditionalAccess(st.nextToken()));
        channel.setSID(Integer.parseInt(st.nextToken()));
        channel.setNID(Integer.parseInt(st.nextToken()));
        channel.setTID(Integer.parseInt(st.nextToken()));
        channel.setRID(Integer.parseInt(st.nextToken()));

        return channel;
    }

    protected List<Integer> parseConditionalAccess(String ca) {
        List<Integer> caList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(ca, ",");
        while (st.hasMoreElements()) {
            String token = st.nextToken();

            // Workaround for Reelbox. Apparently they store, which CA slot to use in the ca field of the channel.
            // S11 and S12 are values, which have been reported by users.
            if (token.toLowerCase().startsWith("s")) {
                token = token.substring(1);
            }

            caList.add(Integer.parseInt(token, 16));
        }
        return caList;
    }

    protected void parseParameters(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            switch (c) {
                case 'B':
                    channel.setBandwidth(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'C':
                    channel.setCodeRateHP(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'D':
                    channel.setCodeRateLP(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'G':
                    channel.setGuardInterval(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'H', 'h':
                    channel.setHorizontalPolarization(true);
                    break;
                case 'I':
                    channel.setInversion(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'L':
                    channel.setLeftCircularPolarization(true);
                    break;
                case 'M':
                    channel.setModulation(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'R':
                    channel.setRightCircularPolarization(true);
                    break;
                case 'T':
                    channel.setTransmissionMode(ChannelLineParser.parseNumberParam(string, i));
                    break;
                case 'V', 'v':
                    channel.setVerticalPolarization(true);
                    break;
                case 'Y':
                    channel.setHierarchy(ChannelLineParser.parseNumberParam(string, i));
                    break;
                default:
                    break;
            }
        }
    }
}
