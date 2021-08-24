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
package org.hampelratte.svdrp.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parses a list of recordings. That means the response String of an LSTR without parameters.
 * For parsing a single recording, like the response of LSTR 1, use {@link RecordingParser}.
 *
 * @author <a href="mailto:hampelratte@users.berlios.de">hampelratte@users.berlios.de</a>
 * @author <a href="mailto:androvdr@googlemail.com">androvdr</a>
 */
public class RecordingListParser {
	private RecordingListParser() {}

    private static Logger logger = LoggerFactory.getLogger(RecordingListParser.class);

    /**
     * Parses a list of recordings and returns a List of Recordings
     * @param response the response String of a LSTR
     * @return a List of Recordings
     */
    // MAYBE Speed up by using String.indexOf and .substring
    public static List<Recording> parse(String response) {
        ArrayList<Recording> list = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
        StringBuilder title = new StringBuilder();
        StringTokenizer st = new StringTokenizer(response, "\n");
        while(st.hasMoreTokens()) {
            String line = st.nextToken();
            Recording recording = new Recording();
            StringTokenizer st2 = new StringTokenizer(line);
            // parse recording number
            int number = Integer.parseInt(st2.nextToken());
            recording.setId(number);

            // parse start date and time
            String date = st2.nextToken() + " " + st2.nextToken();

            // since VDR 1.7.21 the line also contains the duration of the recording. in this case, the new indicator is
            // included after the duration and not after the date.
            Version v = Connection.getVersion();
            Version version1721 = new Version("1.7.21");
            if (v.compareTo(version1721) < 0) {
                // old format without duration
                if (isNew(date)) {
                    date = date.substring(0, date.length() - 1);
                    recording.setNew(true);
                }
            } else {
                // the vdr version is newer or equal to 1.7.21, we have to parse the new format
                // parse the duration
                String duration = st2.nextToken();
                if (hasError(duration)) {
                	duration = duration.substring(0, duration.length() - 1);
                	recording.setHasError(true);
                }
                if (isNew(duration)) {
                    duration = duration.substring(0, duration.length() - 1);
                    recording.setNew(true);
                }
                String[] parts = duration.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                recording.setDuration(hours * 60 + minutes);
            }

            try {
                recording.setStartTime(df.parse(date));
                Calendar endTime = Calendar.getInstance();
                endTime.setTimeInMillis(recording.getStartTime().getTimeInMillis());
                endTime.add(Calendar.MINUTE, recording.getDuration());
                recording.setEndTime(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // parse title
            title.setLength(0);
            while(st2.hasMoreTokens()) {
                title.append(st2.nextToken());
                title.append(' ');
            }
            recording.setTitle(title.toString().trim());

            // add to result list
            list.add(recording);
        }

        return list;
    }
    
    private static boolean hasError(String token) {
        char c = token.charAt(token.length() - 1);
        return !Character.isDigit(c) && c == '!';
    }

    private static boolean isNew(String token) {
        char c = token.charAt(token.length() - 1);
        if (c == '!') {
        	c = token.charAt(token.length() - 2);
        }
        if (!Character.isDigit(c)) {       	
            if (c == '*' || c == '') {
                return true;
            } else {
                logger.warn("Last character [{}] is not a digit and not one of the \"new\" indicators '*' ''", c);
            }
        }
        return false;
    }
}