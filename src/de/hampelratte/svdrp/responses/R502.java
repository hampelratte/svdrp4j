package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Command not yet implemented
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R502 extends Response {

    /**
     * @param message
     */
    public R502(String message) {
        super(502, message);
    }

    public String toString() {
        return "502 - Command not implemented, yet";
    }

}
