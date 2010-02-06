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
package org.hampelratte.svdrp.responses.highlevel;


public class BroadcastChannel extends Channel {
    private int frequency = 0;
    
    private String source = "";

    private int symbolRate = 0;

    private String VPID = "";

    private String APID = "";

    private String TPID = "";

    private String conditionalAccess = "";

    private int SID = 0;

    private int NID = 0;

    private int TID = 0;

    private int RID = 0;
    
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public String getAPID() {
        return APID;
    }

    public void setAPID(String apid) {
        APID = apid;
    }

    public String getConditionalAccess() {
        return conditionalAccess;
    }

    public void setConditionalAccess(String conditionalAccess) {
        this.conditionalAccess = conditionalAccess;
    }

    public int getNID() {
        return NID;
    }

    public void setNID(int nid) {
        NID = nid;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int rid) {
        RID = rid;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int sid) {
        SID = sid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getSymbolRate() {
        return symbolRate;
    }

    public void setSymbolRate(int symbolRate) {
        this.symbolRate = symbolRate;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int tid) {
        TID = tid;
    }

    public String getTPID() {
        return TPID;
    }

    public void setTPID(String tpid) {
        TPID = tpid;
    }

    public String getVPID() {
        return VPID;
    }

    public void setVPID(String vpid) {
        VPID = vpid;
    }
}
