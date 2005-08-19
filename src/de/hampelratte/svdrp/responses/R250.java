package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Requested action ok
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R250 extends Response {

    /**
     * @param message
     */
    public R250(String message) {
        super(250, message);
    }

    
    public String toString() {
        return "250 - Requested action finished";
    }

}
