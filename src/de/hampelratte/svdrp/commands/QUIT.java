package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to close the connection to VDR
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class QUIT extends Command {

	
	public String getCommand() {
		return "QUIT";
	}

	public String toString() {
		return "QUIT";
	}

}
