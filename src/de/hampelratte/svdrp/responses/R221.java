package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: VDR has closed the connection
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R221 extends Response {

    /**
     * @param message
     */
    public R221(String message) {
        super(221, message);
    }

    public String toString() {
        return "221 - VDR has closed the connection";
    }

}
