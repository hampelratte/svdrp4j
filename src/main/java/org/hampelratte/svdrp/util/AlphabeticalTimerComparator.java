package org.hampelratte.svdrp.util;

import java.util.Comparator;

import org.hampelratte.svdrp.responses.highlevel.VDRTimer;

public class AlphabeticalTimerComparator implements Comparator<VDRTimer> {
    public int compare(VDRTimer t1, VDRTimer t2) {
        String title1 = t1.getTitle();
        String title2 = t2.getTitle();

        return title1.toLowerCase().compareTo(title2.toLowerCase());
    }
}
