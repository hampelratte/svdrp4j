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
package de.hampelratte.svdrp.responses.highlevel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hampelratte.svdrp.Connection;
import de.hampelratte.svdrp.VDRVersion;

/**
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net </a>
 * 
 * Represents a timer of the VDR software
 */
public class VDRTimer implements Serializable, Comparable {

    private static final long serialVersionUID = 3865636821582767283L;

    private int ID;

    private Calendar startTime = GregorianCalendar.getInstance();

    private Calendar endTime = GregorianCalendar.getInstance();

    // start date for repeating timers
    private Calendar firstTime = GregorianCalendar.getInstance();

    private boolean active;

    private boolean hasFirstTime;

    private boolean[] repeatingDays = new boolean[7];

    private int channel;

    private String tvBrowserProgID;

    private int priority;

    private int lifetime;

    private String title = "";

    private String path = "";

    private String description = "";

    public VDRTimer() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
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
        title.replaceAll(":", "|");
        this.title = title;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public String toNEWT() {
        String start = Integer.toString(startTime.get(Calendar.HOUR_OF_DAY));
        if (start.length() < 2) {
            start = "0" + start;
        }
        String minute = Integer.toString(startTime.get(Calendar.MINUTE));
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        start += minute;

        String end = Integer.toString(endTime.get(Calendar.HOUR_OF_DAY));
        if (end.length() < 2) {
            end = "0" + end;
        }
        minute = Integer.toString(endTime.get(Calendar.MINUTE));
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        end += minute;

        StringBuffer sb = new StringBuffer();
        sb.append(1);
        sb.append(':');
        sb.append(channel);
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
        sb.append(description.replaceAll("\n", "\\|"));

        return sb.toString();
    }

    public String toString() {
        return toNEWT();
    }

    public boolean equals(Object o) {
        if (o instanceof VDRTimer) {
            VDRTimer timer = (VDRTimer) o;
            return timer.toNEWT().equals(toNEWT());
        } else {
            return false;
        }
    }

    public int compareTo(Object o) {
        if (o instanceof VDRTimer) {
            VDRTimer timer = (VDRTimer) o;
            return timer.toNEWT().compareTo(this.toNEWT());
        }
        return -1;
    }

    public boolean[] getRepeatingDays() {
        return repeatingDays;
    }

    public void setRepeatingDays(boolean[] repeating_days) {
        this.repeatingDays = repeating_days;
    }

    public boolean isRepeating() {
        for (int i = 0; i < repeatingDays.length; i++) {
            if (repeatingDays[i])
                return true;
        }
        return false;
    }

    public Calendar getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Calendar firstTime) {
        this.firstTime = firstTime;
    }

    public boolean hasFirstTime() {
        return hasFirstTime;
    }

    public void setHasFirstTime(boolean hasFirstTime) {
        this.hasFirstTime = hasFirstTime;
    }

    public String getDayString() {
        StringBuffer sb = new StringBuffer();

        if (isRepeating()) {
            sb.append(createRepeatingString());
            if (hasFirstTime()) {
                sb.append('@');
                sb.append(createDateString(firstTime, true));
            }
        } else {
            sb.append(createDateString(startTime, false));
        }

        return sb.toString();
    }

    private String createDateString(Calendar cal, boolean repeating) {
        // shall we use the new format?
        // if no connection is available, we have to use a dummy version
        VDRVersion v = Connection.getVersion();
        if (v == null) {
            v = new VDRVersion("1.0.0");
        }

        int version = v.getMajor();
        int major = v.getMinor();
        int minor = v.getRevision();

		//FIXME which format for which version (repeating timers)
        boolean newFormat = (version == 1 && major >= 3 && minor >= 23) | isRepeating();

        String date = "";
        if (newFormat) {
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String dayString = day < 10 ? ("0" + day) : Integer.toString(day);
            int month = cal.get(Calendar.MONTH) + 1;
            String monthString = month < 10 ? ("0" + month) : Integer
                    .toString(month);
            date = cal.get(Calendar.YEAR) + "-" + monthString + "-" + dayString;
        } else {
            date = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        }

        return date;
    }

    private String createRepeatingString() {
        StringBuffer day = new StringBuffer();
        char c = 'M';

        c = repeatingDays[0] ? 'M' : '-';
        day.append(c);
        c = repeatingDays[1] ? 'T' : '-';
        day.append(c);
        c = repeatingDays[2] ? 'W' : '-';
        day.append(c);
        c = repeatingDays[3] ? 'T' : '-';
        day.append(c);
        c = repeatingDays[4] ? 'F' : '-';
        day.append(c);
        c = repeatingDays[5] ? 'S' : '-';
        day.append(c);
        c = repeatingDays[6] ? 'S' : '-';
        day.append(c);

        return day.toString();
    }

    public String getTvBrowserProgID() {
        return tvBrowserProgID;
    }

    public void setTvBrowserProgID(String tvBrowserProgID) {
        this.tvBrowserProgID = tvBrowserProgID;
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
        this.path = path;
        if (!this.path.endsWith("~")) {
            this.path += "~";
        }
    }

    public String getFile() {
        return (path + title).replaceAll(":", "|");
    }

    public void setFile(String file) {
        if (file.indexOf("~") >= 0) {
            int pos = file.lastIndexOf("~");
            setPath(file.substring(0, pos));
            setTitle(file.substring(pos + 1));
        } else {
            this.path = "";
            this.title = file;
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
}