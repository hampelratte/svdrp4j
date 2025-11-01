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
 * Command to rename a recording
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 *
 */
public class MOVR extends Command {
    @Serial
    private static final long serialVersionUID = 1L;

    private int recording;

    private String newName;

    /**
     * Command to move a recording to a new position
     *
     * @param recording The recording number
     * @param newName   The new name of the recording
     */
    public MOVR(int recording, String newName) {
        this.recording = recording;
        this.newName = newName;
    }

    /**
     * Returns the recording number
     *
     * @return The recording number
     */
    public int getRecording() {
        return recording;
    }

    /**
     * Sets the recording number
     *
     * @param number The recording number
     */
    public void setRecording(int number) {
        this.recording = number;
    }

    /**
     * Returns the new name of the recording
     *
     * @return The new name of the recording
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Sets the new name of the recording
     *
     * @param newName The new name of the recording
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public String getCommand() {
        String cmd = "MOVR " + recording + " " + newName;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "MOVR";
    }

}
