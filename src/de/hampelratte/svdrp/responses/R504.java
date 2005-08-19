package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Parameter not yet implemented
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R504 extends Response {

    /**
     * @param code
     * @param message
     */
    public R504(int code, String message) {
        super(code, message);
    }

    public String toString() {
        return "504 - Command parameter not implemented";
    }

}
