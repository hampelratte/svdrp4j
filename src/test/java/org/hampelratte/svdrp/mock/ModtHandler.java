package org.hampelratte.svdrp.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModtHandler implements RequestHandler {

    private static transient Logger logger = LoggerFactory.getLogger(ModtHandler.class);

    private TimerManager timerManager;

    public ModtHandler(TimerManager timerManager) {
        super();
        this.timerManager = timerManager;
    }

    @Override
    public boolean accept(String request) {
        return request.matches("[Mm][Oo][Dd][Tt] (.*)");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Mm][Oo][Dd][Tt] (.*)").matcher(request);
        if (m.matches()) {
            Timer timer = TimerParser.parse(m.group(1)).get(0);
            logger.info("Modify timer with number {}", timer.getID());
            try {
                timerManager.modifyTimer(timer);
                return "250 Timer modified";
            } catch (RuntimeException e) {
                return "451 Cannot modify timer: " + e.getLocalizedMessage();
            }
        } else {
            return "451 Cannot handle request with MODT handler";
        }
    }

}
