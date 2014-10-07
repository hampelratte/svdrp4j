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
package org.hampelratte.svdrp.responses;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.hampelratte.svdrp.Response;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleResponsesTest {
    private static Map<Integer, String> expectedValues = new HashMap<Integer, String>();

    @BeforeClass
    public static void testCodeAndDescription() {
        expectedValues.put(-1, "Access denied");
        expectedValues.put(214, "214 - Help message");
        expectedValues.put(215, "215 - EPG or recording data record");
        expectedValues.put(216, "216 - Image grab data (base 64)");
        expectedValues.put(220, "220 - VDR service ready");
        expectedValues.put(221, "221 - VDR service closing transmission channel");
        expectedValues.put(250, "250 - Requested VDR action okay, completed");
        expectedValues.put(354, "354 - Start sending EPG data");
        expectedValues.put(451, "451 - Requested action aborted: local error in processing");
        expectedValues.put(500, "500 - Syntax error, command unrecognized");
        expectedValues.put(501, "501 - Syntax error in parameters or arguments");
        expectedValues.put(502, "502 - Command not implemented");
        expectedValues.put(504, "504 - Command parameter not implemented");
        expectedValues.put(550, "550 - Requested action not taken");
        expectedValues.put(554, "554 - Transaction failed");
        expectedValues.put(999, "Plugin response");
    }

    @Test
    public void testResponseAccessDenied() {
        testResponse(new AccessDenied("Test"), -1);
    }

    @Test
    public void testResponse214() {
        testResponse(new R214("Test"), 214);
    }

    @Test
    public void testResponse215() {
        testResponse(new R215("Test"), 215);
    }

    @Test
    public void testResponse216() {
        testResponse(new R216("Test"), 216);
    }

    @Test
    public void testResponse220() {
        testResponse(new R220("Test"), 220);
    }

    @Test
    public void testResponse221() {
        testResponse(new R221("Test"), 221);
    }

    @Test
    public void testResponse250() {
        testResponse(new R250("Test"), 250);
    }

    @Test
    public void testResponse354() {
        testResponse(new R354("Test"), 354);
    }

    @Test
    public void testResponse451() {
        testResponse(new R451("Test"), 451);
    }

    @Test
    public void testResponse500() {
        testResponse(new R500("Test"), 500);
    }

    @Test
    public void testResponse501() {
        testResponse(new R501("Test"), 501);
    }

    @Test
    public void testResponse502() {
        testResponse(new R502("Test"), 502);
    }

    @Test
    public void testResponse504() {
        testResponse(new R504("Test"), 504);
    }

    @Test
    public void testResponse550() {
        testResponse(new R550("Test"), 550);
    }

    @Test
    public void testResponsePlugin() {
        testResponse(new PluginResponse(999, "Test"), 999);
    }

    @Test
    public void testResponse554() {
        testResponse(new R554("Test"), 554);
    }

    private void testResponse(Response r, int code) {
        assertEquals(code, r.getCode());
        assertEquals(expectedValues.get(code), r.toString());
        assertEquals("Test", r.getMessage());
    }
}
