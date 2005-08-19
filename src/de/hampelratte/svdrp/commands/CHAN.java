package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to request or change the current channel.
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public class CHAN extends Command {
  
	private String parameter = "";
	
	/**
	 * Command to request the current channel.
	 * If CHAN is used without a parameter, VDR will return the current channel
	 */
	public CHAN() {}
	
	/**
	 * Command to change the channel
	 * @param parameter One out of:
	 * <ul>
	 * <li>"+"</li>
	 * <li>"-"</li>
	 * <li>the channel number</li>
	 * <li>the channel name</li>
	 * <li>the channel id</li>
	 * </ul>
	 */
	public CHAN(String parameter) {	
		this.parameter = parameter;
	}
	
	public String getCommand() {
	    String cmd = "CHAN " + parameter; 
		return cmd.trim();
	}

	public String toString() {
		return "CHAN";
	}

	/**
	 * Returns the parameter, which will be sent to VDR
	 * @return The parameter, which will be sent to VDR
	 * @see #setParameter(String parameter)
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter, which will be sent to VDR
	 * @param parameter One out of:
	 * <ul>
	 * <li>"+" switches to the next channel</li>
	 * <li>"-" switches to the previous channel</li>
	 * <li>the channel number</li>
	 * <li>the channel name</li>
	 * <li>the channel id</li>
	 * </ul>
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
