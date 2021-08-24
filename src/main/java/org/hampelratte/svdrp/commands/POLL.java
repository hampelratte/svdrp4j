package org.hampelratte.svdrp.commands;

import org.hampelratte.svdrp.Command;

public class POLL extends Command {

	private static final long serialVersionUID = 607392886937631005L;

	public static final String TIMERS = "timers";

    private String param;

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
