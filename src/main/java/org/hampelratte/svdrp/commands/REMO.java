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
 * Turns the remote control on or off. Without a parameter, the current
 * status of the remote control is reported.
 *
 * @author <a href="hampelratte@users.berlios.de">hampelratte@users.berlios.de</a>
 */
public class REMO extends Command {
    public static final String ON = "on";
    public static final String OFF = "off";
    @Serial
    private static final long serialVersionUID = 1L;
    private String state = "";

    /**
     * Receives the current status of the remote control.
     */
    public REMO() {
        super();
    }

    /**
     * Turns the remote control on or off.
     *
     * @param state <code>true</code> to turn the RC on, <code>false</code> to turn the RC off
     *
     */
    public REMO(boolean state) {
        this.state = state ? ON : OFF;
    }

    /**
     * Turns the remote control on or off.
     *
     * @param state {@link REMO#ON REMO.ON}, {@link REMO#OFF REMO.OFF}
     * @see REMO#ON
     * @see REMO#OFF
     */
    public REMO(String state) {
        super();
        setState(state);
    }

    @Override
    public String getCommand() {
        return ("REMO " + state).trim();
    }

    @Override
    public String toString() {
        return "REMO";
    }

    /**
     * Returns the state
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state to ON, OFF or resets the state.
     *
     * @param state {@link REMO#ON REMO.ON}, {@link REMO#OFF REMO.OFF} or an empty String to reset
     *              the parameter
     * @see REMO#ON
     * @see REMO#OFF
     */
    public void setState(String state) {
        if (!(ON.equals(state) || OFF.equals(state) || state.isEmpty())) {
            throw new IllegalArgumentException("State has to be " + ON + " or " + OFF + " or an empty String");
        }
        this.state = state;
    }

    /**
     * Sets the state to ON or OFF.
     *
     * @param state <code>true</code> to turn the RC on, <code>false</code> to turn the RC off
     */
    public void setState(boolean state) {
        this.state = state ? ON : OFF;
    }
}
