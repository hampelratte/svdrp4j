package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to delete a recording
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus </a>
 *  
 */
public class DELR extends Command {

  /**
   * The number of the recording, which should be deleted
   */
  private String number;

  /**
   * Command to delete a recording
   * 
   * @param parameter
   *          The number of the recording, which should be deleted
   */
  public DELR(String parameter) {
    this.number = parameter;
  }

  public String getCommand() {
    return "DELR " + number;
  }

  public String toString() {
    return "DELR";
  }

  /**
   * Returns the number of the recording, which should be deleted
   * @return The number of the recording, which should be deleted
   */
  public String getNumber() {
    return number;
  }

  /**
   * Sets the number of the recording, which should be deleted
   * @param parameter The number of the recording, which should be deleted
   */
  public void setNumber(String parameter) {
    this.number = parameter;
  }
}