package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to list all recordings or details of a given recording
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class LSTR extends Command {

	private String number = "";
	
	/**
	 * Command to get a list of all recordings 
	 */
	public LSTR() {}
	
	/**
	 * Command to get details of a given recording
	 * @param number The number of the recording
	 */
	public LSTR(String number) {
	  this.number = number;
	}
	
	public String getCommand() {
		String cmd = "LSTR " + number;
		return cmd.trim();
	}

	public String toString() {
		return "LSTR";
	}

	/**
	 * Returns the number of the recording
	 * @return The number of the recording
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of the recording
	 * @param number The number of the recording
	 */
	public void setNumber(String number) {
		this.number = number;
	}
}
