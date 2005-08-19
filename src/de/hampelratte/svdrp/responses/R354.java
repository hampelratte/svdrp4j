package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Start of EPG data
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net </a>
 *  
 */
public class R354 extends Response {

    /**
     * @param message
     */
    public R354(String message) {
        super(354, message);
    }

    public String toString() {
        return "354 - Start sending of EPG data";
    }

}