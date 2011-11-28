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
 * Command to request or change the current channel.
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 */
public class CHAN extends Command {
    private static final long serialVersionUID = 1L;
    
    private String parameter;

    /**
     * Command to request the current channel. If CHAN is used without a
     * parameter, VDR will return the current channel
     */
    public CHAN() {
    }

    /**
     * Command to change the channel
     * 
     * @param parameter
     *            One out of:
     *            <ul>
     *            <li>"+"</li>
     *            <li>"-"</li>
     *            <li>the channel number</li>
     *            <li>the channel name</li>
     *            <li>the channel id</li>
     *            </ul>
     */
    public CHAN(String parameter) {
        this.parameter = parameter;
    }
    
    public CHAN(int channelNumber) {
        this.parameter = Integer.toString(channelNumber);
    }
    
    @Override
    public String getCommand() {
        String cmd = "CHAN";
        if(parameter != null) {
            cmd = cmd.concat(" ").concat(parameter.trim());
        }
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "CHAN";
    }

    /**
     * Returns the parameter, which will be sent to VDR
     * 
     * @return The parameter, which will be sent to VDR
     * @see #setParameter(String parameter)
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Sets the parameter, which will be sent to VDR
     * 
     * @param parameter
     *            One out of:
     *            <ul>
     *            <li>"+" switches to the next channel</li>
     *            <li>"-" switches to the previous channel</li>
     *            <li>the channel number {@link org.hampelratte.svdrp.responses.highlevel.Channel#getChannelNumber() Channel.getChannelNumber()}</li>
     *            <li>the channel name {@link org.hampelratte.svdrp.responses.highlevel.Channel#getName() Channel.getName()}</li>
     *            <li>the channel id</li>
     *            </ul>
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
