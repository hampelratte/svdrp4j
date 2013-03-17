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
import org.hampelratte.svdrp.responses.highlevel.IPTVChannel;

public class IPTVChannelLineParser extends ChannelLineParser {

    @Override
    public Channel parse(String chanConfLine) {
        IPTVChannel channel = new IPTVChannel();
        String line = chanConfLine;
        // parse channelNumber
        channel.setChannelNumber(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        // remove channelNumber
        line = line.substring(line.indexOf(" ") + 1);

        // parse other parts
        String[] parts = line.split(":");
        // name
        channel.setName(parts[0]);
        // unique enumeration
        channel.setUniqueEnum(Integer.parseInt(parts[1]));

        // parse the stream settings
        String streamSettings = parts[2];
        String[] streamParts = streamSettings.split("\\|");
        for (String streamPart : streamParts) {
            String[] param = streamPart.split("=");
            String key = param[0];
            String value = param[1];

            if("A".equalsIgnoreCase(key)) {
                channel.setStreamParameters(value);
            } else if ("S".equalsIgnoreCase(key)) {
                channel.setSectionIdScanner("1".equals(value));
            } else if ("P".equalsIgnoreCase(key)) {
                channel.setPidScanner("1".equals(value));
            } else if ("F".equalsIgnoreCase(key)) {
                channel.setProtocol(value);
            } else if ("U".equalsIgnoreCase(key)) {
                channel.setStreamAddress(value);
            }
        }

        // TODO parse the other parameters

        return channel;
    }

}
