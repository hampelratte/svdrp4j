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
package org.hampelratte.svdrp.mock;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.responses.highlevel.Recording;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecordingManager {
    private List<Recording> recordings;

    public RecordingManager() {
        super();
    }

    public void parseData(String data) {
        if (!data.trim().isEmpty()) {
            Connection.setVersion(new Version("2.3.4"));
            recordings = RecordingListParser.parse(data);
        } else {
            recordings = new ArrayList<>();
        }
    }

    public String printRecordingsList() {
        if (recordings != null && !recordings.isEmpty()) {
            StringBuilder response = new StringBuilder();
            for (Iterator<Recording> iterator = recordings.iterator(); iterator.hasNext(); ) {
                Recording recording = iterator.next();
                response.append("250");
                response.append(iterator.hasNext() ? '-' : ' ');
                response.append(recording.toLSTR());
                if (iterator.hasNext()) {
                    response.append('\n');
                }
            }
            return response.toString();
        } else {
            return "550 No recordings defined";
        }
    }

    public Recording getRecording(int id) {
        for (Recording recording : recordings) {
            if (recording.getId() == id) {
                return recording;
            }
        }
        return null;
    }

    public boolean removeRecording(int id) {
        Recording recording = getRecording(id);
        return removeRecording(recording);
    }

    public boolean removeRecording(Recording recording) {
        return recordings.remove(recording);
    }

    public void addRecording(RunningRecording runningRecording) {
        recordings.add(runningRecording);
    }
}
