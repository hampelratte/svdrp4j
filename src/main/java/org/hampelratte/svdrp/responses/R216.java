/* $Id$
 * 
 * Copyright (c) Henrik Niehaus & Lazy Bones development team
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
package org.hampelratte.svdrp.responses;

import java.io.IOException;

import javax.swing.ImageIcon;

import org.hampelratte.svdrp.Response;
import org.hampelratte.svdrp.util.Base64;

/**
 * VDR Response: Image grab data (base 64)
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * 
 */
public class R216 extends Response {
    
    private static final long serialVersionUID = 1L;
    
    public R216(String message) {
        super(216, message);
    }
    
    @Override
    public String toString() {
        return "216 - Image grab data (base 64)";
    }
    
    /**
     * Returns the grabbed image as {@link ImageIcon}
     * @return the grabbed image as {@link ImageIcon}
     * @throws IOException
     */
    public ImageIcon getImage() throws IOException {
        ImageIcon icon = new ImageIcon();
        String image = getMessage().substring(0, getMessage().lastIndexOf("\n"));
        image = image.substring(0, image.lastIndexOf("\n"));
        image = image.replaceAll("\n", "");
        byte[] bytes = Base64.decode(image);
        icon = new ImageIcon(bytes);
        return icon;
    }
}
