package de.hampelratte.svdrp.commands;

import de.hampelratte.svdrp.Command;

/**
 * Command to show the next timer event
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus </a>
 *  
 */
public class NEXT extends Command {

  private String mode = "";

  /**
   * Command to show the next timer event in human readable format
   *  
   */
  public NEXT() {
  }

  /**
   * Command to show the next timer event
   * 
   * @param mode
   *          "abs" or "rel" <br>
   *          "abs" queries the time in absolute time "rel" queries the time
   *          relative to the actual time
   */
  public NEXT(String mode) {
    this.mode = mode;
  }

  public String getCommand() {
    String cmd = "NEXT " + mode;
    return cmd;
  }

  public String toString() {
    return "NEXT";
  }

  /**
   * Returns the mode
   * 
   * @return The mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * Sets the mode
   * 
   * @param mode
   *          "abs" or "rel" <br>
   *          "abs" queries the time in absolute time "rel" queries the time
   *          relative to the actual time
   */
  public void setMode(String mode) {
    this.mode = mode;
  }
}