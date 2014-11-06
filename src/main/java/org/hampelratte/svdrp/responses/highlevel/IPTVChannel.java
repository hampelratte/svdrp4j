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


public class IPTVChannel extends Channel {

    private static final long serialVersionUID = 2L;

    private int uniqueEnum;

    private boolean sectionIdScanner;

    private boolean pidScanner;

    private String protocol;

    private String streamAddress;

    private String streamParameters;

    public int getUniqueEnum() {
        return uniqueEnum;
    }

    public void setUniqueEnum(int uniqueEnum) {
        this.uniqueEnum = uniqueEnum;
    }

    public boolean isSectionIdScanner() {
        return sectionIdScanner;
    }

    public void setSectionIdScanner(boolean sectionIdScanner) {
        this.sectionIdScanner = sectionIdScanner;
    }

    public boolean isPidScanner() {
        return pidScanner;
    }

    public void setPidScanner(boolean pidScanner) {
        this.pidScanner = pidScanner;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getStreamAddress() {
        return streamAddress;
    }

    public void setStreamAddress(String streamAddress) {
        this.streamAddress = streamAddress;
    }

    public String getStreamParameters() {
        return streamParameters;
    }

    public void setStreamParameters(String streamParameters) {
        this.streamParameters = streamParameters;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IPTVChannel)) {
            return false;
        }

        IPTVChannel other = (IPTVChannel) obj;

        return this.protocol.equals(other.protocol)
                && this.streamAddress.equals(other.streamAddress)
                && this.streamParameters.equals(other.streamParameters);
    }
}
