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
 * Command to create a new timer
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * 
 */
public class NEWC extends Command {
    private static final long serialVersionUID = 1L;

    private String settings = "";

    /**
     * Command to create a new timer
     * 
     * @param settings
     *            The settings must be in the same format as returned by the
     *            LSTC command,<br>
     *            e.g. Das
     *            Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
     *            Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional
     *            Access:SID:NID:TID:RID<br>
     *            Have a look at the man page vdr(5) for more details
     */
    public NEWC(String settings) {
        this.settings = settings;
    }

    @Override
    public String getCommand() {
        String cmd = "NEWC " + settings;
        return cmd;
    }

    @Override
    public String toString() {
        return "NEWC";
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
     * @param settings
     *            The settings must be in the same format as returned by the
     *            LSTC command,<br>
     *            e.g. Das
     *            Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
     *            Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional
     *            Access:SID:NID:TID:RID<br>
     *            Have a look at the man page vdr(5) for more details
     */
    public void setSettings(String settings) {
        this.settings = settings;
    }
}
