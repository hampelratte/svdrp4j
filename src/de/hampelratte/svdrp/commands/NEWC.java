package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to create a new timer 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 *
 */
public class NEWC extends Command {

	private String settings = "";
	
	/**
	 * Command to create a new timer
	 * @param settings The settings must be in the same format as returned
     * by the LSTC command,<br>
	 * e.g. Das Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
	 * Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional Access:SID:NID:TID:RID<br>
	 * Have a look at the man page vdr(5) for more details
	 */
	public NEWC(String settings) {
	  this.settings = settings;
	}
	
	public String getCommand() {
		String cmd = "NEWC " + settings;
		return cmd;
	}

	public String toString() {
		return "NEWC";
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
	 * @param settings The settings must be in the same format as returned
     * by the LSTC command,<br>
	 * e.g. Das Erste;ARD:11837:h:S19.2E:27500:101:102=deu:104:0:28106:1:1101:0<br>
	 * Name:Frequency:Parameters:Source:Srate:VPID:APID:TPID:Conditional Access:SID:NID:TID:RID<br>
	 * Have a look at the man page vdr(5) for more details
	 */
	public void setSettings(String settings) {
		this.settings = settings;
	}
}
