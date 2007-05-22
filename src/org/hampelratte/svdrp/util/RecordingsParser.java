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
package org.hampelratte.svdrp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hampelratte.svdrp.responses.highlevel.Recording;


public class RecordingsParser {
    
    /**
     * Parses a list of recordings and returns a List of Recordings
     * @param response the response String of a LSTR
     * @return a List of Recordings 
     */
    public static List<Recording> parse(String response) {
        ArrayList<Recording> list = new ArrayList<Recording>();
        StringTokenizer st = new StringTokenizer(response, "\n");
        while(st.hasMoreTokens()) {
            String line = st.nextToken();
            Recording recording = new Recording();
            StringTokenizer st2 = new StringTokenizer(line);
            // parse recording number
            int number = Integer.parseInt(st2.nextToken());
            recording.setNumber(number);
            
            // parse start date and time
            String date = st2.nextToken() + " " + st2.nextToken();
            if(date.endsWith("*")) {
                recording.setNew(true);
                date = date.substring(0, date.length()-1);
            }

            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
            try {
                recording.setStartTime(df.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            // parse title
            StringBuffer title = new StringBuffer();
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
}