package org.hampelratte.svdrp.util;

import java.util.Comparator;

import org.hampelratte.svdrp.responses.highlevel.Recording;

public class AlphabeticalRecordingComparator implements Comparator<Recording> {
    public int compare(Recording r1, Recording r2) {
        String title1 = r1.getDisplayTitle();
        String title2 = r2.getDisplayTitle();
        return title1.toLowerCase().compareTo(title2.toLowerCase());
    }
}
