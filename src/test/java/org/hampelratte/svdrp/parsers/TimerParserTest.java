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
package org.hampelratte.svdrp.parsers;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Version;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TimerParserTest {

    private static final Calendar day = Calendar.getInstance();

    static {
        day.add(Calendar.DAY_OF_MONTH, 1);
    }

    private final String timerData =
            "1 1:1:" + day.get(Calendar.DAY_OF_MONTH) + ":1945:2030:43:67:Doppel|Punkt:Mehrzeilige|nichtssagende|Beschreibung der Sendung mit Doppel:Punkt.\n" +
                    "2 2:1:2010-11-02:1945:2030:50:50:Tagesschau~Tagesschau am 2.11.2010:\n" +
                    "3 4:1:MTWTF--:2225:2310:50:50:Tagesthemen:\n" +
                    "4 8:2:MTWTFSS@" + day.get(Calendar.DAY_OF_MONTH) + ":2130:2227:50:50:heute-journal:\n" +
                    "5 0:2:M-----S@2010-12-31:2330:0030:50:50:Happy New Year:\n" +
                    "6 13:2:--W----@2010-11-02:2330:0011:50:50:Ganz spät:";

    private List<Timer> timers;

    @BeforeEach
    void parseTimers() {
        timers = TimerParser.parse(timerData);
    }

    @Test
    void testState() {
        assertTrue(timers.get(0).hasState(Timer.ACTIVE));
        assertTrue(timers.get(1).hasState(Timer.INSTANT_TIMER));
        assertTrue(timers.get(2).hasState(Timer.VPS));
        assertTrue(timers.get(3).hasState(Timer.RECORDING));
        assertTrue(timers.get(4).hasState(Timer.INACTIVE));

        assertTrue(timers.get(5).hasState(Timer.ACTIVE));
        assertTrue(timers.get(5).hasState(Timer.VPS));
        assertTrue(timers.get(5).hasState(Timer.RECORDING));
        assertFalse(timers.get(5).hasState(Timer.INACTIVE));
        assertFalse(timers.get(5).hasState(Timer.INSTANT_TIMER));
    }

    @Test
    void testNumber() {
        for (int i = 0; i < timers.size(); i++) {
            assertEquals(i + 1, timers.get(i).getID());
        }
    }

    @Test
    void testChannel() {
        assertEquals(1, timers.get(0).getChannelNumber());
        assertEquals("", timers.get(0).getChannelId());
        assertEquals(2, timers.get(5).getChannelNumber());
        assertEquals("", timers.get(5).getChannelId());
    }

    @Test
    void testChannelId() {
        String timerDataWithChannelId = "1 1:S19.2E-1-1019-10301:2017-06-02:2010:2155:50:50:Eltern und andere Wahrheiten:Nina (38) liebt ihre kleine Familie...\n" +
                "2 1:S19.2E-1-1011-11110:2017-06-02:2010:2125:50:50:Die Chefin:Raubmord im Münchner Nahverkehr - für Vera Lanz und ihre Kollegen scheint der Fall unkompliziert...";
        List<Timer> parsed = TimerParser.parse(timerDataWithChannelId);
        assertEquals(0, parsed.get(0).getChannelNumber());
        assertEquals("S19.2E-1-1019-10301", parsed.get(0).getChannelId());
        assertEquals(0, parsed.get(1).getChannelNumber());
        assertEquals("S19.2E-1-1011-11110", parsed.get(1).getChannelId());
    }

    @Test
    void testDayParsing() {
        Timer timer = timers.getFirst();
        assertEquals(day.get(Calendar.DAY_OF_MONTH), timer.getStartTime().get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void testDateParsing() {
        Timer timer = timers.get(1);
        assertEquals(2, timer.getStartTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(10, timer.getStartTime().get(Calendar.MONTH)); // 10 because Calendar begins counting with 0 for months
        assertEquals(2010, timer.getStartTime().get(Calendar.YEAR));
    }

    @Test
    void testRepeatingDays() {
        Timer timer = timers.get(2);
        assertTrue(timer.isRepeating());
        assertEquals("MTWTF--", timer.getDayString());
        assertTrue(timer.getRepeatingDays()[0]);
        assertTrue(timer.getRepeatingDays()[1]);
        assertTrue(timer.getRepeatingDays()[2]);
        assertTrue(timer.getRepeatingDays()[3]);
        assertTrue(timer.getRepeatingDays()[4]);
        assertFalse(timer.getRepeatingDays()[5]);
        assertFalse(timer.getRepeatingDays()[6]);
    }

    @Test
    void testRepeatingTimerStartingOnDay() {
        Timer timer = timers.get(3);
        assertTrue(timer.isRepeating());
        assertEquals(day.get(Calendar.DAY_OF_MONTH), timer.getStartTime().get(Calendar.DAY_OF_MONTH));
        assertTrue(timer.getRepeatingDays()[0]);
        assertTrue(timer.getRepeatingDays()[1]);
        assertTrue(timer.getRepeatingDays()[2]);
        assertTrue(timer.getRepeatingDays()[3]);
        assertTrue(timer.getRepeatingDays()[4]);
        assertTrue(timer.getRepeatingDays()[5]);
        assertTrue(timer.getRepeatingDays()[6]);
    }

    @Test
    void testRepeatingTimerStartingOnDate() {
        Timer timer = timers.get(4);
        assertTrue(timer.isRepeating());
        assertEquals(31, timer.getStartTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(11, timer.getStartTime().get(Calendar.MONTH)); // 11 because Calendar begins counting with 0 for months
        assertEquals(2010, timer.getStartTime().get(Calendar.YEAR));
        assertTrue(timer.getRepeatingDays()[0]);
        assertFalse(timer.getRepeatingDays()[1]);
        assertFalse(timer.getRepeatingDays()[2]);
        assertFalse(timer.getRepeatingDays()[3]);
        assertFalse(timer.getRepeatingDays()[4]);
        assertFalse(timer.getRepeatingDays()[5]);
        assertTrue(timer.getRepeatingDays()[6]);
    }

    @Test
    void testLastToNextDay() {
        Timer timer = timers.get(5);
        assertEquals(2, timer.getStartTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(10, timer.getStartTime().get(Calendar.MONTH)); // 10 because Calendar begins counting with 0 for months
        assertEquals(2010, timer.getStartTime().get(Calendar.YEAR));
        assertEquals(23, timer.getStartTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(30, timer.getStartTime().get(Calendar.MINUTE));

        assertEquals(3, timer.getEndTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(10, timer.getEndTime().get(Calendar.MONTH)); // 10 because Calendar begins counting with 0 for months
        assertEquals(2010, timer.getEndTime().get(Calendar.YEAR));
        assertEquals(0, timer.getEndTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(11, timer.getEndTime().get(Calendar.MINUTE));
    }

    @Test
    void testLastToNextYear() {
        Timer timer = timers.get(4);
        assertEquals(31, timer.getStartTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(11, timer.getStartTime().get(Calendar.MONTH)); // 11 because Calendar begins counting with 0 for months
        assertEquals(2010, timer.getStartTime().get(Calendar.YEAR));
        assertEquals(23, timer.getStartTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(30, timer.getStartTime().get(Calendar.MINUTE));

        assertEquals(1, timer.getEndTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(0, timer.getEndTime().get(Calendar.MONTH)); // 0 because Calendar begins counting with 0 for months
        assertEquals(2011, timer.getEndTime().get(Calendar.YEAR));
        assertEquals(0, timer.getEndTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(30, timer.getEndTime().get(Calendar.MINUTE));
    }

    @Test
    void testPrio() {
        assertEquals(43, timers.getFirst().getPriority());
    }

    @Test
    void testLifetime() {
        assertEquals(67, timers.getFirst().getLifetime());
    }

    @Test
    void testTitle() {
        assertEquals("Doppel:Punkt", timers.get(0).getTitle());
        assertEquals("Tagesschau am 2.11.2010", timers.get(1).getTitle());
    }

    @Test
    void testPath() {
        assertEquals("", timers.getFirst().getPath());
        assertEquals("Tagesschau", timers.get(1).getPath());
    }

    @Test
    void testFile() {
        assertEquals("Doppel|Punkt", timers.getFirst().getFile());
        assertEquals("Tagesschau~Tagesschau am 2.11.2010", timers.get(1).getFile());
    }

    @Test
    void testDescription() {
        assertEquals("Mehrzeilige\nnichtssagende\nBeschreibung der Sendung mit Doppel:Punkt.", timers.getFirst().getDescription());
    }

    @Test
    void testToNEWT() {
        Connection.setVersion(new Version("1.7.22"));
        String dayString = new SimpleDateFormat("yyyy-MM-dd").format(day.getTime());
        assertEquals("1:1:" + dayString + ":1945:2030:43:67:Doppel|Punkt:Mehrzeilige|nichtssagende|Beschreibung der Sendung mit Doppel:Punkt.", timers.getFirst().toNEWT());
    }
}

