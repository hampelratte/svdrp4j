package org.hampelratte.svdrp.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.parsers.TimerParser;
import org.hampelratte.svdrp.responses.highlevel.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewtHandler implements RequestHandler {

    private static transient Logger logger = LoggerFactory.getLogger(NewtHandler.class);

    private TimerManager timerManager;

    public NewtHandler(TimerManager timerManager) {
        super();
        this.timerManager = timerManager;
    }

    @Override
    public boolean accept(String request) {
        return request.matches("[Nn][Ee][Ww][Tt] (.*)");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Nn][Ee][Ww][Tt] (.*)").matcher(request);
        if (m.matches()) {
            Timer timer = TimerParser.parse("1 " + m.group(1)).get(0);
            int id = timerManager.addTimer(timer);
            logger.info("New Timer ID: {} - {}", id, timer.toString());
            return "250 " + id + " " + timer.toNEWT();
        } else {
            return "451 Cannot handle request with NEWT handler";
        }
    }

}
