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
package de.hampelratte.svdrp.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import de.hampelratte.svdrp.responses.highlevel.EPGEntry;

public class EPGParser {

    public static List parse(String epgData) {
        ArrayList list = new ArrayList();
        StringTokenizer st = new StringTokenizer(epgData, "\n");
        EPGEntry epg = null;
        String currentChannelID = null, currentChannelName = null;
        String[] parts;
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            if (line.startsWith("End")) {
                return list;
            }
            switch (line.charAt(0)) {
            case 'C':
                /* Channel */
                parts = line.split(" ", 3);
                currentChannelID = parts[1];
                currentChannelName = parts[2];
                break;

            case 'E':
                /* EPG start */
                epg = new EPGEntry();
                epg.setChannelID(currentChannelID);
                epg.setChannelName(currentChannelName);

                parts = line.split(" ");
                epg.setEventID(Integer.parseInt(parts[1]));
                int startTime = Integer.parseInt(parts[2]);
                int duration = Integer.parseInt(parts[3]);
                int endTime = startTime + duration;
                Calendar calStartTime = GregorianCalendar.getInstance();
                calStartTime.setTimeInMillis(startTime * 1000L);
                epg.setStartTime(calStartTime);
                Calendar calEndTime = GregorianCalendar.getInstance();
                calEndTime.setTimeInMillis(endTime * 1000L);
                epg.setEndTime(calEndTime);
                epg.setTableID(parts[4]);
                break;
            case 'T':
                /* Title */
                epg.setTitle(line.substring(2));
                break;
            case 'S':
                /* Short text */
                String shorttext = line.substring(2).replaceAll("\\|", "\n");
                epg.setShortText(shorttext);
                break;
            case 'D':
                /* Description */
                String desc = line.substring(2).replaceAll("\\|", "\n");
                epg.setDescription(desc);
                break;
            case 'e':
                /* end of Entry */
                list.add(epg);
                break;
            case 'c':
                /* end of Channel (multiple entries) */
                break;
            default:
                break;
            }
        }
        return list;
    }
}
