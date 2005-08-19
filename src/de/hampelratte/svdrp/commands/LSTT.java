package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to list all timers or details of a given timer
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class LSTT extends Command {

	private String number = "";
	
	/**
	 * Command to get a list of all timers
	 */
	public LSTT() {}

	/**
	 * Command to get details of a given timer
	 * @param number
	 */
	public LSTT(String number) {
	  this.number = number;
	}
	
	public String getCommand() {
		String cmd = "LSTT " + number;
		return cmd.trim();
	}

	public String toString() {
		return "LSTT";
	}

	/**
	 * Returns the number of the timer
	 * @return the number of the timer
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
}
