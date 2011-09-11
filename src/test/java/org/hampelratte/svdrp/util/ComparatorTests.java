package org.hampelratte.svdrp.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.VDRTimer;
import org.junit.Test;

public class ComparatorTests {
    
    @Test
    public void testAlphabeticalRecordingComparator() {
        AlphabeticalRecordingComparator comp = new AlphabeticalRecordingComparator();
        Recording r1 = new Recording();
        r1.setTitle("A");
        Recording r2 = new Recording();
        r2.setTitle("B");
        
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));
        r2.setTitle("A");
        assertEquals(0, comp.compare(r2, r1));
        assertEquals(0, comp.compare(r1, r2));
    }
    
    @Test
    public void testAlphabeticalTimerComparator() {
        AlphabeticalTimerComparator comp = new AlphabeticalTimerComparator();
        VDRTimer t1 = new VDRTimer();
        t1.setTitle("A");
        VDRTimer t2 = new VDRTimer();
        t2.setTitle("B");
        
        assertEquals(-1, comp.compare(t1, t2));
        assertEquals(1, comp.compare(t2, t1));
        t2.setTitle("A");
        assertEquals(0, comp.compare(t2, t1));
        assertEquals(0, comp.compare(t1, t2));
    }
    
    @Test
    public void testChronologicalTimerComparator() {
        ChronologicalTimerComparator comp = new ChronologicalTimerComparator();
        VDRTimer t1 = new VDRTimer();
        t1.getStartTime().add(Calendar.HOUR_OF_DAY, -1);
        VDRTimer t2 = new VDRTimer();
        
        assertEquals(-1, comp.compare(t1, t2));
        assertEquals(1, comp.compare(t2, t1));
        t2.getStartTime().setTimeInMillis(t1.getStartTime().getTimeInMillis());
        assertEquals(0, comp.compare(t2, t1));
        assertEquals(0, comp.compare(t1, t2));
    }
}
