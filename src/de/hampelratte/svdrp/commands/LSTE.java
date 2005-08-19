package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to list all EPG data of all channels, or the data of one channel, or the data of one channel at a time
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class LSTE extends Command {

	private String channel = "";
	private String time = "";
	
	/**
	 * Command to list all EPG data of all channels
	 */
	public LSTE() {}
	
	/**
	 * Command to list all data of one channel
	 * @param channel The number or the ID of the channel
	 */
	public LSTE(String channel) {
	    this.channel = channel;
	}
	
	/**
	 * Command to list the data of one channel at a given time
	 * @param channel The number or the ID of the channel
	 * @param time "now", "next" or "at &lt;time&gt;" , where &lt;time&gt; is in time_t format, 
	 * which is equal to the unix time stamp without the milliseconds -> unix time stamp / 1000, e.g. 1115484780
	 */
	public LSTE(String channel, String time) {
	    this.channel = channel;
	    this.time = time;
	}
	
	public String getCommand() {
		String cmd = "LSTE " + channel + " " + time; 
		return cmd.trim();
	}

	public String toString() {
		return "LSTE";
	}

	/**
	 * Returns the channel
	 * @return The channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel
	 * @param channel The ID or number of the channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * Returns the time
	 * @return The time
	 * @see #setTime(String time)
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets the time
	 * @param time "now", "next" or "at &lt;time&gt;" , where &lt;time&gt; is in time_t format, 
	 * which is equal to the unix time stamp without the milliseconds -> unix time stamp / 1000, e.g. 1115484780
	 */
	public void setTime(String time) {
		this.time = time;
	}
}
