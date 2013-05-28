package org.hampelratte.svdrp.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeltHandler implements RequestHandler {

    private TimerManager timerManager;

    public DeltHandler(TimerManager timerManager) {
        super();
        this.timerManager = timerManager;
    }

    @Override
    public boolean accept(String request) {
        return request.matches("[Dd][Ee][Ll][Tt] (.*)");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Dd][Ee][Ll][Tt] (.*)").matcher(request);
        if (m.matches()) {
            try {
                int id = Integer.parseInt(m.group(1));
                boolean removed = timerManager.removeTimer(id);
                if (removed) {
                    return "250 Timer \"" + id + "\" deleted";
                } else {
                    return "501 Timer \"" + id + "\" not defined";
                }
            } catch (NumberFormatException e) {
                return "501 Timer ID [" + m.group(1) + "] is not a number";
            }
        } else {
            return "451 Cannot handle request with DELT handler";
        }
    }

}
