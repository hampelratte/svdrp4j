package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to create a new timer
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class NEWT extends Command {

	private String settings = "";
	
	/**
	 * Command to create a new timer  
	 * @param settings
	 * 1:7:8:0704:0938:50:50:Quarks & Co:<br>
	 * Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
	 * In the summary newline characters have to be replaced by |<br>
	 * More details in the man page vdr(5)
	 */
	public NEWT(String settings) {
	    this.settings = settings;
	}
	
	public String getCommand() {
		String cmd = "NEWT " + settings;
		return cmd;
	}

	public String toString() {
		return "NEWT";
	}

	/**
	 * Returns the settings
	 * @return The settings
	 */
	public String getSettings() {
		return settings;
	}

	/**
	 * Sets the settings of the timer
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
