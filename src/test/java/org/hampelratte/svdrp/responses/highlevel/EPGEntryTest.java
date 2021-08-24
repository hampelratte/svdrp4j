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
package org.hampelratte.svdrp.responses.highlevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

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

    @Test
    public void testToString() {
        try {
			// call toString to make sure, no NPE is fired
			epg = new EPGEntry();
			epg.toString();
		} catch (Exception e) {
			fail();
		}
    }
}
