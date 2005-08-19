package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to get help
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class HELP extends Command {

	private String topic = "";
	
	/**
	 * Command to get help.
	 * Without a parameter, VDR returns a list of all available commands
	 */
	public HELP() {}
	
	/**
	 * Command to get help for a specified command.
	 * @param parameter The command to get help for, e.g. "GRAB"
	 */
	public HELP(String parameter) {
		this.topic = parameter;
	}

	public String getCommand() {
	    String cmd = "HELP " + topic; 
		return cmd.trim();
	}

	public String toString() {
		return "HELP";
	}

	/**
	 * Returns the topic/command to get help for
	 * @return topic The topic/command to get help for
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Sets the topic/command to get help for
	 * @param topic The topic/command to get help for, e.g. "GRAB"
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
}
