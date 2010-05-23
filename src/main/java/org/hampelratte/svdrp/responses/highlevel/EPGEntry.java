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

import java.util.Calendar;

public class EPGEntry {

    private String channelID = "";

    private String channelName = "";

    private int eventID = 0;

    private long startTime;
    private Calendar startTimeCal;

    private long endTime;
    private Calendar endTimeCal;

    private String tableID = "";
    
    private String version = "";

    private String title = "";

    private String shortText = "";

    private String description = "";

    private int stream = 1;

    private String streamType = "";

    private String language = "";

    private long vpsTime;
    private Calendar vpsTimeCal;

    public EPGEntry() {
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getEndTime() {
        if(endTimeCal == null) {
            endTimeCal = Calendar.getInstance();
            endTimeCal.setTimeInMillis(endTime);
        }
        return endTimeCal;
    }

    /**
     * Set the end time of the programm as unix timestamp
     * @param endTime as unix timestamp
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public Calendar getStartTime() {
        if(startTimeCal == null) {
            startTimeCal = Calendar.getInstance();
            startTimeCal.setTimeInMillis(startTime);
        }
        return startTimeCal;
    }

    /**
     * Set the start time of the programm as unix timestamp
     * @param startTime as unix timestamp
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getStream() {
        return stream;
    }

    public void setStream(int stream) {
        this.stream = stream;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getVpsTime() {
        if(vpsTimeCal == null) {
            vpsTimeCal = Calendar.getInstance();
            vpsTimeCal.setTimeInMillis(vpsTime);
        }
        return vpsTimeCal;
    }

    /**
     * Set the end time of the programm as unix timestamp
     * @param vpsTime as unix timestamp
     */
    public void setVpsTime(long vpsTime) {
        this.vpsTime = vpsTime;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString() {
        return getTitle() + " starts: " + getStartTime().get(Calendar.HOUR_OF_DAY)
                + ":" + getStartTime().get(Calendar.MINUTE) + " ends: "
                + getEndTime().get(Calendar.HOUR_OF_DAY) + ":"
                + getEndTime().get(Calendar.MINUTE);
    }
}