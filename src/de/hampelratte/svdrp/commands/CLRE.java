package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to clear all EPG data of your VDR
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class CLRE extends Command {

	public String getCommand() {
		return "CLRE";
	}

	public String toString() {
		return "CLRE";
	}

}
