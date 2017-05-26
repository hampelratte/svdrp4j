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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.parsers.RecordingListParser;
import org.hampelratte.svdrp.responses.highlevel.Recording;

public class RecordingManager {
    private List<Recording> recordings;

    public void parseData(String data) {
        if (!data.trim().isEmpty()) {
            Connection.setVersion(new Version("2.3.4"));
            recordings = RecordingListParser.parse(data);
        } else {
            recordings = new ArrayList<Recording>();
        }
    }

    public String printRecordingsList() {
        if (recordings != null && recordings.size() > 0) {
            StringBuilder response = new StringBuilder();
            for (Iterator<Recording> iterator = recordings.iterator(); iterator.hasNext();) {
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

    //    public int addTimer(Timer timer) {
    //        recordings.add(timer);
    //        int id = recordings.size();
    //        timer.setID(id);
    //        return id;
    //    }
    //


    public Recording getRecording(int id) {
        for (Iterator<Recording> iterator = recordings.iterator(); iterator.hasNext();) {
            Recording recording = iterator.next();
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
    // private boolean removeAndRenumber(Timer timer) {
    //        boolean removed = recordings.remove(timer);
    //        if (removed) {
    //            renumberTimers();
    //        }
    //        return removed;
    //    }
    //
    //    private void renumberTimers() {
    //        for (int i = 0; i < recordings.size(); i++) {
    //            Timer timer = recordings.get(i);
    //            timer.setID(i + 1);
    //        }
    //    }

    //
    //    public void modifyTimer(Timer modifiedTimer) {
    //        int index = -1;
    //        for (int i = 0; i < recordings.size(); i++) {
    //            Timer t = recordings.get(i);
    //            if (t.getID() == modifiedTimer.getID()) {
    //                index = i;
    //            }
    //        }
    //
    //        if (index >= 0) {
    //            recordings.set(index, modifiedTimer);
    //        } else {
    //            throw new RuntimeException("Timer with ID " + modifiedTimer.getID() + " not found");
    //        }
    //    }
}
