package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Requested action not executed
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R550 extends Response {

    /**
     * @param message
     */
    public R550(String message) {
        super(550, message);
    }

    public String toString() {
        return "550 - Requested action not executed";
    }

}
