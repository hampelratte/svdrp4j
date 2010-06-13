package org.hampelratte.svdrp.util;

import java.util.Comparator;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;

public class ChronologicalTimerComparator implements Comparator<VDRTimer> {
    public int compare(VDRTimer t1, VDRTimer t2) {
        return t1.getStartTime().compareTo(t2.getStartTime());
    }
}
