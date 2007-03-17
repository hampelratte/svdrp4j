/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
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
 * Command to move a channel to a new position
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 * 
 */
public class MOVC extends Command {

    private String channel = "";

    private String position = "";

    /**
     * Command to move a channel to a new position
     * 
     * @param number
     *            The number of the channel
     * @param to
     *            The new postio of the channel
     */
    public MOVC(String number, String to) {
        this.channel = number;
        this.position = to;
    }

    /**
     * Returns the channel number
     * 
     * @return The channel number
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the channel number
     * 
     * @param number
     *            The channel number
     */
    public void setChannel(String number) {
        this.channel = number;
    }

    /**
     * Returns the new position of the channel
     * 
     * @return The new position of the channel
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the new postion of the channel
     * 
     * @param position
     *            The new postion of the channel
     */
    public void setPosition(String position) {
        this.position = position;
    }

    public String getCommand() {
        String cmd = "MOVC " + channel + " " + position;
        return cmd.trim();
    }

    public String toString() {
        return "MOVC";
    }

}
