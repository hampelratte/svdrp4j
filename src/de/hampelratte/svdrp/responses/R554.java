package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Transaction failed
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R554 extends Response {

    /**
     * @param message
     */
    public R554(String message) {
        super(554, message);
    }

    public String toString() {
        return "554 - Transaction failed";
    }
}
