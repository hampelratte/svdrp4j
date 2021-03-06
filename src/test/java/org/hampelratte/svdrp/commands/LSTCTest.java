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
package org.hampelratte.svdrp.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LSTCTest {
    @Test
    public void testConstructor() {
        assertEquals("LSTC", new LSTC().getCommand());
    }

    @Test
    public void testStringConstructor() {
        assertEquals("LSTC 1", new LSTC("1").getCommand());
        assertEquals("LSTC 1", new LSTC("1", false).getCommand());
        assertEquals("LSTC :ids 1", new LSTC("1", true).getCommand());
    }

    @Test
    public void testIntConstructor() {
        assertEquals("LSTC 1", new LSTC(1).getCommand());
        assertEquals("LSTC 1", new LSTC(1, false).getCommand());
        assertEquals("LSTC :ids 1", new LSTC(1, true).getCommand());
    }

    @Test
    public void testWithGroups() {
        assertEquals("LSTC :groups", new LSTC(true).getCommand());
        assertEquals("LSTC", new LSTC(false).getCommand());
    }

    @Test
    public void testWithGroupsAndIds() {
        assertEquals("LSTC :ids :groups", new LSTC(true, true).getCommand());
        assertEquals("LSTC :ids", new LSTC(true, false).getCommand());
        assertEquals("LSTC :groups", new LSTC(false, true).getCommand());
        assertEquals("LSTC", new LSTC(false, false).getCommand());
    }

    @Test
    public void testSetChannel() {
        LSTC lstc = new LSTC();
        lstc.setChannel("1");
        assertEquals("LSTC 1", lstc.getCommand());
        assertEquals("1", lstc.getChannel());
    }

    @Test
    public void testToString() {
        assertEquals("LSTC", new LSTC().toString());
        assertEquals("LSTC", new LSTC("1").toString());
        assertEquals("LSTC", new LSTC(1).toString());
    }

    //    @Test
    //    public void testBuilder() {
    //        assertEquals("LSTC", LSTC.Builder.of().build().getCommand());
    //        assertEquals("LSTC :groups", LSTC.Builder.of().withGroups().build().getCommand());
    //        assertEquals("LSTC :ids", LSTC.Builder.of().withIds().build().getCommand());
    //        assertEquals("LSTC 1", LSTC.Builder.of(1).build().getCommand());
    //        assertEquals("LSTC 1", LSTC.Builder.of("1").build().getCommand());
    //        assertEquals("LSTC :ids 1", LSTC.Builder.of(1).withIds().build().getCommand());
    //        assertEquals("LSTC :ids 1", LSTC.Builder.of("1").withIds().build().getCommand());
    //    }
    //
    //    @Test(expected = IllegalStateException.class)
    //    public void testBuilderException() {
    //        LSTC lstc = LSTC.Builder.of(1).withGroups().build();
    //        assertEquals("LSTC :groups 1", lstc.getCommand());
    //        lstc = LSTC.Builder.of("1").withGroups().build();
    //        assertEquals("LSTC :groups 1", lstc.getCommand());
    //    }
}
