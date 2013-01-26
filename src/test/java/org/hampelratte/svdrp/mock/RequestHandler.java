package org.hampelratte.svdrp.mock;

public interface RequestHandler {
    public boolean accept(String request);

    public String process(String request);
}
