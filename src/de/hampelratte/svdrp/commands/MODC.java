package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to modify a channel.
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class MODC extends Command {

	private String number = "";
	private String settings = "";

	/**
	 * Command to modify a channel.
	 * @param number The number of the channel
	 * @param settings The settings must be in the same format as returned
     * by the LSTC command,<br>
	 * e.g. Das Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
	 * Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional Access:SID:NID:TID:RID<br>
	 * Have a look at the man page vdr(5) for more details
	 */
	public MODC(String number, String settings) {
	  this.number = number;
	  this.settings = settings;
	}

	/**
	 * Returns the number of the channel
	 * @return The number of the channel
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of the channel
	 * @param number The number of the channel
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Returns the settings
	 * @return The settings
	 */
	public String getSettings() {
		return settings;
	}

	/**
	 * Sets the settings
	 * @param setting The settings must be in the same format as returned
     * by the LSTC command,<br>
	 * e.g. Das Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
	 * Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional Access:SID:NID:TID:RID<br>
	 * Have a look at the man page vdr(5) for more details
	 */
	public void setSettings(String setting) {
		this.settings = setting;
	}
	
	public String getCommand() {
		String cmd = "MODC " + number + " " + settings; 
		return cmd.trim();
	}
	
	public String toString() {
		return "MODC";
	}

}
