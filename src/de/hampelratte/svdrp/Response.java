package de.hampelratte.svdrp;

/** 
 * Superclass for all responses from the VDR
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public abstract class Response extends Message {

  /**
   * The response code
   */
  protected int code = 0;

  /**
   * The response message
   */
  protected String message = "";

  /**
   * Creates a new Response with response code and message
   * @param code The response code of the response
   * @param message The message of the response
   */
  public Response(int code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Gets the response code of the response. One out of:
   * <ul>
   * <li>214 - Help text</li>
   * <li>215 - EPG entry</li>
   * <li>220 - VDR ready</li>
   * <li>221 - VDR closing connection</li>
   * <li>250 - Requested action ok</li>
   * <li>354 - Start of EPG data</li>
   * <li>451 - Requested action canceled</li>
   * <li>500 - Unknown command</li>
   * <li>501 - Unknown parameter</li>
   * <li>502 - Command not yet implemented</li>
   * <li>504 - Parameter not yet implemented</li>
   * <li>550 - Requested action not executed</li>
   * <li>554 - Transaction failed</li>
   * </ul>
   * Have a look at the subclasses for details
   * 
   * @return the response code of the response
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets the message of the response
   * 
   * @return the message of the response
   */
  public String getMessage() {
    return message;
  }
}