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


/**
 * Represents a channel of vdr 
 * 
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 * @see man 5 vdr for details
 */
public class DVBChannel extends Channel {
    public static final int AUTOMATIC = 999;

    private int frequency = 0;

    // Parameters
    private int bandwidth = -1;
    private int codeRateHP = -1;
    private int codeRateLP = -1;
    private int guardInterval = -1;
    private boolean horizontalPolarization = false;
    private boolean verticalPolarization = false;
    private int inversion = -1;
    private boolean leftCircularPolarization = false;
    private boolean rightCircularPolarization = false;
    private int modulation = -1;
    private int transmissionMode = -1;
    private int hierarchy = -1;
    
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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
    
    
    
    /**
     * @return The string representation of this channel in the channels.conf format
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getName());
        if(getShortName().length() > 0) {
            sb.append(',');
            sb.append(getShortName());
        }
        if(getServiceProviderName().length() > 0) {
            sb.append(';');
            sb.append(getServiceProviderName());
        }
        sb.append(":");
        sb.append(getFrequency()); sb.append(':');
        sb.append(getParameterString()); sb.append(':');
        sb.append(getSource()); sb.append(':');
        sb.append(getSymbolRate()); sb.append(':');
        sb.append(getVPID()); sb.append(':');
        sb.append(getAPID()); sb.append(':');
        sb.append(getTPID()); sb.append(':');
        sb.append(getSID()); sb.append(':');
        sb.append(getNID()); sb.append(':');
        sb.append(getTID()); sb.append(':');
        sb.append(getRID());
        
        return sb.toString();
    }

    private String getParameterString() {
        StringBuffer sb = new StringBuffer();
        if(getBandwidth() > -1) {
            sb.append('B'); sb.append(getBandwidth());
        }
        if(getCodeRateHP() > -1) {
            sb.append('C'); sb.append(getCodeRateHP());
        }
        if(getCodeRateLP() > -1) {
            sb.append('D'); sb.append(getCodeRateLP());
        }
        if(getGuardInterval() > -1) {
            sb.append('G'); sb.append(getGuardInterval());
        }
        if(isHorizontalPolarization()) {
            sb.append('h');
        }
        if(getInversion() > -1) {
            sb.append('I'); sb.append(getInversion());
        }
        if(isLeftCircularPolarization()) {
            sb.append('L');
        }
        if(getModulation() > -1) {
            sb.append('M'); sb.append(getModulation());
        }
        if(isRightCircularPolarization()) {
            sb.append('R');
        }
        if(getTransmissionMode() > -1) {
            sb.append('T'); sb.append(getTransmissionMode());
        }
        if(isVerticalPolarization()) {
            sb.append('v');
        }
        if(getHierarchy() > -1) {
            sb.append('Y'); sb.append(getHierarchy());
        }
        
        return sb.toString();
    }

    public int getBandwidth() {
        return bandwidth;
    }

    /**
     * Sets the bandwidth of this channel.
     * @param bandwidth Valid values are 6,7,8. To reset this parameter,
     * call the method with -1 as parameter.
     */
    public void setBandwidth(int bandwidth) throws IllegalArgumentException {
        if(bandwidth != 6 & bandwidth != 7 & bandwidth != 8 
                & bandwidth != AUTOMATIC & bandwidth != -1) {
            throw new IllegalArgumentException(bandwidth + " Valid values are 6,7,8 or Channel.AUTOMATIC (999)");
        }
        this.bandwidth = bandwidth;
    }

    public int getCodeRateHP() {
        return codeRateHP;
    }

