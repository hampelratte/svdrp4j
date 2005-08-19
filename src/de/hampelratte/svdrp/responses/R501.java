package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Unknown parameter
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R501 extends Response {

    /**
     * @param message
     */
    public R501(String message) {
        super(501, message);
    }

    public String toString() {
        return "501 - Wrong syntax in parameter";
    }

}
