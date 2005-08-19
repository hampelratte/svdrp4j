package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to change the volume of VDR
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class VOLU extends Command {

	private String volume = "";
	
	/**
	 * Command to change the volume of VDR
	 * @param volume "+", "-", "mute" or a number between 0..255
	 */
	public VOLU(String volume) {
	  this.volume = volume;
	}
	
	public String getCommand() {
		return "VOLU";
	}

	public String toString() {
		return "VOLU";
	}

	/**
	 * returns the new volume
	 * @return The new volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * Sets the volume
	 * @param volume "+", "-", "mute" or a number between 0..255
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
}
