package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to update a timer
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class UPDT extends Command {

	private String settings = "";
	
	/**
	 * Command to update a timer
	 * @param settings
	 * 1:7:8:0704:0938:50:50:Quarks & Co:<br>
	 * Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
	 * In the summary newline characters have to be replaced by |<br>
	 * More details in the man page vdr(5)
	 */
	public UPDT(String settings) {
	  this.settings = settings;
	}
	
	public String getCommand() {
		return "UPDT " + settings;
	}

	public String toString() {
		return "UPDT";
	}

	/**
	 * Returns the settings for the timer
	 * @return The settings for the timer
	 */
	public String getSettings() {
		return settings;
	}

	/**
	 * Sets the settings for the timer
	 * @param settings
	 * 1:7:8:0704:0938:50:50:Quarks & Co:<br>
	 * Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
	 * In the summary newline characters have to be replaced by |<br>
	 * More details in the man page vdr(5)
	 */
	public void setSettings(String settings) {
		this.settings = settings;
	}
}
