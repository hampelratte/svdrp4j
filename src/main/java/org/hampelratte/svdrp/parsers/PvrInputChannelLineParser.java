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
import org.hampelratte.svdrp.responses.highlevel.PvrInputChannel;

import java.util.StringTokenizer;

public class PvrInputChannelLineParser extends DVBChannelLineParser {

    @Override
    public Channel parse(String chanConfLine) {
        DVBChannel dvb = (DVBChannel) super.parse(chanConfLine);
        PvrInputChannel channel = new PvrInputChannel(dvb);
        parseParameters(channel, chanConfLine);
        return channel;
    }

    @Override
    protected void parseParameters(String string) {
        // override method from DVBChannelLineParser with no operation
    }

    private void parseParameters(PvrInputChannel channel, String chanConfLine) {
        String[] parts = chanConfLine.split(":");
        String params = parts[2];
        StringTokenizer st = new StringTokenizer(params, "|");
        if (params.startsWith("PVRINPUT|")) {
            st.nextToken(); // skip PVRINPUT
        }
        channel.setType(st.nextToken()); // set type (TV, RADIO, COMPOSITE0..COMPOSITE4, SVIDEO0..SVIDEO3)
        if (st.hasMoreElements()) {
            channel.setVideoNorm(st.nextToken());
        }
        if (st.hasMoreElements()) {
            channel.setCard(st.nextToken());
        }
    }
}
