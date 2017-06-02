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

import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Timer;

public class DelrHandler implements RequestHandler {

    private RecordingManager recordingManager;
    private TimerManager timerManager;

    public DelrHandler(RecordingManager recordingManager, TimerManager timerManager) {
        super();
        this.recordingManager = recordingManager;
        this.timerManager = timerManager;
    }

    @Override
    public boolean accept(String request) {
        return request.matches("[Dd][Ee][Ll][Rr] (.*)");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Dd][Ee][Ll][Rr] (.*)").matcher(request);
        if (m.matches()) {
            try {
                int id = Integer.parseInt(m.group(1));
                Recording rec = recordingManager.getRecording(id);
                if(rec instanceof RunningRecording) {
                    Timer timer = timerManager.getTimer(999);
                    if(timer != null && timer.isActive()) {
                        return "550 Recording \"999\" is in use by timer 999";
                    }
                }

                boolean removed = recordingManager.removeRecording(id);
                if (removed) {
                    return "250 Recording \"" + id + "\" deleted";
                } else {
                    return "550 Recording \"" + id + "\" not defined";
                }
            } catch (NumberFormatException e) {
                return "501 Error in recording id \""+m.group(1)+"\"";
            }
        } else {
            return "501 Missing recording id";
        }
    }

}