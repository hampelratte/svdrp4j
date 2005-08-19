package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Requested action canceled
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R451 extends Response {

    /**
     * @param message
     */
    public R451(String message) {
        super(451, message);
    }

    public String toString() {
        return "451 - Requested action canceled: A local error occured";
    }

}
