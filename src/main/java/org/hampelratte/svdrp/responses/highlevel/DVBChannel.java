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

import java.util.Arrays;


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
    private int alpha = -1;
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
    private int rolloff = -1;
    private int priority = -1;
    
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
    public String toChannelsConf() {
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
    
    @Override
    public String toString() {
        return getName();
    }

    private String getParameterString() {
        StringBuffer sb = new StringBuffer();
        if(getAlpha() > -1) {
            sb.append('A'); sb.append(getAlpha());
        }
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
            sb.append('H');
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
        if(getRolloff() > -1) {
            sb.append('O'); sb.append(getRolloff());
        }
        if(getPriority() > -1) {
            sb.append('P'); sb.append(getPriority());
        }
        if(isRightCircularPolarization()) {
            sb.append('R');
        }
        if(getTransmissionMode() > -1) {
            sb.append('T'); sb.append(getTransmissionMode());
        }
        if(isVerticalPolarization()) {
            sb.append('V');
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
     * @param bandwidth Valid values are 5, 6, 7, 8. To reset this parameter,
     * call the method with -1 as parameter.
     */
    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getCodeRateHP() {
        return codeRateHP;
    }

    /**
     * Sets the code rate high priority value of this channel.
     * @param codeRateHP Valid values are 0, 12, 13, 14, 23, 25, 34, 35, 45, 56, 67, 78, 89, 910.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setCodeRateHP(int codeRateHP) {
        this.codeRateHP = codeRateHP;
    }

    public int getCodeRateLP() {
        return codeRateLP;
    }

    /**
     * Sets the code rate low priority value of this channel.
     * @param codeRateLP Valid values are 0, 12, 13, 14, 23, 25, 34, 35, 45, 56, 67, 78, 89, 910.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setCodeRateLP(int codeRateLP) {
        this.codeRateLP = codeRateLP;
    }

    public int getGuardInterval() {
        return guardInterval;
    }

    /**
     * Sets the guard interval value of this channel.
     * @param guardInterval Valid values are 4, 8, 16, 32.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setGuardInterval(int guardInterval) {
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
    public void setHierarchy(int hierarchy) {
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
    public void setInversion(int inversion) {
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
     * @param modulation Valid values are 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 16, 32, 64, 128, 256, 512, 998, 1024.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setModulation(int modulation) {
        this.modulation = modulation;
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
     * @param transmissionMode Valid values are 2, 4, 8.
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setTransmissionMode(int transmissionMode)  {
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

    public int getAlpha() {
        return alpha;
    }

    /**
     * Sets the alpha value of this channel.
     * @param alpha Valid values are 0, 1, 2, 4
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRolloff() {
        return rolloff;
    }

    /**
     * Sets the rolloff value of this channel.
     * @param rolloff Valid values are 0, 20, 25, 35
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setRolloff(int rolloff) {
        this.rolloff = rolloff;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority value of this channel.
     * @param priority Valid values are 0, 1
     * To reset this parameter, call the method with -1 as parameter.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    /**
     * Validates the parameters of this channel.
     * 
     * @throws IllegalArgumentException
     *             If one of the parameters is invalid.
     */
    public void validate() throws IllegalArgumentException {
        // validate alpha
        int[] validValues = {-1, 0, 1, 2, 4, AUTOMATIC};
        boolean valid = validateArray(validValues, alpha);
        if(!valid) {
            throwIllegalArgumentException("Alpha", alpha, validValues);
        }

        // validate bandwidth
        validValues = new int[] {-1, 5, 6, 7, 8, AUTOMATIC};
        valid = validateArray(validValues, bandwidth);
        if(!valid) {
            throwIllegalArgumentException("Bandwidth", bandwidth, validValues);
        }
        
        // validate codeRateHP
        validValues = new int[] {-1, 0, 12, 13, 14, 23, 25, 34, 35, 45, 56, 67, 78, 89, 910, AUTOMATIC};
        valid = validateArray(validValues, codeRateHP);
        if(!valid) {
            throwIllegalArgumentException("Code rate HP", codeRateHP, validValues);
        }
        
        // validate codeRateLP
        validValues = new int[] {-1, 0, 12, 13, 14, 23, 25, 34, 35, 45, 56, 67, 78, 89, 910, AUTOMATIC};
        valid = validateArray(validValues, codeRateLP);
        if(!valid) {
            throwIllegalArgumentException("Code rate LP", codeRateLP, validValues);
        }
        
        // validate guardInterval
        validValues = new int[] {-1, 4, 8, 16, 32, AUTOMATIC};
        valid = validateArray(validValues, guardInterval);
        if(!valid) {
            throwIllegalArgumentException("Guard interval", guardInterval, validValues);
        }
        
        // validate hierarchy
        validValues = new int[] {-1, 0, 1, 2, 4, AUTOMATIC};
        valid = validateArray(validValues, hierarchy);
        if(!valid) {
            throwIllegalArgumentException("Hierarchy", hierarchy, validValues);
        }
        
        // validate inversion
        validValues = new int[] {-1, 0, 1, AUTOMATIC};
        valid = validateArray(validValues, inversion);
        if(!valid) {
            throwIllegalArgumentException("Inversion", inversion, validValues);
        }
        
        // validate modulation
        validValues = new int[] {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 16, 32, 64, 128, 256, 512, 998, 1024, AUTOMATIC};
        valid = validateArray(validValues, modulation);
        if(!valid) {
            throwIllegalArgumentException("Modulation", modulation, validValues);
        }
        
        // validate priority
        validValues = new int[] {-1, 0, 1, AUTOMATIC};
        valid = validateArray(validValues, priority);
        if(!valid) {
            throwIllegalArgumentException("Priority", priority, validValues);
        }
        
        // validate rolloff
        validValues = new int[] {-1, 0, 20, 25, 35, AUTOMATIC};
        valid = validateArray(validValues, rolloff);
        if(!valid) {
            throwIllegalArgumentException("Rolloff", rolloff, validValues);
        }
        
        // validate transmission mode
        validValues = new int[] {-1, 2, 4, 8, AUTOMATIC};
        valid = validateArray(validValues, transmissionMode);
        if(!valid) {
            throwIllegalArgumentException("Transmission mode", transmissionMode, validValues);
        }
    }
    
    private void throwIllegalArgumentException (String name, int value, int[] validValues) throws IllegalArgumentException {
        throw new IllegalArgumentException(name + " value [" + value + "] is invalid. Valid values are " + Arrays.toString(validValues));
    }
    
    /**
     * 
     * @param validValues an array with valid values
     * @param value the value to validate
     * @return true, if the value is valid
     */
    private boolean validateArray(int[] validValues, int value) {
        for (int i = 0; i < validValues.length; i++) {
            if(value == validValues[i]) {
                return true;
            }
        }
        
        return false;
    }
}