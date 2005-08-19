package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to delete a timer
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class DELT extends Command {

  /**
   * The number of the timer, which should be deleted
   */
	private String number;
	
	/**
	 * Command to delete a timer
	 * @param parameter The number of the timer, which should be deleted
	 */
	public DELT(String parameter) {
		this.number = parameter;
	}

	public String getCommand() {
		return "DELT " + number;
	}

	public String toString() {
		return "DELT";
	}

	/**
	 * Returns the number of the timer, which should be deleted
	 * @return The number of the timer, which should be deleted
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of the timer, which should be deleted
	 * @param parameter The number of the timer, which should be deleted
	 */
	public void setNumber(String parameter) {
		this.number = parameter;
	}
}
