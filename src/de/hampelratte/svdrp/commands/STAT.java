package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to list system statistics like disc space
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class STAT extends Command {

	private String disk = "disk";
	
	public String getCommand() {
		String cmd = "STAT " + disk;
		return cmd.trim();
	}

	public String toString() {
		return "STAT";
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}
}
