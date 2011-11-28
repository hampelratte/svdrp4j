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
package org.hampelratte.svdrp.parsers;

public class ChannelLineParserFactory {

    private static DVBChannelLineParser dvb;

    private static PvrInputChannelLineParser pvr;

    // private static IPTVChannelLineParser iptv;

    private static GroupChannelLineParser group;

    public static ChannelLineParser createChannelParser(String chanConfLine) throws Exception {
        if (isDvbChannel(chanConfLine)) {
            if (dvb == null) {
                dvb = new DVBChannelLineParser();
            }
            return dvb;
            /*
             * TODO enable if IPTV is fully supported if(chanConfLine.toLowerCase().contains("iptv")) { } else if(iptv == null) iptv = new
             * IPTVChannelLineParser(); return iptv;
             */
        } else if (isPvrInputChannel(chanConfLine)) {
            if (pvr == null) {
                pvr = new PvrInputChannelLineParser();
            }
            return pvr;
        } else if (isGroup(chanConfLine)) {
            if (group == null) {
                group = new GroupChannelLineParser();
            }
            return group;
        } else {
            throw new Exception("Unknown format for channels.conf lines: " + chanConfLine);
        }
    }

    private static boolean isGroup(String chanConfLine) {
        return chanConfLine.startsWith("0 :");
    }

    private static boolean isPvrInputChannel(String chanConfLine) {
        return chanConfLine.toLowerCase().contains("pvrinput") || chanConfLine.contains("w_pvrscan");
    }

    private static boolean isDvbChannel(String chanConfLine) {
        int pos = -1;
        for (int i = 0; i < 3; i++) {
            pos = chanConfLine.indexOf(':', pos + 1);
        }
        pos += 1;

        // we check, if the source part starts with S, C or T
        // if every column in name:freq:params:source has at least length 1,
        // S, C or T may start at least at pos 7
        if (pos >= 7) {
            char first = chanConfLine.charAt(pos);
            if (first == 'S' || first == 'C' || first == 'T') {
                return true;
            }
        }

        // String[] parts = chanConfLine.split(":");
        // if (parts.length >= 4 && (parts[3].charAt(0) == 'S' || parts[3].charAt(0) == 'C' || parts[3].charAt(0) == 'T')) {
        // return true;
        // }

        return false;
    }
}