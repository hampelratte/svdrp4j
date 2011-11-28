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
package org.hampelratte.svdrp.responses.highlevel;

import java.io.Serializable;

/**
 * Base class for any VDR channel. This can be a DVB channel or a channel provided by any of the VDR plug-ins like an
 * IPTV channel or an PvrInput channel
 * 
 * @author <a href="mailto:hampelratte@users.berlios.de">hampelratte@users.berlios.de</a>
 */
public abstract class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int channelNumber = -1;

    private String name = "";

    private String shortName = "";

    private String serviceProviderName = "";

    /**
     * A channel name coming from VDR may consist of the name, a short name and a service provider name, e.g.:
     * <code>RTL Television,RTL;RTL World</code> This method returns only the name, which is <code>RTL Television</code>
     * 
     * @return the name of the channel
     */
    public String getName() {
        return name;
    }

    /**
     * A channel name may consist of the name, a short name and a service provider name, e.g.: RTL Television,RTL;RTL
     * World This method will split the argument into name, short name and provider name, so that {@link #getName()},
     * {@link #getShortName()} and {@link #getServiceProviderName()} will return the according part.
     * 
     * @param name
     *            the name of the channel. You have to provide at least the name, but the following combinations are
     *            possible:
     *            <ul>
     *            <li>RTL Television</li>
     *            <li>RTL Television,RTL</li>
     *            <li>RTL Television,RTL;RTL World</li>
     *            <li>RTL Television;RTL World</li>
     *            </ul>
     */
    public void setName(String name) {
        int delim = name.indexOf(';');
        if (delim > 0) {
            this.serviceProviderName = name.substring(delim + 1, name.length());
            name = name.substring(0, delim);
        }
        delim = name.indexOf(',');
        if (delim > 0) {
            this.shortName = name.substring(delim + 1, name.length());
            name = name.substring(0, delim);
        }
        this.name = name;
    }

    /**
     * A channel name coming from VDR may consist of the name, a short name and a service provider name, e.g.: RTL
     * Television,RTL;RTL World This method returns only the short name, which is RTL
     * 
     * @return the name of the channel
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * A channel name coming from VDR may consist of the name, a short name and a service provider name, e.g.: RTL
     * Television,RTL;RTL World This method returns only the service provider name, which is RTL World
     * 
     * @return the name of the channel
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * The number of the channel. This equates to the key you would hit on the remote control to tune this channel.
     * 
     * @return the number of the channel.
     */
    public int getChannelNumber() {
        return channelNumber;
    }

    /**
     * The number of the channel. This equates to the key you would hit on the remote control to tune this channel.
     * 
     * @param channelNumber
     *            the number of the channel.
     */
    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    @Override
    public String toString() {
        return getName();
    }
}
