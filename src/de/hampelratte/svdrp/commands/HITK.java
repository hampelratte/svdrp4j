package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/** 
 * Command to simulate the hit of a key on the remote control.
 * To get a list of valid keys use the constructor HITK()
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class HITK extends Command {

	private String key = "";

	/**
	 * Command to get a list of all valid keys.
	 * Without a parameter, VDR returns a list of all valid keys
	 */
	public HITK() {}
	
	/**
	 * Command to simulate the hit of a key on the remote control
	 * @param key The name of the key, which should be hit, e.g. UP,DOWN,1,2,...
	 */
	public HITK(String key) {
		this.key = key;
	}
	
	public String getCommand() {
		String cmd = "HITK " + key;
		return cmd.trim();
	}

	public String toString() {
		return "HITK";
	}

	/**
	 * Returns the key, which should be pressed
	 * @return The key, which should be pressed
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key, which should be pressed
	 * @param key The key, which should be pressed, e.g. UP,DOWN,1,2,...
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
