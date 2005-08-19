package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: EPG entry
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 *
  */
public class R215 extends Response {

    /**
     * @param message
     */
    public R215(String message) {
        super(215, message);
    }

    public String toString() {
        return "215 - EPG Entry";
    }

}
