/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project (Lazy Bones) nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp;

/**
 * Superclass for all responses from the VDR
 * 
 * @author <a href="mailto:henrik.niehaus@gmx.de">Henrik Niehaus</a>
 */
public abstract class Response extends Message {
    private static final long serialVersionUID = 1L;
    
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
     * 
     * @param code
     *            The response code of the response
     * @param message
     *            The message of the response
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