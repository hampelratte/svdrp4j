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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;

/**
 * Represents a timer of the VDR software
 * @author <a href="hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 */
public class Timer implements Serializable, Comparable<Timer>, Cloneable {

    private static final long serialVersionUID = 2L;

    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int INSTANT_TIMER = 2;
    public static final int VPS = 4;
    public static final int RECORDING = 8;

    public static final boolean ENABLED = true;
    public static final boolean DISABLED = false;

    private int state = ACTIVE;

    private Calendar startTime = Calendar.getInstance();

    private Calendar endTime = Calendar.getInstance();

    // start date for repeating timers
    private Calendar firstTime = Calendar.getInstance();

    private boolean hasFirstTime;

    private boolean[] repeatingDays = new boolean[7];

    private int channelNumber;
    private String channelId = "";

    private int ID; // NOSONAR

    private int priority;

    private int lifetime;

    private String title = "";

    private String path = "";

    private String description = "";

    public boolean isActive() {
        return hasState(ACTIVE);
    }

    /**
     * Returns, if a timer has a specific state
     *
     * @param state
     *            One of INACTIVE, ACTIVE, INSTANT_TIMER, VPS, RECORDING
     * @return true, if the timer has the state
     * @see Timer#ACTIVE
     * @see Timer#INACTIVE
     * @see Timer#INSTANT_TIMER
     * @see Timer#VPS
     * @see Timer#RECORDING
     */
    public boolean hasState(int state) {
        if (state == INACTIVE) {
            return !isActive();
        } else {
            return (this.state & state) == state;
        }
    }

