/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.commands.QUIT;
import org.hampelratte.svdrp.mock.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VDRTimerTest  {
    
    private VDRTimer timer;
    
    private static Server server;
    
    @BeforeClass
    public static void startMockServer() throws IOException, InterruptedException {
        server = new Server();
        server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
        new Thread(server).start();
        
        // wait for the server
        Thread.sleep(1000);
    }
    
    @Before
    public void createTimer() {
        timer = new VDRTimer();
        timer.setTitle("TestTitle");
        timer.setChannelNumber(1);
        
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 4); // 04.11.2010 is a thursday
        startTime.set(Calendar.MONTH, 10); // Calendars begins counting of months with 0, so 10 is november
        startTime.set(Calendar.YEAR, 2010);
        startTime.set(Calendar.HOUR_OF_DAY, 12);
        startTime.set(Calendar.MINUTE, 0);
        timer.setStartTime(startTime);
        
        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.DAY_OF_MONTH, 4);
        endTime.set(Calendar.MONTH, 10);
        endTime.set(Calendar.YEAR, 2010);
        endTime.set(Calendar.HOUR_OF_DAY, 13);
        endTime.set(Calendar.MINUTE, 0);
        timer.setEndTime(endTime);

        timer.setRepeatingDays(new boolean[] { false, // MO
                false, // TUE
                false, // WED
                false, // THU
                false, // FRI
                true, // SAT
                true // SUN
        });
        
        
    }
    
    @Test
    public void testIsDaySet() {
        Calendar test = Calendar.getInstance();
        test.set(Calendar.DAY_OF_MONTH, 31); // 31.10.2010 is a monday
        test.set(Calendar.MONTH, 9);         // Calendars begins counting of months with 0, so 9 is october
        test.set(Calendar.YEAR, 2010);
        
        // monday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(timer.isDaySet(test));
        // tuesday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(timer.isDaySet(test));
        // wednesday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(timer.isDaySet(test));
        // thursday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(timer.isDaySet(test));
        // friday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(timer.isDaySet(test));
        // saturday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertTrue(timer.isDaySet(test));
        // sunday
        test.add(Calendar.DAY_OF_MONTH, 1);
        assertTrue(timer.isDaySet(test));
    }
    
    @Test
    public void testDayString() throws UnknownHostException, IOException {
        assertEquals("-----SS", timer.getDayString());
        timer.setRepeatingDays(new boolean[] { true, false, true, false, true, false, true });
        assertEquals("M-W-F-S", timer.getDayString());
        
        Calendar first = Calendar.getInstance();
        first.set(Calendar.DAY_OF_MONTH, 4); // 04.11.2010 is a thursday
        first.set(Calendar.MONTH, 10); // Calendars begins counting of months with 0, so 10 is november
        first.set(Calendar.YEAR, 2010);
        timer.setFirstTime(first);
        
        assertEquals("M-W-F-S@2010-11-04", timer.getDayString());
        
        timer.setHasFirstTime(false);
        assertEquals("M-W-F-S", timer.getDayString());
        
        // now retrieve the VDR version from the mock server so that the day string should have the new format
        Connection conn = new Connection("localhost", 2001);
        conn.send(new QUIT());
        timer.setRepeatingDays(new boolean[7]);
        assertEquals("2010-11-04", timer.getDayString());
    }
    
    @AfterClass 
    public static void shutdownServer() throws IOException, InterruptedException {
        server.shutdown();
    }
}