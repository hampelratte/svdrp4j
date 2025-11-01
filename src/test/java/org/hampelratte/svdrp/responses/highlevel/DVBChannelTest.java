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
package org.hampelratte.svdrp.responses.highlevel;

import org.hampelratte.svdrp.parsers.DVBChannelLineParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DVBChannelTest {

    private final DVBChannelLineParser parser = new DVBChannelLineParser();

    @Test
    void testID() {
        DVBChannel channel = (DVBChannel) parser.parse("1 Das Erste:11836:B8C23D12M64T2G32Y0:S19.2E:27500:101:102=deu,103=2ch;106=deu:104:0:28106:1:1101:0");
        assertEquals("S19.2E-1-1101-28106", channel.getID());

        channel.setTID(0);
        channel.setNID(0);
        assertEquals("S19.2E-0-11836-28106", channel.getID());

        channel.setHorizontalPolarization(true);
        assertEquals("S19.2E-0-111836-28106", channel.getID());

        channel.setHorizontalPolarization(false);
        channel.setLeftCircularPolarization(true);
        assertEquals("S19.2E-0-311836-28106", channel.getID());

        channel.setRID(1);
        assertEquals("S19.2E-0-311836-28106-1", channel.getID());
    }
}
