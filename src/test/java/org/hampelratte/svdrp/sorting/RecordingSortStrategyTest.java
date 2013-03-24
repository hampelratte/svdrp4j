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
package org.hampelratte.svdrp.sorting;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.junit.Test;

public class RecordingSortStrategyTest {

    @Test
    public void testTitleAscending() {
        Recording r1 = new Recording();
        Recording r2 = new Recording();
        Recording r3 = new Recording();
        r1.setTitle("A");
        r2.setTitle("B");
        r3.setTitle("C");

        List<Recording> recs = new ArrayList<Recording>(3);
        recs.add(r2);
        recs.add(r3);
        recs.add(r1);

        RecordingSortStrategy rss = new RecordingSortStrategy(new RecordingAlphabeticalComparator(), true);
        rss.sort(recs);

        assertEquals("A", recs.get(0).getTitle());
        assertEquals("B", recs.get(1).getTitle());
        assertEquals("C", recs.get(2).getTitle());
    }

    @Test
    public void testTitleDescending() {
        Recording r1 = new Recording();
        Recording r2 = new Recording();
        Recording r3 = new Recording();
        r1.setTitle("A");
        r2.setTitle("B");
        r3.setTitle("C");

        List<Recording> recs = new ArrayList<Recording>(3);
        recs.add(r2);
        recs.add(r3);
        recs.add(r1);

        RecordingSortStrategy rss = new RecordingSortStrategy(new RecordingAlphabeticalComparator(), false);
        rss.sort(recs);

        assertEquals("C", recs.get(0).getTitle());
        assertEquals("B", recs.get(1).getTitle());
        assertEquals("A", recs.get(2).getTitle());
    }
}
