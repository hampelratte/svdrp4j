package org.hampelratte.svdrp.mock;

import org.hampelratte.svdrp.responses.highlevel.Recording;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovrHandler implements RequestHandler {

    private final RecordingManager recordingManager;

    public MovrHandler(RecordingManager recordingManager) {
        this.recordingManager = recordingManager;
    }

    @Override
    public boolean accept(String request) {
        return request.matches("[Mm][Oo][Vv][Rr]\\s+(\\d+)\\s+(.*)");
    }

    @Override
    public String process(String request) {
        Matcher m = Pattern.compile("[Mm][Oo][Vv][Rr]\\s+(\\d+)\\s+(.*)").matcher(request);
        if (m.matches()) {
            try {
                int id = Integer.parseInt(m.group(1));
                Recording rec = recordingManager.getRecording(id);
                if (rec == null) {
                    return "550 Recording \"" + id + "\" not found";
                } else {
                    String oldTitle = rec.getTitle();
                    String newTitle = m.group(2);
                    rec.setTitle(newTitle);
                    return "250 Recording \"" + oldTitle + "\" moved to \"" + newTitle + "\"";
                }
            } catch (NumberFormatException e) {
                return "501 Error in recording id \"" + m.group(1) + "\"";
            }
        } else {
            return "501 Missing recording id";
        }
    }

}
