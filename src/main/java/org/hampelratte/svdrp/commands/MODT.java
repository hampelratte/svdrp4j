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
import org.hampelratte.svdrp.responses.highlevel.Timer;

/**
 * Command to modify a timer
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * 
 */
public class MODT extends Command {
    private static final long serialVersionUID = 2L;

    private int number;

    private String settings = "";

    /**
     * Command to modify a timer
     * 
     * @param number
     *            The number of the timer
     * @param settings
     *            1:7:8:0704:0938:50:50:Quarks & Co:<br>
     *            Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
     *            In the summary newline characters have to be replaced by |<br>
     *            More details in the man page vdr(5)
     */
    public MODT(int number, String settings) {
        this.settings = settings;
        this.number = number;
    }

    /**
     * Command to modify a timer
     * 
     * @param number
     *            The number of the timer
     * @param timer
     *            The timer with the new settings
     */
    public MODT(int number, Timer timer) {
        this.settings = timer.toNEWT();
        this.number = number;
    }

    /**
     * Returns the number of the timer
     * 
     * @return The number of the timer
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

    /**
     * Returns the settings of the timer
     * 
     * @return The settings of the timer
     */
    public String getSettings() {
        return settings;
    }

    /**
     * Sets the settings of the timer
     * 
     * @param setting
     *            1:7:8:0704:0938:50:50:Quarks & Co:<br>
     *            Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
     *            In the summary newline characters have to be replaced by |<br>
     *            More details in the man page vdr(5)
     */
    public void setSettings(String setting) {
        this.settings = setting;
    }

    @Override
    public String getCommand() {
        String cmd = "MODT " + number + " " + settings;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "MODT";
    }

}