    /**
     * Returns the channel number returned by a LSTC command. In some cases, this is not set by the parser, but instead the channelId is set.
     *
     * @return the channel number returned as in LSTC as integer. This defaults to 0, if the id keyword has been used in the LSTT command.Either channelNumber
     *         or channelId is set or both.
     *
     * @see #getChannelId()
     */
    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channel) {
        this.channelNumber = channel;
    }

    /**
     * Returns the channel id as described in man 5 vdr (tupel of Source, NID, TID, SID and RID). In some cases, this is not set by the parser, but instead the
     * channelNumber is set.
     *
     * @return the channel id as described in man 5 vdr. This defaults to an empty string, if the id keyword has not been used in the LSTT command. Either
     *         channelNumber or channelId is set or both.
     * @see #getChannelNumber()
     */
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int durability) {
        this.lifetime = durability;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
        if (endTime.before(startTime)) {
            endTime.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replace('|', ':');
    }

    /**
     * Returns a unique key, which consits of the channel, the day, the start time and the end time
     *
     * @return a String which identifies this Timer
     */
    public String getUniqueKey() {
        SimpleDateFormat sdf;
        if (isRepeating()) {
            sdf = new SimpleDateFormat("HH:mm");
        } else {
            sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(getChannelNumber());
        sb.append(':');
        sb.append(getDayString());
        sb.append(':');
        sb.append(sdf.format(getStartTime().getTime()));
        sb.append(':');
        sb.append(sdf.format(getEndTime().getTime()));
        return sb.toString();
    }

    /**
     * Returns a settings string, which can be used to create or update timers
     *
     * @return a settings string, which can be used to create or update timers
     */
    public String toNEWT() {
        String start = createTimeString(getStartTime());
        String end = createTimeString(getEndTime());

        StringBuilder sb = new StringBuilder();
        sb.append(getState());
        sb.append(':');
        sb.append(channelNumber);
        sb.append(':');
        sb.append(getDayString());
        sb.append(':');
        sb.append(start);
        sb.append(':');
        sb.append(end);
        sb.append(':');
        sb.append(priority);
        sb.append(':');
        sb.append(lifetime);
        sb.append(':');
        sb.append(getFile());
        sb.append(':');
        sb.append(description.replace("\n", "|"));

        return sb.toString();
    }

    @Override
    public String toString() {
        String start = createTimeString(getStartTime());
        String end = createTimeString(getEndTime());

        StringBuilder sb = new StringBuilder();
        sb.append(getState());
        sb.append(':');
        sb.append(channelNumber);
        sb.append(':');
        sb.append(getDayString());
        if (isRepeating()) {
            sb.append(" [instance:" + createDateString(startTime) + "]");
        }
        sb.append(':');
        sb.append(start);
        sb.append(':');
        sb.append(end);
        sb.append(':');
        sb.append(priority);
        sb.append(':');
        sb.append(lifetime);
        sb.append(':');
        sb.append(getFile());
        sb.append(':');

        String desc = description.replace("\n", "|");
        if (desc.length() > 15) {
            desc = desc.substring(0, 15) + "...";
        }
        sb.append(desc);

        return sb.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Timer) {
            Timer timer = (Timer) o;
            return timer.toNEWT().equals(toNEWT());
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
    	return Objects.hashCode(toNEWT());
    }

    @Override
    public int compareTo(Timer that) {
        return that.toNEWT().compareTo(this.toNEWT());
    }

    public boolean[] getRepeatingDays() {
        return repeatingDays;
    }

    public void setRepeatingDays(boolean[] repeatingDays) {
        this.repeatingDays = repeatingDays;
    }

    public boolean isRepeating() {
        for (int i = 0; i < repeatingDays.length; i++) {
            if (repeatingDays[i]) {
                return true;
            }
        }
        return false;
    }

    public Calendar getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Calendar firstTime) {
        this.firstTime = firstTime;
        hasFirstTime = true;
    }

    public boolean hasFirstTime() {
        return hasFirstTime;
    }

    public void setHasFirstTime(boolean hasFirstTime) {
        this.hasFirstTime = hasFirstTime;
    }

    public String getDayString() {
        StringBuilder sb = new StringBuilder();

        if (isRepeating()) {
            sb.append(createRepeatingString());
            if (hasFirstTime()) {
                sb.append('@');
                sb.append(createDateString(firstTime));
            }
        } else {
            sb.append(createDateString(startTime));
        }

        return sb.toString();
    }

    private String createDateString(Calendar cal) {
        // shall we use the new format?
        // if no connection is available, we have to use a dummy version
        Version v = Connection.getVersion();
        if (v == null) {
            v = new Version("1.0.0");
        }

        int major = v.getMajor();
        int minor = v.getMinor();
        int rev = v.getRevision();

        boolean newFormat = (major >= 2 || major == 1 && (minor > 3 || minor == 3 && rev >= 23)) || isRepeating();

        String date = "";
        if (newFormat) {
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String dayString = day < 10 ? ("0" + day) : Integer.toString(day);
            int month = cal.get(Calendar.MONTH) + 1;
            String monthString = month < 10 ? ("0" + month) : Integer.toString(month);
            date = cal.get(Calendar.YEAR) + "-" + monthString + "-" + dayString;
        } else {
            date = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        }

        return date;
    }

    private String createTimeString(Calendar time) {
        SimpleDateFormat df = new SimpleDateFormat("HHmm");
        Date date = new Date(time.getTimeInMillis());
        return df.format(date);
    }

    private String createRepeatingString() {
    	StringBuilder day = new StringBuilder();
        day.append(repeatingDays[0] ? 'M' : '-');
        day.append(repeatingDays[1] ? 'T' : '-');
        day.append(repeatingDays[2] ? 'W' : '-');
        day.append(repeatingDays[3] ? 'T' : '-');
        day.append(repeatingDays[4] ? 'F' : '-');
        day.append(repeatingDays[5] ? 'S' : '-');
        day.append(repeatingDays[6] ? 'S' : '-');
        return day.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path.replace('|', ':');
    }

    public String getFile() {
        String tmpPath = this.path;
        if (!tmpPath.endsWith("~") && !tmpPath.equals("")) {
            tmpPath += "~";
        }
        return (tmpPath + title).replace(':', '|');
    }

    public void setFile(String file) {
        if (file.indexOf("~") >= 0) {
            int pos = file.lastIndexOf("~");
            setPath(file.substring(0, pos));
            setTitle(file.substring(pos + 1));
        } else {
            setPath("");
            setTitle(file);
        }
    }

    public boolean isDaySet(Calendar cal) {
        boolean[] days = getRepeatingDays();
        // days begins with 0 - Monday
        // Calendar begins with 1 - Sunday
        // so cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 matches the right day in
        // days
        return days[(cal.get(Calendar.DAY_OF_WEEK) + 5) % 7];
    }

    @Override
    public Object clone() {
        Timer timer = new Timer();
        timer.setID(getID());
        timer.setState(getState());
        timer.setChannelNumber(getChannelNumber());
        timer.setChannelId(getChannelId());
        timer.setDescription(getDescription());
        timer.setEndTime((Calendar) getEndTime().clone());
        timer.setFile(getFile());
        timer.setFirstTime((Calendar) getFirstTime().clone());
        timer.setHasFirstTime(hasFirstTime());
        timer.setChannelNumber(getChannelNumber());
        timer.setLifetime(getLifetime());
        timer.setPath(getPath());
        timer.setPriority(getPriority());
        timer.setRepeatingDays(getRepeatingDays().clone());
        timer.setStartTime((Calendar) getStartTime().clone());
        timer.setTitle(getTitle());
        return timer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public int getState() {
        return state;
    }

    /**
     * Sets the state of a timer. To change a single part of the state, e.g. VPS or ACTIVE, please use {@link Timer#changeStateTo(int, boolean)}
     *
     * @param state
     *            The new state for the timer. Bitwise OR of multiple states is possible. E.g. setState(ACTIVE | VPS) sets the timer to ACTIVE and enables VPS
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Sets the given state to the given value. All other states are not touched.
     *
     * @param state
     *            the state to change
     * @param enabled
     *            the new value of the state
     */
    public void changeStateTo(int state, boolean enabled) {
        if (enabled && hasState(state) || !enabled && !hasState(state)) {
            // we don't have to change anything, because the timer already
            // has the requested state
            return;
        }

        int sign = enabled ? 1 : -1;
        this.state += sign * state;
    }

    public boolean isRecording() {
        if (hasState(ACTIVE) && hasState(RECORDING)) {
            return true;
        }

        Calendar now = Calendar.getInstance();
        if (now.after(getStartTime()) && now.before(getEndTime())) {
            return hasState(ACTIVE);
        }

        return false;
    }
}
