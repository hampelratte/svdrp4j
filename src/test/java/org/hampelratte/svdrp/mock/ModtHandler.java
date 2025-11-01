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
package org.hampelratte.svdrp.mock;

import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModtHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(ModtHandler.class);

    private final TimerManager timerManager;

    public ModtHandler(TimerManager timerManager) {
        super();
        this.timerManager = timerManager;
    }

    @Override
    public boolean accept(String request) {
        return request.matches("[Mm][Oo][Dd][Tt]\\s+(\\d+)\\s+(.*?)");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Mm][Oo][Dd][Tt]\\s+((\\d+)\\s+(.*?))").matcher(request);
        if (m.matches()) {
            int id = Integer.parseInt(m.group(2));
            String settings = m.group(3);

            if (settings.equalsIgnoreCase("on")) {
                Timer timer = timerManager.getTimer(id);
                timer.changeStateTo(Timer.ACTIVE, true);
                return "250 Timer modified";
            } else if (settings.equalsIgnoreCase("off")) {
                Timer timer = timerManager.getTimer(id);
                timer.changeStateTo(Timer.ACTIVE, false);
                return "250 Timer modified";
            } else {
                Timer timer = TimerParser.parse(m.group(1)).getFirst();
                logger.info("Modify timer with number {}", timer.getID());
                try {
                    timerManager.modifyTimer(timer);
                    return "250 Timer modified";
                } catch (RuntimeException e) {
                    return "451 Cannot modify timer: " + e.getLocalizedMessage();
                }
            }

        } else {
            return "451 Cannot handle request with MODT handler";
        }
    }

}
