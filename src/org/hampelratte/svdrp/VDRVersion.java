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
package org.hampelratte.svdrp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net </a>
 * 
 * Holds the version of the VDR, which we are talking to
 */
public class VDRVersion {

    int major = 0;

    int minor = 0;

    int revision = 0;

    public VDRVersion(String versionString) {
        Pattern pattern = Pattern
                .compile("((?:\\d)+)\\.((?:\\d)+)\\.((?:\\d)+)");
        Matcher m = pattern.matcher(versionString);

        if (m.matches()) {
            major = Integer.parseInt(m.group(1));
            minor = Integer.parseInt(m.group(2));
            revision = Integer.parseInt(m.group(3));
        }
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int major) {
        this.minor = major;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int minor) {
        this.revision = minor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int version) {
        this.major = version;
    }

    public String toString() {
        return major + "." + minor + "." + revision;
    }
}