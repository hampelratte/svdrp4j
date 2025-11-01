package org.hampelratte.svdrp.mock;

import org.hampelratte.svdrp.responses.highlevel.Recording;

import java.io.Serial;
import java.util.Calendar;

public class RunningRecording extends Recording {

    @Serial
    private static final long serialVersionUID = -2809310342205108862L;

    public RunningRecording() {
        setId(999);
        setTitle("RunningRecording");
        setLifetime(99);
        setPriority(99);
        Calendar startTime = Calendar.getInstance();
        startTime.add(Calendar.MINUTE, -30);
        setStartTime(startTime);
        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(startTime.getTimeInMillis());
        endTime.add(Calendar.MINUTE, 30);
        setEndTime(endTime);
        setDuration(60);
        setNew(true);
    }
}
