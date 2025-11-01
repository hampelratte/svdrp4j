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
 * Command to request or change the primary device.
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 */
public class PRIM extends Command {
    @Serial
    private static final long serialVersionUID = 1L;

    private int number = 0;

    /**
     * Command to change the primary device. If PRIM is used without a
     * parameter, VDR will return the current primary device
     */
    public PRIM() {
    }

    /**
     * Command to change the primary device
     *
     * @param number device number
     *
     */
    public PRIM(int number) {
        this.number = number;
    }

    @Override
    public String getCommand() {
        String cmd = "PRIM";
        if (number > 0) {
            cmd = cmd.concat(" ").concat(Integer.toString(number));
        }
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "PRIM";
    }

    /**
     * Returns the parameter, which will be sent to VDR
     *
     * @return The parameter, which will be sent to VDR
     * @see #setNumber(int number)
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the parameter, which will be sent to VDR
     *
     * @param number device number
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
