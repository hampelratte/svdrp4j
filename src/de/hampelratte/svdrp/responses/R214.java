package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Help text
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 */
public class R214 extends Response {
    
    public R214(String msg) {
        super(214, msg);
    }
    
    public String toString() {
        return "214 - Help text";
    }
}
