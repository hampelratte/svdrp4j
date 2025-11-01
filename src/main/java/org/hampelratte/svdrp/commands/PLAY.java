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
package org.hampelratte.svdrp.commands;

import org.hampelratte.svdrp.Command;

import java.io.Serial;

/**
 * Command to start the playback of a recording
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 */
public class PLAY extends Command {
    public static final String BEGIN = "begin";
    @Serial
    private static final long serialVersionUID = 2L;
    int startFrame = -1;

    String startTime = null;

    int recordingNumber;

    /**
     * @param recordingNumber Number of a recording
     */
    public PLAY(int recordingNumber) {
        this(recordingNumber, -1);
    }

    public PLAY(int recordingNumber, int startFrame) {
        this.recordingNumber = recordingNumber;
        this.startFrame = startFrame;
    }

    public PLAY(int recordingNumber, String startTime) {
        this.recordingNumber = recordingNumber;
        this.startTime = startTime;
    }

    public int getRecordingNumber() {
        return recordingNumber;
    }

    public void setRecordingNumber(int recordingNumber) {
        this.recordingNumber = recordingNumber;
    }

    public int getStartFrame() {
        return startFrame;
    }

    /**
     * Sets the frame to begin with. A previously set start time will be reset.
     *
     * @param startFrame Frame start
     */
    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
        this.startTime = null;
    }

    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time to begin with in the format hh:mm:ss[.ff] or {@link PLAY#BEGIN}. A previously set start frame will be reset.
     *
     * @param startTime in the format hh:mm:ss[.ff] or {@link PLAY#BEGIN}
     */
    public void setStartTime(String startTime) {
        if (startTime != null && !("begin".equals(startTime) || startTime.matches("\\d\\d:\\d\\d:\\d\\d(?:\\.\\d\\d)?"))) {
            throw new IllegalArgumentException("Start time has to be in the format hh:mm:ss[.ff]");
        }
        this.startTime = startTime;
        this.startFrame = -1;
    }

    @Override
    public String getCommand() {
        String command = "PLAY " + recordingNumber;
        if (startFrame > -1) {
            command += " " + startFrame;
        } else if (startTime != null) {
            command += " " + startTime;
        }
        return command;
    }

    @Override
    public String toString() {
        return "PLAY";
    }
}
