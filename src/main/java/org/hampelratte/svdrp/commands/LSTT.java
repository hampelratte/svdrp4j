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

/**
 * Command to list all timers or details of a given timer
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 *
 */
public class LSTT extends Command {
    private static final long serialVersionUID = 2L;

    private int number;

    private boolean withIds;

    /**
     * Command to get a list of all timers
     */
    public LSTT() {
    }

    /**
     * Command to get a list of all timers. The channel ID is returned instead of the channel number.
     *
     * @param withIds
     *            return the channel ID instead of the channel number
     */
    public LSTT(boolean withIds) {
        this.withIds = withIds;
    }

    /**
     * Command to get details of a given timer
     *
     * @param number
     *            The number/id of this timer
     */
    public LSTT(int number) {
        this.number = number;
    }

    /**
     * Command to get details of a given timer. The channel ID is returned instead of the channel number.
     *
     * @param number
     *            The number/id of this timer
     * @param withId
     *            return the channel ID instead of the channel number
     */
    public LSTT(int number, boolean withId) {
        this.number = number;
        withIds = withId;
    }

    @Override
    public String getCommand() {
        String cmd = "LSTT";
        if (number > 0) {
            cmd += " " + number;
        }
        if(withIds) {
            cmd += " id";
        }
        return cmd;
    }

    @Override
    public String toString() {
        return "LSTT";
    }

    /**
     * Returns the number of the timer
     *
     * @return the number of the timer
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the timer
     *
     * @param number
     *            The number of the timer
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
