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
package org.hampelratte.svdrp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VersionTest {

    @Test
    void testConstructor() {
        Version v = new Version("1.7.14");
        assertEquals(1, v.getMajor());
        assertEquals(7, v.getMinor());
        assertEquals(14, v.getRevision());
    }

    @Test
    void testInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> new Version("InvalidVersion"));
    }

    @Test
    void testSetter() {
        Version v = new Version("1.7.14");
        v.setMajor(2);
        v.setMinor(8);
        v.setRevision(15);

        assertEquals(2, v.getMajor());
        assertEquals(8, v.getMinor());
        assertEquals(15, v.getRevision());
    }

    @Test
    void testToString() {
        String version = "1.7.14";
        Version v = new Version(version);
        assertEquals(version, v.toString());
    }

    @Test
    void testCompareTo() {
        Version older = new Version("1.6.0");
        Version newer = new Version("1.7.15");

        assertEquals(1, newer.compareTo(older));
        assertEquals(-1, older.compareTo(newer));
        assertEquals(0, older.compareTo(older));
    }
}
