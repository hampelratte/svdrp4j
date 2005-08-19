package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to show the given message on the osd of your VDR
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class MESG extends Command {

	private String message = "";
	
	/**
	 * Command to show the given message on the osd of your VDR
	 * @param message The message, which should be shown on the osd
	 */
	public MESG(String message) {
		this.message = message;
	}
	
	public String getCommand() {
		String cmd = "MESG " + message;
		return cmd.trim();
	}

	public String toString() {
		return "MESG";
	}

	/**
	 * Returns the message, which should be shown on the osd
	 * @return The message, which should be shown on the osd
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message, which should be shown on the osd
	 * @param message The message, which should be shown on the osd
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
