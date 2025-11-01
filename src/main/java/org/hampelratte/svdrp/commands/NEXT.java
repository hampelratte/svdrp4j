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
 * Command to show the next timer event
 *
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus </a>
 *
 */
public class NEXT extends Command {
    public static final String ABS = "abs";
    public static final String REL = "rel";
    @Serial
    private static final long serialVersionUID = 1L;
    private String mode = "";

    /**
     * Command to show the next timer event in human readable format
     *
     */
    public NEXT() {
    }

    /**
     * Command to show the next timer event
     *
     * @param mode Mode
     * @see #setMode(String)
     */
    public NEXT(String mode) {
        setMode(mode);
    }

    @Override
    public String getCommand() {
        String cmd = "NEXT " + mode;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "NEXT";
    }

    /**
     * Returns the mode
     *
     * @return The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the mode
     *
     * @param mode {@link #ABS} or {@link #REL} <br>
     *             <ul>
     *             <li>{@link #ABS} queries the time in seconds since unix epoche </li>
     *             <li>{@link #REL} queries the time in seconds until the next event relative to the actual time</li>
     *             </ul>
     */
    public void setMode(String mode) {
        if (!(ABS.equals(mode) || REL.equals(mode))) {
            throw new IllegalArgumentException("Mode has to be one of abs or rel");
        }
        this.mode = mode;
    }
}
