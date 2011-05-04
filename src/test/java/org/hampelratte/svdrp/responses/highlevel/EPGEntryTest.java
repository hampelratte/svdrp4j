package org.hampelratte.svdrp.responses.highlevel;

import java.util.Calendar;

import static junit.framework.Assert.*;

import org.junit.Test;

public class EPGEntryTest {
    private EPGEntry epg;
    private Calendar now;
    private Calendar then;
    
    public EPGEntryTest() {
        epg = new EPGEntry();
        now = Calendar.getInstance();
        then = Calendar.getInstance();
        then.add(Calendar.DAY_OF_MONTH, 1);
    }
    
    @Test
    public void testResetStartTimeCalendar() {
        // ensure that the initial calendar is set correctly
        epg.setStartTime(now);
        assertEquals(now.getTimeInMillis(), epg.getStartTime().getTimeInMillis());
        
        // test with Calendar parameter
        epg.setStartTime(then);
        assertEquals(then.getTimeInMillis(), epg.getStartTime().getTimeInMillis());
    }
    
    @Test
    public void testResetStartTimeLong() {
        // ensure that the initial calendar is set correctly
        epg.setStartTime(now);
        assertEquals(now.getTimeInMillis(), epg.getStartTime().getTimeInMillis());
        
        // test with Calendar parameter
        epg.setStartTime(then.getTimeInMillis());
        assertEquals(then.getTimeInMillis(), epg.getStartTime().getTimeInMillis());
    }
    
    @Test
    public void testResetEndTimeCalendar() {
        // ensure that the initial calendar is set correctly
        epg.setEndTime(now);
        assertEquals(now.getTimeInMillis(), epg.getEndTime().getTimeInMillis());
        
        // test with Calendar parameter
        epg.setEndTime(then);
        assertEquals(then.getTimeInMillis(), epg.getEndTime().getTimeInMillis());
    }
    
    @Test
    public void testResetEndTimeLong() {
        // ensure that the initial calendar is set correctly
        epg.setEndTime(now);
        assertEquals(now.getTimeInMillis(), epg.getEndTime().getTimeInMillis());
        
        // test with Calendar parameter
        epg.setEndTime(then.getTimeInMillis());
        assertEquals(then.getTimeInMillis(), epg.getEndTime().getTimeInMillis());
    }
    
    @Test
    public void testResetVpsTimeCalendar() {
        // ensure that the initial calendar is set correctly
        epg.setVpsTime(now);
        assertEquals(now.getTimeInMillis(), epg.getVpsTime().getTimeInMillis());
        
        // test with Calendar parameter
        epg.setVpsTime(then);
        assertEquals(then.getTimeInMillis(), epg.getVpsTime().getTimeInMillis());
    }
    
    @Test
    public void testResetVpsTimeLong() {
        // ensure that the initial calendar is set correctly
        epg.setVpsTime(now);
        assertEquals(now.getTimeInMillis(), epg.getVpsTime().getTimeInMillis());
        
        // test with Calendar parameter
        epg.setVpsTime(then.getTimeInMillis());
        assertEquals(then.getTimeInMillis(), epg.getVpsTime().getTimeInMillis());
    }
}
