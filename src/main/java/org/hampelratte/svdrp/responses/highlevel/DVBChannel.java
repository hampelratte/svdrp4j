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

import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;


/**
 * Represents a DVB channel of vdr. See man 5 vdr for details
 *
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * @author <a href="mailto:androvdr@googlemail.com">androvdr</a>
 */
public class DVBChannel extends BroadcastChannel {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int QAM = 998;
    public static final int AUTOMATIC = 999;

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

    /**
     * @return The string representation of this channel in the channels.conf format
     */
    public String toChannelsConf() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName().replace(":", "|"));
        if (!getShortName().isEmpty()) {
            sb.append(',');
            sb.append(getShortName());
        }
        if (!getServiceProviderName().isEmpty()) {
            sb.append(';');
            sb.append(getServiceProviderName());
        }
        sb.append(":");
        sb.append(getFrequency());
        sb.append(':');
        sb.append(getParameterString());
        sb.append(':');
        sb.append(getSource());
        sb.append(':');
        sb.append(getSymbolRate());
        sb.append(':');
        sb.append(getVPID());
        sb.append(':');
        sb.append(getAPID());
        sb.append(':');
        sb.append(getTPID());
        sb.append(':');
        sb.append(getSID());
        sb.append(':');
        sb.append(getNID());
        sb.append(':');
        sb.append(getTID());
        sb.append(':');
        sb.append(getRID());

        return sb.toString();
    }

    private String getParameterString() {
        StringBuilder sb = new StringBuilder();
        if (getAlpha() > -1) {
            sb.append('A');
            sb.append(getAlpha());
        }
        if (getBandwidth() > -1) {
            sb.append('B');
            sb.append(getBandwidth());
        }
        if (getCodeRateHP() > -1) {
            sb.append('C');
            sb.append(getCodeRateHP());
        }
        if (getCodeRateLP() > -1) {
            sb.append('D');
            sb.append(getCodeRateLP());
        }
        if (getGuardInterval() > -1) {
            sb.append('G');
            sb.append(getGuardInterval());
        }
        if (isHorizontalPolarization()) {
            sb.append('H');
        }
        if (getInversion() > -1) {
            sb.append('I');
            sb.append(getInversion());
        }
        if (isLeftCircularPolarization()) {
            sb.append('L');
        }
        if (getModulation() > -1) {
            sb.append('M');
            sb.append(getModulation());
        }
        if (getRolloff() > -1) {
            sb.append('O');
            sb.append(getRolloff());
        }
        if (getPriority() > -1) {
            sb.append('P');
            sb.append(getPriority());
        }
        if (isRightCircularPolarization()) {
            sb.append('R');
        }
        if (getTransmissionMode() > -1) {
            sb.append('T');
            sb.append(getTransmissionMode());
        }
        if (isVerticalPolarization()) {
            sb.append('V');
        }
        if (getHierarchy() > -1) {
            sb.append('Y');
            sb.append(getHierarchy());
        }

        return sb.toString();
    }

    public int getBandwidth() {
        return bandwidth;
    }

    /**
     * Sets the bandwidth of this channel.
     *
     * @param bandwidth Valid values are 6, 7, 8. To reset this parameter,
     *                  call the method with -1 as parameter.
     */
    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getCodeRateHP() {
        return codeRateHP;
    }

    /**
     * Sets the code rate high priority value of this channel.
     *
     * @param codeRateHP Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89.
     *                   To reset this parameter, call the method with -1 as parameter.
     */
    public void setCodeRateHP(int codeRateHP) {
        this.codeRateHP = codeRateHP;
    }

    public int getCodeRateLP() {
        return codeRateLP;
    }

    /**
     * Sets the code rate low priority value of this channel.
     *
     * @param codeRateLP Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89.
     *                   To reset this parameter, call the method with -1 as parameter.
     */
    public void setCodeRateLP(int codeRateLP) {
        this.codeRateLP = codeRateLP;
    }

    public int getGuardInterval() {
        return guardInterval;
    }

    /**
     * Sets the guard interval value of this channel.
     *
     * @param guardInterval Valid values are 4, 8, 16, 32.
     *                      To reset this parameter, call the method with -1 as parameter.
     */
    public void setGuardInterval(int guardInterval) {
        this.guardInterval = guardInterval;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    /**
     * Sets the hierarchy value of this channel.
     *
     * @param hierarchy Valid values are 0, 1, 2, 4.
     *                  To reset this parameter, call the method with -1 as parameter.
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
     *
     * @param inversion Valid values are 0, 1.
     *                  To reset this parameter, call the method with -1 as parameter.
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
     *
     * @param modulation Valid values are 0, 12, 23, 34, 45, 56, 67, 78, 89.
     *                   To reset this parameter, call the method with -1 as parameter.
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
     *
     * @param transmissionMode Valid values are 2, 8.
     *                         To reset this parameter, call the method with -1 as parameter.
     */
    public void setTransmissionMode(int transmissionMode) {
        this.transmissionMode = transmissionMode;
    }

    public boolean isVerticalPolarization() {
        return verticalPolarization;
    }

    public void setVerticalPolarization(boolean verticalPolarization) {
        this.verticalPolarization = verticalPolarization;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getID());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DVBChannel c) {
            return c.getID().equals(getID());
        }
        return false;
    }

    @Deprecated
    public int getAlpha() {
        return alpha;
    }

    /**
     * Sets the alpha value of this channel.
     *
     * @param alpha Valid values are 0, 1, 2, 4
     *              To reset this parameter, call the method with -1 as parameter.
     */
    @Deprecated
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Deprecated
    public int getRolloff() {
        return rolloff;
    }

    /**
     * Sets the rolloff value of this channel.
     *
     * @param rolloff Valid values are 0, 20, 25, 35
     *                To reset this parameter, call the method with -1 as parameter.
     */
    @Deprecated
    public void setRolloff(int rolloff) {
        this.rolloff = rolloff;
    }

    @Deprecated
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority value of this channel.
     *
     * @param priority Valid values are 0, 1
     *                 To reset this parameter, call the method with -1 as parameter.
     */
    @Deprecated
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Validates the parameters of this channel.
     *
     * @throws IllegalArgumentException If one of the parameters is invalid.
     */
    public void validate() throws IllegalArgumentException {
        // validate alpha
        int[] validValues = {-1, 0, 1, 2, 4, AUTOMATIC};
        boolean valid = validateArray(validValues, alpha);
        if (!valid) {
            throwIllegalArgumentException("Alpha", alpha, validValues);
        }

        // validate bandwidth
        validValues = new int[]{-1, 1712, 5, 6, 7, 8, 10, AUTOMATIC};
        valid = validateArray(validValues, bandwidth);
        if (!valid) {
            throwIllegalArgumentException("Bandwidth", bandwidth, validValues);
        }

        // validate codeRateHP
        validValues = new int[]{-1, 0, 12, 23, 34, 35, 45, 56, 67, 78, 89, 910, AUTOMATIC};
        valid = validateArray(validValues, codeRateHP);
        if (!valid) {
            throwIllegalArgumentException("Code rate HP", codeRateHP, validValues);
        }

        // validate codeRateLP
        validValues = new int[]{-1, 0, 12, 23, 34, 35, 45, 56, 67, 78, 89, 910, AUTOMATIC};
        valid = validateArray(validValues, codeRateLP);
        if (!valid) {
            throwIllegalArgumentException("Code rate LP", codeRateLP, validValues);
        }

        // validate guardInterval
        validValues = new int[]{-1, 4, 8, 16, 32, 128, 19128, 19256, AUTOMATIC};
        valid = validateArray(validValues, guardInterval);
        if (!valid) {
            throwIllegalArgumentException("Guard interval", guardInterval, validValues);
        }

        // validate hierarchy
        validValues = new int[]{-1, 0, 1, 2, 4, AUTOMATIC};
        valid = validateArray(validValues, hierarchy);
        if (!valid) {
            throwIllegalArgumentException("Hierarchy", hierarchy, validValues);
        }

        // validate inversion
        validValues = new int[]{-1, 0, 1, AUTOMATIC};
        valid = validateArray(validValues, inversion);
        if (!valid) {
            throwIllegalArgumentException("Inversion", inversion, validValues);
        }

        // validate modulation
        validValues = new int[]{-1, 0, 2, 5, 6, 7, 10, 11, 12, 16, 32, 64, 128, 256, QAM, AUTOMATIC};
        valid = validateArray(validValues, modulation);
        if (!valid) {
            throwIllegalArgumentException("Modulation", modulation, validValues);
        }

        // validate priority
        validValues = new int[]{-1, 0, 1, AUTOMATIC};
        valid = validateArray(validValues, priority);
        if (!valid) {
            throwIllegalArgumentException("Priority", priority, validValues);
        }

        // validate rolloff
        validValues = new int[]{-1, 0, 20, 25, 35, AUTOMATIC};
        valid = validateArray(validValues, rolloff);
        if (!valid) {
            throwIllegalArgumentException("Rolloff", rolloff, validValues);
        }

        // validate transmission mode
        validValues = new int[]{-1, 2, 8, AUTOMATIC};
        valid = validateArray(validValues, transmissionMode);
        if (!valid) {
            throwIllegalArgumentException("Transmission mode", transmissionMode, validValues);
        }
    }

    private void throwIllegalArgumentException(String name, int value, int[] validValues) throws IllegalArgumentException {
        throw new IllegalArgumentException(name + " value [" + value + "] is invalid. Valid values are " + Arrays.toString(validValues));
    }

    /**
     *
     * @param validValues an array with valid values
     * @param value       the value to validate
     * @return true, if the value is valid
     */
    private boolean validateArray(int[] validValues, int value) {
        for (int validValue : validValues) {
            if (value == validValue) {
                return true;
            }
        }

        return false;
    }

    /**
     * Copied from "man 5 vdr"
     * <p>
     * A particular channel can be uniquely identified by its channel ID, which is a string that looks like this:
     * <p>
     * S19.2E-1-1089-12003-0
     * <p>
     * The components of this string are the Source (S19.2E), NID (1), TID (1089), SID (12003) and RID (0) as defined above.  The last part can be omitted if it is 0, so the above example could also be written  as
     * S19.2E-1-1089-12003).
     * The channel ID is used in the timers.conf and epg.data files to properly identify the channels.
     * <p>
     * If a channel has both NID and TID set to 0, the channel ID will use the Frequency instead of the TID. For satellite channels an additional offset of 100000, 200000, 300000 or 400000 is added to that number,
     * depending on the Polarization (H, V, L or R, respectively). This is necessary because on some satellites the same frequency is used for two different transponders, with opposite polarization.
     *
     * @return the channel ID as String
     */
    public String getID() {
        String id = getSource() + "-" + getNID() + "-";
        if (getNID() == 0 && getTID() == 0) {
            int part = getFrequency();
            part += isHorizontalPolarization() ? 100000 : 0;
            part += isVerticalPolarization() ? 200000 : 0;
            part += isLeftCircularPolarization() ? 300000 : 0;
            part += isRightCircularPolarization() ? 400000 : 0;
            id += part;
        } else {
            id += getTID();
        }
        id += "-" + getSID();
        if (getRID() != 0) {
            id += "-" + getRID();
        }
        return id;
    }
}
