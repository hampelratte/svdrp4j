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
 * Command to simulate the hit of a key on the remote control. To get a list of
 * valid keys use the constructor HITK()
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 *
 */
public class HITK extends Command {
    @Serial
    private static final long serialVersionUID = 1L;

    private String key = "";

    /**
     * Command to get a list of all valid keys. Without a parameter, VDR returns
     * a list of all valid keys
     */
    public HITK() {
    }

    /**
     * Command to simulate the hit of a key on the remote control
     *
     * @param key The name of the key, which should be hit, e.g. UP,DOWN,1,2,...
     */
    public HITK(String key) {
        this.key = key;
    }

    @Override
    public String getCommand() {
        String cmd = "HITK " + key;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "HITK";
    }

    /**
     * Returns the key, which should be pressed
     *
     * @return The key, which should be pressed
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key, which should be pressed
     *
     * @param key The key, which should be pressed, e.g. UP,DOWN,1,2,...
     */
    public void setKey(String key) {
        this.key = key;
    }
}
