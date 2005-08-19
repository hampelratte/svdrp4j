package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: VDR ready
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
 */
public class R220 extends Response {

    /**
     * @param code
     * @param message
     */
    public R220(String message) {
        super(220, message);
        
    }

    public String toString() {
        return "220 - VDR is ready to receive commands";
    }

}
