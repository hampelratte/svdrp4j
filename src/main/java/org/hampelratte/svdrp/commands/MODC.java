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
 * Command to modify a channel.
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * 
 */
public class MODC extends Command {
    private static final long serialVersionUID = 2L;
    
    private int number;

    private String settings = "";

    /**
     * Command to modify a channel.
     * 
     * @param number
     *            The number of the channel
     * @param settings
     *            The settings must be in the same format as returned by the
     *            LSTC command,<br>
     *            e.g. Das
     *            Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
     *            Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional
     *            Access:SID:NID:TID:RID<br>
     *            Have a look at the man page vdr(5) for more details
     */
    public MODC(int number, String settings) {
        this.number = number;
        this.settings = settings;
    }

    /**
     * Returns the number of the channel
     * 
     * @return The number of the channel
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the channel
     * 
     * @param number
     *            The number of the channel
     */
    public void setNumber(int number) {
        this.number = number;
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
     * Sets the settings
     * 
     * @param setting
     *            The settings must be in the same format as returned by the
     *            LSTC command,<br>
     *            e.g. Das
     *            Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
     *            Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional
     *            Access:SID:NID:TID:RID<br>
     *            Have a look at the man page vdr(5) for more details
     */
    public void setSettings(String setting) {
        this.settings = setting;
    }

    @Override
    public String getCommand() {
        String cmd = "MODC " + number + " " + settings;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "MODC";
    }

}
