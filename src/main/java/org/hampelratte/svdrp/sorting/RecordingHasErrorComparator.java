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

import java.util.Comparator;

import org.hampelratte.svdrp.responses.highlevel.Recording;

/**
 * Sorts Recordings by their "error" status. A recording with errors is treated as "less than" a regular recording. If both have the same status, they are compared with
 * the RecordingAlphabeticalComparator.
 * 
 * @author <a href="mailto:hampelratte@users.berlios.de">hampelratte@users.berlios.de</a>
 */
public class RecordingHasErrorComparator implements Comparator<Recording> {

    private final RecordingAlphabeticalComparator alphabet = new RecordingAlphabeticalComparator();

    @Override
    public int compare(Recording r1, Recording r2) {
        if (r1.hasError()) {
            if (r2.hasError()) {
                return alphabet.compare(r1, r2);
            } else {
                return -1;
            }
        } else if (r2.hasError()) {
            return 1;
        } else {
            return alphabet.compare(r1, r2);
        }
    }
}
