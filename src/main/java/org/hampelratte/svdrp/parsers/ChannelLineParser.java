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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.responses.highlevel.Channel;

public interface ChannelLineParser {

    Channel parse(String chanConfLine);

    static int parseNumberParam(String string, int startIndex) {
        StringBuilder number = new StringBuilder();
        for(int j=startIndex+1; j<string.length(); j++) {
            char c = string.charAt(j);
            if(Character.isDigit(c)) {
                number.append(c);
            } else {
                break;
            }
        }

        return Integer.parseInt(number.toString());
    }

    static boolean hasId(String normalizedChanConfLine) {
        return normalizedChanConfLine.matches("\\d+\\s+.*?(?:-\\d+){3,4}\\s+.*");
    }

    static String removeId(String chanConfLine) {
        Pattern p = Pattern.compile("(\\d+\\s+).*?(?:-\\d+){3,4}\\s+(.*)");
        Matcher m = p.matcher(chanConfLine);
        if(m.matches()) {
            return m.group(1) + m.group(2);
        } else {
            return chanConfLine;
        }
    }
}
