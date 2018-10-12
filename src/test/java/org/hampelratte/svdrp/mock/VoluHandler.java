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
package org.hampelratte.svdrp.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoluHandler implements RequestHandler {

    private int volume = 200;
    private boolean muted = false;

    @Override
    public boolean accept(String request) {
        return request.matches("[Vv][Oo][Ll][Uu].*");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Vv][Oo][Ll][Uu](?:\\s+(\\d+|[Mm][Uu][Tt][Ee]|\\+|-)\\s*)?").matcher(request);
        if (m.matches()) {
            String param = m.group(1);
            if(param == null) {
                // noop
            } else if("+".equals(param)) {
                volume = Math.min(255, ++volume);
                muted = false;
            } else if("-".equals(param)) {
                volume = Math.max(0, --volume);
                muted = false;
            } else if("mute".equals(param.toLowerCase())) {
                muted = !muted;
            } else {
                volume = Integer.parseInt(param);
                muted = false;
            }

            if(muted) {
                return "Audio is mute";
            } else {
                return "Audio volume is " + volume;
            }
        } else {
            return "501 Unknown option";
        }
    }

}
