package de.hampelratte.svdrp.responses;

import de.hampelratte.svdrp.Response;

public class NotImplementedBySVDRP4J extends Response {

    public NotImplementedBySVDRP4J() {
        super(-2, "This response code is not supported by SVDRP4J");
    }
    
    public NotImplementedBySVDRP4J(int code, String mesg) {
        super(code, mesg);
    }

    public String toString() {
        return getCode() + " - This response code is not supported by SVDRP4J";
    }

}
