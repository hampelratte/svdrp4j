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
 * Command to list all recordings or details of a given recording
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 *
 */
public class LSTR extends Command {
    @Serial
    private static final long serialVersionUID = 2L;

    private int number;

    private boolean path;

    /**
     * Command to get a list of all recordings
     */
    public LSTR() {
    }

    /**
     * Command to get details of a given recording
     *
     * @param number The number of the recording
     */
    public LSTR(String number) {
        this.number = Integer.parseInt(number);
    }

    /**
     * Command to get details of a given recording
     *
     * @param number The number of the recording
     */
    public LSTR(int number) {
        this.number = number;
    }

    /**
     * Command to get details of a given recording
     *
     * @param number The number of the recording
     * @param path   return only the path in the file system instead of the recording information
     *
     */
    public LSTR(String number, boolean path) {
        this.number = Integer.parseInt(number);
        this.path = path;
    }

    /**
     * Command to get details of a given recording
     *
     * @param number The number of the recording
     * @param path   return only the path in the file system instead of the recording information
     */
    public LSTR(int number, boolean path) {
        this.number = number;
        this.path = path;
    }

    @Override
    public String getCommand() {
        String cmd = "LSTR";
        if (number > 0) {
            cmd += " " + number;
        }
        if (path) {
            cmd += " path";
        }
        return cmd;
    }

    @Override
    public String toString() {
        return "LSTR";
    }

    /**
     * Returns the number of the recording
     *
     * @return The number of the recording
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the recording
     *
     * @param number The number of the recording
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
