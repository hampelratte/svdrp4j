package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to delete a channel on the VDR
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class DELC extends Command {

  /**
   * The number of the channel, which should be deleted
   */
	private String number;
	
	/**
	 * Command to delete a channel
	 * @param parameter The number of the channel, which should be deleted
	 */
	public DELC(String parameter) {
		this.number = parameter;
	}

	public String getCommand() {
		return "DELC " + number;
	}

	public String toString() {
		return "DELC";
	}

	/**
	 * Returns the number of the channel, which should be deleted
	 * @return The number of the channel, which should be deleted
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of the channel, which should be deleted
	 * @param parameter The number of the channel, which should be deleted
	 */
	public void setNumber(String parameter) {
		this.number = parameter;
	}
}
