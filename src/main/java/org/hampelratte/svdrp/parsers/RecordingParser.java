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
import java.util.List;

import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.hampelratte.svdrp.responses.highlevel.Recording;

public class RecordingParser extends EPGParser {

    public void parseRecording (Recording recording, String epgData) throws ParseException {
        String epg = addEpgEntryEnd(epgData);
        List<EPGEntry> list = parse(epg);
        if(!list.isEmpty()) {
            EPGEntry entry = list.get(0);
            recording.copyFrom(entry);
            recording.setPriority( ((Recording)entry).getPriority() );
            recording.setLifetime( ((Recording)entry).getLifetime() );
            recording.setDisplayTitle(entry.getTitle());
        } else {
            throw new ParseException("Couldn't parse recording. EPGParser returned an empty list.", -1);
        }
    }

    private String addEpgEntryEnd(String epgData) {
        String[] lines = epgData.split("\n");
        StringBuilder mesg = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i == lines.length - 1) {
                mesg.append("e\n");
            }
            mesg.append(lines[i] + "\n");
        }
        return mesg.toString();
    }

    @Override
    protected void parseLine(String line, List<EPGEntry> list) {
        switch (line.charAt(0)) {
        case 'P':
            ((Recording)epg).setPriority(Integer.parseInt(line.substring(2)));
            break;
        case 'L':
            ((Recording)epg).setLifetime(Integer.parseInt(line.substring(2)));
            break;
        default:
            super.parseLine(line, list);
        }
    }

    @Override
    protected EPGEntry createNewEpgEntry() {
        return new Recording();
    }
}
