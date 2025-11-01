package org.hampelratte.svdrp.commands;

import org.hampelratte.svdrp.Command;

import java.io.Serial;

public class POLL extends Command {

    public static final String TIMERS = "timers";
    @Serial
    private static final long serialVersionUID = 607392886937631005L;
    private final String param;

    public POLL(String param) {
        this.param = param;
    }

    @Override
    public String getCommand() {
        return "POLL " + param;
    }

    @Override
    public String toString() {
        return "POLL";
    }
}
