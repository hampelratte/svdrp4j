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

import java.text.MessageFormat;

/**
 * Used by peer-to-peer connections between VDRs to tell the other VDR
 * to establish a connection to this VDR. The name is the SVDRP host name
 * of this VDR, which may differ from its DNS name.
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 */
public class CONN extends Command {
    private static final long serialVersionUID = 1L;
    private static final String COMMAND_TEMPLATE = "CONN name:{0} port:{1,number,#} vdrversion:{2} apiversion:{3} timeout:{4,number,#}";

    private final String name;
    private final int port;
    private final String vdrVersion;
    private final String apiVersion;
    private final int timeout;

    public CONN(String name, int port, String vdrVersion, String apiVersion, int timeout) {
        this.name = name;
        this.port = port;
        this.vdrVersion = vdrVersion;
        this.apiVersion = apiVersion;
        this.timeout = timeout;
    }

    @Override
    public String getCommand() {
        return MessageFormat.format(COMMAND_TEMPLATE, name, port, vdrVersion, apiVersion, timeout);
    }

    @Override
    public String toString() {
        return "CONN";
    }
}
