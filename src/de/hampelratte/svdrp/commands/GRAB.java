package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/** 
 * Command to grab a screenshot of the current channel
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class GRAB extends Command {

	private String format = "jpeg";
	private String quality = "80";
	private String resolution = "400 300";
	private String filename = "/tmp/screen.jpg";

	/**
	 * Command to grab a screenshot of the current channel
	 * @param filename The filename to save the screenshot to
	 */
	public GRAB(String filename) {
	    this.filename = filename;
	}
	
	public String getCommand() {
		String cmd = "GRAB " + filename + " " + format + " " + quality + " " + resolution;
		return cmd.trim();
	}

	
	public String toString() {
		return "GRAB";
	}

	/**
	 * Returns the format of the screenshot
	 * @return The format of the screenshot ("jpeg" or "pnm")
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the format of the screenshot
	 * @param format The format of the screenshot ("jpeg" or "pnm")
	 */
	public void setFormat(String format) {
	  if("jpeg".equals(format) || "pnm".equals(format)) {
		this.format = format;
	  }
	}

	/**
	 * Returns the quality of the screenshot.
	 * This parameter only applies to the jpeg format
	 * @return The quality of the screenshot
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * Sets the quality of the screenshot
	 * This parameter only applies to the jpeg format
	 * @param quality The quality of the screenshot (1-100)
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * Returns the resolution of the screenshot
	 * @return The resolution of the screenshot
	 */
	public String getResolution() {
		return resolution;
	}

	/**
	 * Sets the resolution of the screenshot
	 * @param resolution The resolution of the screenshot, e.g. "400 300"
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
}