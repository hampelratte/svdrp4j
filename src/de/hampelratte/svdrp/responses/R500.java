package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Unknown command
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R500 extends Response {

    /**
     * @param message
     */
    public R500(String message) {
        super(500, message);
    }

    public String toString() {
        return "500 - Unknown command";
    }

}
