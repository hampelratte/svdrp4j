package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

/**
 * VDR Response: Help text
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net</a>
 */
public class AccessDenied extends Response {
    
    public AccessDenied(String msg) {
      super(-1, msg);
    }
    
    public String toString() {
        return "Access denied";
    }
}