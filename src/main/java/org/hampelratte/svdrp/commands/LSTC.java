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
 * Command to list all channels or details of the a given channel
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 *
 */
public class LSTC extends Command {
    private static final long serialVersionUID = 2L;

    private String channel = "";

    private boolean withGroups = false;

    private boolean withIds = false;

    /**
     * Command to list all channels
     */
    public LSTC() {
    }

    /**
     * Command to list all channels including the group separators
     *
     * @param withGroups  true if the groups should be included
     */
    public LSTC(boolean withGroups) {
        this.withGroups = withGroups;
    }

    /**
     * Command to list all channels including the group separators and channel IDs
     *
     * @param withIds  true if the channel ID should be included
     * @param withGroups  true if the groups should be included
     */
    public LSTC(boolean withIds, boolean withGroups) {
        this.withIds = withIds;
        this.withGroups = withGroups;
    }

    /**
     * Command to get details for a given channel
     *
     * @param channel
     *            The number, ID or the name of the channel
     */
    public LSTC(String channel) {
        this.channel = channel;
    }

    /**
     * Command to get details for a given channel
     *
     * @param channel
     *            The number of the channel
     */
    public LSTC(int channel) {
        this.channel = Integer.toString(channel);
    }

    /**
     * Command to get details for a given channel including the channel ID
     *
     * @param channel
     *            The number, ID or the name of the channel
     * @param withId
     *            true if the channel ID should be included
     *
     */
    public LSTC(String channel, boolean withId) {
        this.channel = channel;
        this.withIds = withId;
    }

    /**
     * Command to get details for a given channel
     *
     * @param channel
     *            The number of the channel
     * @param withId
     *            true if the channel ID should be included
     */
    public LSTC(int channel, boolean withId) {
        this.channel = Integer.toString(channel);
        this.withIds = withId;
    }

    @Override
    public String getCommand() {
        String cmd = "LSTC ";
        if(withIds) {
            cmd += ":ids ";
        }
        if (withGroups) {
            cmd += ":groups";
        } else {
            cmd += channel;
        }
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "LSTC";
    }

    /**
     * Returns the channel, which should be listed
     *
     * @return The channel, which should be listed
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the channel, which should be listed
     *
     * @param channel
     *            The number, ID or the name of the channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }
}