    /**
     * Sets the code rate high priority value of this channel.
     * @param codeRateHP Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setCodeRateHP(int codeRateHP) throws IllegalArgumentException {
        int[] validValues = {0, 12, 23, 34, 45, 56, 67, 78, 89, AUTOMATIC};
        for (int i = 0; i < validValues.length; i++) {
            if(codeRateHP == validValues[i]) {
                this.codeRateHP = codeRateHP;
                return;
            }
        }
        throw new IllegalArgumentException(codeRateHP +" Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89 or Channel.AUTOMATIC (999)");
    }

    public int getCodeRateLP() {
        return codeRateLP;
    }

    /**
     * Sets the code rate low priority value of this channel.
     * @param codeRateLP Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setCodeRateLP(int codeRateLP) throws IllegalArgumentException {
        int[] validValues = {0, 12, 23, 34, 45, 56, 67, 78, 89, AUTOMATIC};
        for (int i = 0; i < validValues.length; i++) {
            if(codeRateLP == validValues[i]) {
                this.codeRateLP = codeRateLP;
                return;
            }
        }
        throw new IllegalArgumentException(codeRateLP + " Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89 or Channel.AUTOMATIC (999)");
    }

    public int getGuardInterval() {
        return guardInterval;
    }

    /**
     * Sets the guard interval value of this channel.
     * @param guardInterval Valid values are 4, 8, 16, 32.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setGuardInterval(int guardInterval) throws IllegalArgumentException {
        if(guardInterval != 4 & guardInterval != 8 & guardInterval != 16 
                & guardInterval != 32 & guardInterval != AUTOMATIC & guardInterval != -1) {
            throw new IllegalArgumentException(guardInterval + " Valid values are 4, 8, 16, 32 or Channel.AUTOMATIC (999)");
        }
        this.guardInterval = guardInterval;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    /**
     * Sets the hierarchy value of this channel.
     * @param hierarchy Valid values are 0, 1, 2, 4.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setHierarchy(int hierarchy) throws IllegalArgumentException {
        if(hierarchy != 0 & hierarchy != 1 & hierarchy != 2 
                & hierarchy != 4 & hierarchy != AUTOMATIC & hierarchy != -1) {
            throw new IllegalArgumentException(hierarchy + " Valid values are 0, 1, 2, 4 or Channel.AUTOMATIC (999)");
        }
        this.hierarchy = hierarchy;
    }

    public boolean isHorizontalPolarization() {
        return horizontalPolarization;
    }

    public void setHorizontalPolarization(boolean horizontalPolarization) {
        this.horizontalPolarization = horizontalPolarization;
    }

    public int getInversion() {
        return inversion;
    }

    /**
     * Sets the inversion value of this channel.
     * @param inversion Valid values are 0, 1.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setInversion(int inversion) throws IllegalArgumentException {
        if(inversion != 0 & inversion != 1 & inversion != AUTOMATIC & inversion != -1) {
            throw new IllegalArgumentException(inversion + " Valid values are 0, 1 or Channel.AUTOMATIC (999)");
        }
        this.inversion = inversion;
    }

    public boolean isLeftCircularPolarization() {
        return leftCircularPolarization;
    }

    public void setLeftCircularPolarization(boolean leftCircularPolarization) {
        this.leftCircularPolarization = leftCircularPolarization;
    }

    public int getModulation() {
        return modulation;
    }

    /**
     * Sets the modulation value of this channel.
     * @param modulation Valid values are 0, 16, 32, 64, 128, 256.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setModulation(int modulation) throws IllegalArgumentException {
        int[] validValues = {0, 16, 32, 64, 128, 256, AUTOMATIC};
        for (int i = 0; i < validValues.length; i++) {
            if(modulation == validValues[i]) {
                this.modulation = modulation;
                return;
            }
        }
        throw new IllegalArgumentException(modulation + " Valid values are 0, 16, 32, 64, 128, 256 or Channel.AUTOMATIC (999)");
    }

    public boolean isRightCircularPolarization() {
        return rightCircularPolarization;
    }

    public void setRightCircularPolarization(boolean rightCircularPolarization) {
        this.rightCircularPolarization = rightCircularPolarization;
    }

    public int getTransmissionMode() {
        return transmissionMode;
    }

    /**
     * Sets the transmission mode value of this channel.
     * @param transmissionMode Valid values are 2, 8.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setTransmissionMode(int transmissionMode) throws IllegalArgumentException {
        if(transmissionMode != 2 & transmissionMode != 8 & transmissionMode != AUTOMATIC & transmissionMode != -1) {
            throw new IllegalArgumentException(transmissionMode + " Valid values are 2, 8 or Channel.AUTOMATIC (999)");
        }
        this.transmissionMode = transmissionMode;
    }

    public boolean isVerticalPolarization() {
        return verticalPolarization;
    }

    public void setVerticalPolarization(boolean verticalPolarization) {
        this.verticalPolarization = verticalPolarization;
    }

    public String getChannelID() {
        return getSource()+"-"+getNID()+"-"+getTID()+"-"+getSID()+"-"+getRID();
    }
    
    public boolean equals(Object o) {
        if (o instanceof DVBChannel) {
            DVBChannel c = (DVBChannel) o;
            return c.getChannelID().equals(getChannelID());
        }
        return false;
    }
}