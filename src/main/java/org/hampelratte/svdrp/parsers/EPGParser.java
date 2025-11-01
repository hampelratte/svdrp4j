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

import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.hampelratte.svdrp.responses.highlevel.Genre;
import org.hampelratte.svdrp.responses.highlevel.GenreTable;
import org.hampelratte.svdrp.responses.highlevel.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class EPGParser {
    private final GenreTable genreTable = new GenreTable();
    protected String currentChannelID;
    protected String currentChannelName;
    protected EPGEntry epg;
    protected List<EPGEntry> list;

    public List<EPGEntry> parse(String epgData) {
        currentChannelID = null;
        currentChannelName = "";
        epg = null;
        list = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(epgData, "\n");
        while (st.hasMoreTokens()) {
            String line = st.nextToken();
            if (line.startsWith("End")) {
                return list;
            }
            parseLine(line, list);
        }
        return list;
    }

    protected void parseLine(String line, List<EPGEntry> list) {
        String desc;
        StringTokenizer lineTokenizer;
        switch (line.charAt(0)) {
            case 'C':
                /* Channel */
                int start = line.indexOf(' ') + 1;
                int stop = line.indexOf(' ', start + 1);
                stop = stop > 0 ? stop : line.length();
                currentChannelID = line.substring(start, stop);
                if (stop < line.length()) {
                    start = stop + 1;
                    currentChannelName = line.substring(start);
                }
                break;
            case 'E':
                /* EPG start */
                epg = createNewEpgEntry();
                epg.setChannelID(currentChannelID);
                epg.setChannelName(currentChannelName);

                lineTokenizer = new StringTokenizer(line, " ");
                lineTokenizer.nextToken(); // skip the e
                epg.setEventID(Long.parseLong(lineTokenizer.nextToken()));
                int startTime = Integer.parseInt(lineTokenizer.nextToken());
                int duration = Integer.parseInt(lineTokenizer.nextToken());
                int endTime = startTime + duration;
                epg.setStartTime(startTime * 1000L);
                epg.setEndTime(endTime * 1000L);
                epg.setTableID(Integer.parseInt(lineTokenizer.nextToken(), 16));
                if (lineTokenizer.hasMoreElements()) {
                    epg.setVersion(Integer.parseInt(lineTokenizer.nextToken(), 16));
                }
                break;
            case 'T':
                /* Title */
                epg.setTitle(line.substring(2));
                break;
            case 'S':
                /* Short text */
                String shortText = line.substring(2).replace('|', '\n');
                epg.setShortText(shortText);
                break;
            case 'D':
                /* Description */
                desc = line.substring(2).replace('|', '\n');
                epg.setDescription(desc);
                break;
            case 'V':
                /* VPS */
                int vps = Integer.parseInt(line.substring(2));
                epg.setVpsTime(vps * 1000L);
                break;
            case 'X':
                lineTokenizer = new StringTokenizer(line, " ");
                lineTokenizer.nextToken(); // skip the X
                int content = Integer.parseInt(lineTokenizer.nextToken(), 16);
                int type = Integer.parseInt(lineTokenizer.nextToken(), 16);
                String iso3code = lineTokenizer.nextToken();

                // parse the description, if available
                desc = "N/A";
                if (lineTokenizer.hasMoreElements()) {
                    StringBuilder sb = new StringBuilder(lineTokenizer.nextToken());
                    while (lineTokenizer.hasMoreElements()) {
                        sb.append(' ').append(lineTokenizer.nextToken());
                    }
                    desc = sb.toString();
                }

                Stream stream = new Stream();
                switch (content) {
                    case 1:
                        stream.setContent(Stream.CONTENT.MP2V);
                        break;
                    case 2:
                        stream.setContent(Stream.CONTENT.MP2A);
                        break;
                    case 3:
                        stream.setContent(Stream.CONTENT.SUBTITLE);
                        break;
                    case 4:
                        stream.setContent(Stream.CONTENT.AC3);
                        break;
                    case 5:
                        stream.setContent(Stream.CONTENT.H264);
                        break;
                    case 6:
                        stream.setContent(Stream.CONTENT.HEAAC);
                        break;
                    default:
                        stream.setContent(Stream.CONTENT.UNKNOWN);
                        break;
                }
                stream.setType(type);
                stream.setLanguage(iso3code);
                stream.setDescription(desc);
                epg.getStreams().add(stream);
                break;
            case 'G':
                lineTokenizer = new StringTokenizer(line, " ");
                lineTokenizer.nextToken(); // skip the G
                while (lineTokenizer.hasMoreElements()) {
                    int genreCode = Integer.parseInt(lineTokenizer.nextToken(), 16);
                    Genre genre = genreTable.get(genreCode);
                    if (genre != null) {
                        epg.getGenres().add(genre);
                    }
                }
                break;
            case 'e':
                /* end of Entry */
                list.add(epg);
                epg = null;
                break;
            case 'c':
                /* end of Channel (multiple entries) */
                break;
            default:
                break;
        }
    }

    protected EPGEntry createNewEpgEntry() {
        return new EPGEntry();
    }
}
