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
 * Command to move a timer to a new position
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 * 
 */
public class MOVT extends Command {
    private static final long serialVersionUID = 2L;

    private int timer;

    private int position;

    /**
     * Command to move a timer to a new position
     * 
     * @param timer
     *            The timer number
     * @param to
     *            The new postion of the timer
     */
    public MOVT(int timer, int to) {
        this.timer = timer;
        this.position = to;
    }
    
    /**
     * Returns the timer number
     * 
     * @return The timer number
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Sets the timer number
     * 
     * @param number
     *            The timer number
     */
    public void setTimer(int number) {
        this.timer = number;
    }

    /**
     * Returns the new postion of the timer
     * 
     * @return The new postion of the timer
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the new position of the timer
     * 
     * @param setting
     *            The new position of the timer
     */
    public void setPosition(int setting) {
        this.position = setting;
    }

    @Override
    public String getCommand() {
        String cmd = "MOVT " + timer + " " + position;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "MOVT";
    }

}
