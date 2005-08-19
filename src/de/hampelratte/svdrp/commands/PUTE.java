package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to put data into the EPG list of the VDR
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus </a>
 *  
 */
public class PUTE extends Command {

  private String data = "";

  /**
   * Command to put data into the EPG list of the VDR
   * 
   * @param data
   *          The EPG data to enter. The data entered has to strictly follow the
   *          format defined in the man page vdr(5) for the 'epg.data' file.
   */
  public PUTE(String data) {
    this.data = data;
  }

  public String getCommand() {
    String cmd = "PUTE " + data + "\n.";
    return cmd.trim();
  }

  public String toString() {
    return "PUTE";
  }

  /**
   * Returns the EPG data to send to the VDR
   * @return The EPG data to send to the VDR
   */
  public String getData() {
    return data;
  }

  /**
   * Sets the EPG data to send to the VDR
   * 
   * @param data
   *          The EPG data to enter. The data entered has to strictly follow the
   *          format defined in the man page vdr(5) for the 'epg.data' file.
   */
  public void setData(String data) {
    this.data = data;
  }
}