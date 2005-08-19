package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to move a timer to a new position
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class MOVT extends Command {

	private String timer = "";
	private String position = "";
	
	/**
	 * Command to move a timer to a new position
	 * @param timer The timer number
	 * @param to The new postion of the timer
	 */
	public MOVT(String timer, String to) {
	  this.timer = timer;
	  this.position = to;
	}

	/**
	 * Returns the timer number
	 * @return The timer number
	 */
	public String getTimer() {
		return timer;
	}

	/**
	 * Sets the timer number
	 * @param number The timer number
	 */
	public void setTimer(String number) {
		this.timer = number;
	}

	/**
	 * Returns the new postion of the timer
	 * @return The new postion of the timer
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets the new position of the timer
	 * @param setting The new position of the timer
	 */
	public void setPosition(String setting) {
		this.position = setting;
	}
	
	public String getCommand() {
		String cmd = "MOVT " + timer + " " + position; 
		return cmd.trim();
	}
	
	public String toString() {
		return "MOVT";
	}

}
