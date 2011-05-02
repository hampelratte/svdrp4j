/* $Id$
 * 
 * Copyright (c) Henrik Niehaus & Lazy Bones development team
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;


/**
 * Parses a list of timers received from VDR by the LSTT command
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 *
 */
//FIXME für repeating timer die start und endzeit richtig setzen
public class TimerParser {
    /**
     * Parses a list of timers received from VDR by the LSTT command
     * @param timerData A list of timers received from VDR by LSTT command
     * @return A list of VDRTimer objects
     */
    public static List<VDRTimer> parse(String timerData) {
        ArrayList<VDRTimer> list = new ArrayList<VDRTimer>();
        StringTokenizer st1 = new StringTokenizer(timerData, "\n");
        while (st1.hasMoreTokens()) {
            VDRTimer timer = new VDRTimer();
            String line = st1.nextToken();
            int pos = line.indexOf(" ");
            String id = line.substring(0, pos);
            String restOfLine = line.substring(pos + 1);
            StringTokenizer st = new StringTokenizer(restOfLine, ":");
            String active = st.nextToken();
            String channel = st.nextToken();
            String day = st.nextToken();
            String starttime = st.nextToken();
            String endtime = st.nextToken();
            parseDay(timer, day, starttime, endtime);
            String priority = st.nextToken();
            String lifetime = st.nextToken();
            String file = st.nextToken();
            String desc = "";
            while (st.hasMoreTokens()) {
                desc += st.nextToken();
                if(st.hasMoreTokens()) {
                    desc += ":";
                }
            }
            desc = desc.replaceAll("\\|", "\n");
            timer.setID(Integer.parseInt(id));
            timer.setState(Integer.parseInt(active));
            timer.setChannelNumber(Integer.parseInt(channel));
            timer.setPriority(Integer.parseInt(priority));
            timer.setLifetime(Integer.parseInt(lifetime));
            timer.setFile(file);
            timer.setDescription(desc);
            list.add(timer);
        }
        return list;
    }

    private static void parseTime(Calendar time, String timeString) {
        int hours = Integer.parseInt(timeString.substring(0, 2));
        int minutes = Integer.parseInt(timeString.substring(2));
        time.set(Calendar.HOUR_OF_DAY, hours);
        time.set(Calendar.MINUTE, minutes);
    }

    /**
     * Parses the day of a timer. this can have the following formats: 19 (1-31)
     * 2005-03-19 MTWTFSS MTWTFSS@19 MTWTFSS@2005-03-19
     * The results will be stored in the passed timer object
     */
    private static void parseDay(VDRTimer timer, String day,
            String startString, String endString) {
        Calendar startTime = timer.getStartTime();
        Calendar endTime = timer.getEndTime();
        Calendar firstTime = timer.getFirstTime();

        // 19 (1-31)
        Pattern dayPattern = Pattern.compile("([1-9]|[12][0-9]|3[01])");

        // 2005-03-19
        Pattern datePattern = Pattern.compile("((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])");

        // MTWTFSS
        Pattern simpleRepeating = Pattern.compile("(\\p{Upper}|-){7}");

        // MTWTFSS@19 (1-31)
        Pattern repeatingAtShort = Pattern.compile("((?:\\p{Upper}|-){7})@(0?[1-9]|[12][0-9]|3[01])");

        // MTWTFSS@2005-03-19
        Pattern repeatingAt = Pattern.compile("((?:\\p{Upper}|-){7})@((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])");

        Matcher matcher;
        int day_of_month;
        if ((matcher = dayPattern.matcher(day)).matches()) {
            day_of_month = Integer.parseInt(day);
            int today = startTime.get(Calendar.DAY_OF_MONTH);
            startTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            endTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            if (day_of_month < today) {
                startTime.add(Calendar.MONTH, 1);
                endTime.add(Calendar.MONTH, 1);
            }
            TimerParser.parseTime(startTime, startString);
            TimerParser.parseTime(endTime, endString);
            // if the endTime lasts into the next day, we have to add 1 to the
            // DAY_OF_MONTH
            if (endTime.get(Calendar.HOUR_OF_DAY) < startTime
                    .get(Calendar.HOUR_OF_DAY)) {
                endTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else if ((matcher = datePattern.matcher(day)).matches()) {
            day_of_month = Integer.parseInt(matcher.group(3));
            int month = Integer.parseInt(matcher.group(2)) - 1;
            int year = Integer.parseInt(matcher.group(1));
            TimerParser.parseTime(startTime, startString);
            TimerParser.parseTime(endTime, endString);
            startTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            startTime.set(Calendar.MONTH, month);
            startTime.set(Calendar.YEAR, year);
            endTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            endTime.set(Calendar.MONTH, month);
            endTime.set(Calendar.YEAR, year);
            // if the endTime lasts into the next day, we have to add 1 to the
            // DAY_OF_MONTH
            if (endTime.get(Calendar.HOUR_OF_DAY) < startTime
                    .get(Calendar.HOUR_OF_DAY)) {
                endTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else if ((matcher = simpleRepeating.matcher(day)).matches()) {
            timer.setRepeatingDays(TimerParser.determineDays(day));
            day_of_month = -1;
            TimerParser.parseTime(startTime, startString);
            TimerParser.parseTime(endTime, endString);
            // if the endTime lasts into the next day, we have to add 1 to the
            // DAY_OF_MONTH
            if (endTime.get(Calendar.HOUR_OF_DAY) < startTime
                    .get(Calendar.HOUR_OF_DAY)) {
                endTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else if ((matcher = repeatingAtShort.matcher(day)).matches()) {
            timer.setHasFirstTime(true);
            String days = matcher.group(1);
            timer.setRepeatingDays(TimerParser.determineDays(days));
            day_of_month = Integer.parseInt(matcher.group(2));
            firstTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            startTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            endTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            TimerParser.parseTime(startTime, startString);
            TimerParser.parseTime(endTime, endString);
            // if the endTime lasts into the next day, we have to add 1 to the
            // DAY_OF_MONTH
            if (endTime.get(Calendar.HOUR_OF_DAY) < startTime
                    .get(Calendar.HOUR_OF_DAY)) {
                endTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else if ((matcher = repeatingAt.matcher(day)).matches()) {
            timer.setHasFirstTime(true);
            String days = matcher.group(1);
            timer.setRepeatingDays(TimerParser.determineDays(days));

            day_of_month = Integer.parseInt(matcher.group(4));
            int month = Integer.parseInt(matcher.group(3)) - 1;
            int year = Integer.parseInt(matcher.group(2));
            TimerParser.parseTime(startTime, startString);
            TimerParser.parseTime(endTime, endString);
            firstTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            firstTime.set(Calendar.MONTH, month);
            firstTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            startTime.set(Calendar.MONTH, month);
            startTime.set(Calendar.YEAR, year);
            endTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            endTime.set(Calendar.MONTH, month);
            endTime.set(Calendar.YEAR, year);
            // if the endTime lasts into the next day, we have to add 1 to the
            // DAY_OF_MONTH
            if (endTime.get(Calendar.HOUR_OF_DAY) < startTime
                    .get(Calendar.HOUR_OF_DAY)) {
                endTime.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private static boolean[] determineDays(String days) {
        boolean[] d = new boolean[days.length()];
        char[] chars = days.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            d[i] = chars[i] != '-';
        }
        return d;
    }
}
