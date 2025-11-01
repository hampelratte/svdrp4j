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

import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimerManager {
    private List<Timer> timers;

    public void parseData(String data) {
        if (!data.trim().isEmpty()) {
            timers = TimerParser.parse(data);
        } else {
            timers = new ArrayList<>();
        }
    }

    public String printTimersList() {
        if (timers != null && !timers.isEmpty()) {
            StringBuilder response = new StringBuilder();
            for (Iterator<Timer> iterator = timers.iterator(); iterator.hasNext(); ) {
                Timer timer = iterator.next();
                response.append("250");
                response.append(iterator.hasNext() ? '-' : ' ');
                response.append(timer.getID()).append(' ').append(timer.toNEWT());
                if (iterator.hasNext()) {
                    response.append('\n');
                }
            }
            return response.toString();
        } else {
            return "550 No timers defined";
        }
    }

    public int addTimer(Timer timer) {
        timers.add(timer);
        if (timer.getID() == 0) {
            int id = timers.size();
            timer.setID(id);
        }
        return timer.getID();
    }

    public boolean removeTimer(int id) {
        Timer timer = getTimer(id);
        if (timer.hasState(Timer.ACTIVE) && timer.hasState(Timer.RECORDING)) {
            throw new IllegalStateException("Timer is recording");
        }
        return removeTimer(timer);
    }

    public boolean removeTimer(Timer timer) {
        return removeAndRenumber(timer);
    }

    private boolean removeAndRenumber(Timer timer) {
        boolean removed = timers.remove(timer);
        if (removed) {
            renumberTimers();
        }
        return removed;
    }

    private void renumberTimers() {
        for (int i = 0; i < timers.size(); i++) {
            Timer timer = timers.get(i);
            timer.setID(i + 1);
        }
    }

    public Timer getTimer(int id) {
        for (Timer timer : timers) {
            if (timer.getID() == id) {
                return timer;
            }
        }
        return null;
    }

    public void modifyTimer(Timer modifiedTimer) {
        int index = -1;
        for (int i = 0; i < timers.size(); i++) {
            Timer t = timers.get(i);
            if (t.getID() == modifiedTimer.getID()) {
                index = i;
            }
        }

        if (index >= 0) {
            timers.set(index, modifiedTimer);
        } else {
            throw new RuntimeException("Timer with ID " + modifiedTimer.getID() + " not found");
        }
    }
}
