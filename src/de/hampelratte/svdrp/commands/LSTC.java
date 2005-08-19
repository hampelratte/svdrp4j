package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to list all channels or details of the a given channel
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class LSTC extends Command {

	private String channel = "";
	
	/**
	 * Command to list all channels
	 */
	public LSTC() {} 
	
	/**
	 * Command to get details for a given channel
	 * @param channel The number or the name of the channel
	 */
	public LSTC(String channel) {
		this.channel = channel;
	}

	public String getCommand() {
		String cmd = "LSTC " + channel;
		return cmd.trim();
	}

	public String toString() {
		return "LSTC";
	}

	/**
	 * Returns the channel, which should be listed
	 * @return The channel, which should be listed
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel, which should be listed
	 * @param channel The number or the name of the channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
