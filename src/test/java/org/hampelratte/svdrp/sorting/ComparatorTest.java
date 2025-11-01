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
package org.hampelratte.svdrp.sorting;

import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparatorTest {

    @Test
    void testRecordingAlphabeticalComparator() {
        RecordingAlphabeticalComparator comp = new RecordingAlphabeticalComparator();
        Recording r1 = new Recording();
        Recording r2 = new Recording();

        r1.setTitle("A");
        r2.setTitle("B");
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));

        r1.setTitle("A");
        r2.setTitle("A");
        assertEquals(0, comp.compare(r2, r1));
        assertEquals(0, comp.compare(r1, r2));

        r1.setTitle("ABC~XYZ");
        r2.setTitle("BCD~ABC");
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));

        r1.setTitle("ABC");
        r2.setTitle("ABC~XYZ");
        assertTrue(comp.compare(r1, r2) < 0);
        assertTrue(comp.compare(r2, r1) > 0);
    }

    @Test
    void testRecordingStartTimeComparator() {
        RecordingStartTimeComparator comp = new RecordingStartTimeComparator();

        Calendar now = Calendar.getInstance();
        Calendar yesterday = (Calendar) now.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        Recording r1 = new Recording();
        r1.setTitle("B");
        r1.setStartTime(yesterday);
        Recording r2 = new Recording();
        r2.setTitle("A");
        r2.setStartTime(now);

        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));
        r2.setStartTime(yesterday);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));
    }

    @Test
    void testRecordingIsCutComparator() {
        RecordingIsCutComparator comp = new RecordingIsCutComparator();

        Recording r1 = new Recording();
        Recording r2 = new Recording();

        // r1 is cut
        r1.setTitle("%B");
        r2.setTitle("A");
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));

        // r2 is cut
        r1.setTitle("B");
        r2.setTitle("%A");
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));

        // both are cut
        r1.setTitle("%B");
        r2.setTitle("%A");
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));

        // both are uncut
        r1.setTitle("B");
        r2.setTitle("A");
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));
    }

    @Test
    void testRecordingIsNewComparator() {
        RecordingIsNewComparator comp = new RecordingIsNewComparator();

        Calendar now = Calendar.getInstance();
        Calendar yesterday = (Calendar) now.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        // only r1 new
        Recording r1 = new Recording();
        r1.setTitle("B");
        r1.setNew(true);
        r1.setStartTime(yesterday);
        Recording r2 = new Recording();
        r2.setTitle("A");
        r2.setNew(false);
        r2.setStartTime(now);
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));

        // both new
        r1.setNew(true);
        r2.setNew(true);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));

        // only r2 new
        r1.setNew(false);
        r2.setNew(true);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));

        // both not new
        r1.setNew(false);
        r2.setNew(false);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));
    }

    @Test
    void testRecordingHasErrorComparator() {
        RecordingHasErrorComparator comp = new RecordingHasErrorComparator();
        Recording r1 = new Recording();
        r1.setTitle("B");
        Recording r2 = new Recording();
        r2.setTitle("A");

        // only r1 error
        r1.setHasError(true);
        r2.setHasError(false);
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));

        // both error
        r1.setHasError(true);
        r2.setHasError(true);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));

        // only r2 error
        r1.setHasError(false);
        r2.setHasError(true);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));

        // both not error
        r1.setHasError(false);
        r2.setHasError(false);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));
    }

    @Test
    void testRecordingLengthComparator() {
        RecordingLengthComparator comp = new RecordingLengthComparator();
        Recording r1 = new Recording();
        r1.setTitle("B");
        Recording r2 = new Recording();
        r2.setTitle("A");

        // r1 < r2
        r1.setDuration(1);
        r2.setDuration(2);
        assertEquals(-1, comp.compare(r1, r2));
        assertEquals(1, comp.compare(r2, r1));

        // r1 == r2
        r1.setDuration(1);
        r2.setDuration(1);
        assertEquals(0, comp.compare(r1, r2));
        assertEquals(0, comp.compare(r2, r1));

        // r1 > r2
        r1.setDuration(2);
        r2.setDuration(1);
        assertEquals(1, comp.compare(r1, r2));
        assertEquals(-1, comp.compare(r2, r1));
    }

    @Test
    void testAlphabeticalTimerComparator() {
        AlphabeticalTimerComparator comp = new AlphabeticalTimerComparator();
        Timer t1 = new Timer();
        Timer t2 = new Timer();

        t1.setTitle("A");
        t2.setTitle("B");
        assertEquals(-1, comp.compare(t1, t2));
        assertEquals(1, comp.compare(t2, t1));

        t1.setTitle("A");
        t2.setTitle("A");
        assertEquals(0, comp.compare(t2, t1));
        assertEquals(0, comp.compare(t1, t2));
    }

    @Test
    void testChronologicalTimerComparator() {
        ChronologicalTimerComparator comp = new ChronologicalTimerComparator();
        Timer t1 = new Timer();
        t1.getStartTime().add(Calendar.HOUR_OF_DAY, -1);
        Timer t2 = new Timer();

        assertEquals(-1, comp.compare(t1, t2));
        assertEquals(1, comp.compare(t2, t1));
        t2.getStartTime().setTimeInMillis(t1.getStartTime().getTimeInMillis());
        assertEquals(0, comp.compare(t2, t1));
        assertEquals(0, comp.compare(t1, t2));
    }
}
