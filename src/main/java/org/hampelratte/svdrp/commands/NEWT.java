/* $Id$
 * 
 * Copyright (c) Henrik Niehaus & Lazy Bones development team
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
import org.hampelratte.svdrp.responses.highlevel.VDRTimer;

/**
 * Command to create a new timer
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * 
 */
public class NEWT extends Command {
    private static final long serialVersionUID = 1L;

    private String settings = "";

    /**
     * Command to create a new timer
     * 
     * @param settings
     *            1:7:8:0704:0938:50:50:Quarks & Co:<br>
     *            Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
     *            In the summary newline characters have to be replaced by |<br>
     *            More details in the man page vdr(5)
     */
    public NEWT(String settings) {
        this.settings = settings;
    }
    
    public NEWT(VDRTimer timer) {
        this(timer.toNEWT());
    }

    @Override
    public String getCommand() {
        String cmd = "NEWT " + settings;
        return cmd;
    }

    @Override
    public String toString() {
        return "NEWT";
    }

    /**
     * Returns the settings
     * 
     * @return The settings
     */
    public String getSettings() {
        return settings;
    }

    /**
     * Sets the settings of the timer
     * 
     * @param settings
     *            1:7:8:0704:0938:50:50:Quarks & Co:<br>
     *            Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
     *            In the summary newline characters have to be replaced by |<br>
     *            More details in the man page vdr(5)
     */
    public void setSettings(String settings) {
        this.settings = settings;
    }
}
