package de.hampelratte.svdrp;

/**
 * Represents a Message of the SVDRP. Either a command or a response.
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public abstract class Message {

	/**
	 * Returns a String representation for debug purposes
	 */
	public abstract String toString();
}
