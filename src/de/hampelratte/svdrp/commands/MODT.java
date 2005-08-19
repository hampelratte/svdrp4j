package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to modify a timer
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class MODT extends Command {

	private String number = "";
	private String settings = "";
	
	/**
	 * Command to modify a timer
	 * @param number The number of the timer
	 * @param settings
	 * 1:7:8:0704:0938:50:50:Quarks & Co:<br>
	 * Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
	 * In the summary newline characters have to be replaced by |<br>
	 * More details in the man page vdr(5)
	 */
	public MODT(String number, String settings) {
	  this.settings = settings;
	  this.number = number;
	}
	
	/**
	 * Returns the number of the timer
	 * @return The number of the timer
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of the timer
	 * @param number The number of the timer
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Returns the settings of the timer
	 * @return The settings of the timer
	 */
	public String getSettings() {
		return settings;
	}

	/**
	 * Sets the settings of the timer
	 * @param setting
	 * 1:7:8:0704:0938:50:50:Quarks & Co:<br>
	 * Status:Channel:Day:Start:Stop:Priority:Lifetime:File:Summary<br>
	 * In the summary newline characters have to be replaced by |<br>
	 * More details in the man page vdr(5)
	 */
	public void setSettings(String setting) {
		this.settings = setting;
	}
	
	public String getCommand() {
		String cmd = "MODT " + number + " " + settings; 
		return cmd.trim();
	}
	
	public String toString() {
		return "MODT";
	}

}
