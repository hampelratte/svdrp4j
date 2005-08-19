package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to move a channel to a new position
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class MOVC extends Command {

	private String channel = "";
	private String position = "";
	
	/**
	 * Command to move a channel to a new position
	 * @param number The number of the channel
	 * @param to The new postio of the channel
	 */
	public MOVC(String number, String to) {
	  this.channel = number;
	  this.position = to;
	}
	
	/**
	 * Returns the channel number
	 * @return The channel number
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel number
	 * @param number The channel number
	 */
	public void setChannel(String number) {
		this.channel = number;
	}

	/**
	 * Returns the new position of the channel
	 * @return The new position of the channel
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets the new postion of the channel
	 * @param position The new postion of the channel
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getCommand() {
		String cmd = "MOVC " + channel + " " + position; 
		return cmd.trim();
	}
	
	public String toString() {
		return "MOVC";
	}

}
