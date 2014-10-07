package org.hampelratte.svdrp.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Timer;

public class TimerManager {
    private List<Timer> timers;

    public void parseData(String data) {
        if (!data.trim().isEmpty()) {
            timers = TimerParser.parse(data);
        } else {
            timers = new ArrayList<Timer>();
        }
    }

    public String printTimersList() {
        if (timers != null && timers.size() > 0) {
            StringBuilder response = new StringBuilder();
            for (Iterator<Timer> iterator = timers.iterator(); iterator.hasNext();) {
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
        int id = timers.size();
        timer.setID(id);
        return id;
    }

    public boolean removeTimer(int id) {
        Timer timer = getTimer(id);
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
        for (Iterator<Timer> iterator = timers.iterator(); iterator.hasNext();) {
            Timer timer = iterator.next();
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
