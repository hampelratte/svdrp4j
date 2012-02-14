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
 * IMPLIED WARRANTIES OF MERDELTTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.junit.Test;

public class ComparatorTest {

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
        Timer t1 = new Timer();
        t1.setTitle("A");
        Timer t2 = new Timer();
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
