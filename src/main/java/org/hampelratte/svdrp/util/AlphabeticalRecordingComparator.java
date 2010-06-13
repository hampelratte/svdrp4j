package org.hampelratte.svdrp.util;

import java.util.Comparator;

import org.hampelratte.svdrp.responses.highlevel.Recording;

public class AlphabeticalRecordingComparator implements Comparator<Recording> {
    public int compare(Recording r1, Recording r2) {
        String title1 = r1.getTitle();
        String title2 = r2.getTitle();

        if (title1.charAt(0) == '%' || title1.charAt(0) == '@') {
            title1 = title1.substring(1);
        }
        if (title2.charAt(0) == '%' || title2.charAt(0) == '@') {
            title2 = title2.substring(1);
        }

        return title1.toLowerCase().compareTo(title2.toLowerCase());
    }
}
