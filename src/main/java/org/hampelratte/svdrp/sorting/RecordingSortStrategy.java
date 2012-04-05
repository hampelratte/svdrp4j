package org.hampelratte.svdrp.sorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hampelratte.svdrp.responses.highlevel.Recording;

public class RecordingSortStrategy {
    private final Comparator<Recording> comparator;
    private final boolean ascending;

    public RecordingSortStrategy(Comparator<Recording> comparator, boolean ascending) {
        super();
        this.comparator = comparator;
        this.ascending = ascending;
    }

    public List<Recording> sort(List<Recording> recordings) {
        Collections.sort(recordings, comparator);
        if (!ascending) {
            Collections.reverse(recordings);
        }
        return recordings;
    }
}
