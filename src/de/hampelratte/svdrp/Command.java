package de.hampelratte.svdrp;

/**
 * The superclass for all commands sent to VDR
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public abstract class Command extends Message {
	/**
	 * Returns the command string, which will be sent to the VDR
	 * @return The command string, which will be
	 * sent to the VDR
	 */
	public abstract String getCommand();
}
