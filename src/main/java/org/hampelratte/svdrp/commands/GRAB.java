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
package org.hampelratte.svdrp.commands;

import org.hampelratte.svdrp.Command;

/**
 * Command to grab a screenshot of the current channel
 * 
 * @author <a href="mailto:hampelratte@users.sf.net">hampelratte@users.sf.net</a>
 * 
 */
public class GRAB extends Command {
    private static final long serialVersionUID = 2L;
    
    private int quality = 80;

    private Resolution resolution = new Resolution(400, 300);

    private String filename = "/tmp/screen.jpg";

    public GRAB() {}
    
    /**
     * Command to grab a screenshot of the current channel
     * 
     * @param filename
     *            The filename to save the screenshot to
     */
    public GRAB(String filename) {
        this.filename = filename;
    }

    @Override
    public String getCommand() {
        String cmd = "GRAB " + filename + " " + quality + " " + resolution;
        return cmd.trim();
    }

    @Override
    public String toString() {
        return "GRAB";
    }

    /**
     * Returns the quality of the screenshot. This parameter only applies to the
     * jpeg format
     * 
     * @return The quality of the screenshot
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Sets the quality of the screenshot This parameter only applies to the
     * jpeg format
     * 
     * @param quality
     *            The quality of the screenshot (1-100)
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /**
     * Returns the resolution of the screenshot
     * 
     * @return The resolution of the screenshot
     */
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution of the screenshot
     * 
     * @param resolution
     *            The resolution of the screenshot.
     */
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public String getFilename() {
        return filename;
    }

    /**
     * Determines, where the screenshot will be saved
     * 
     * @param filename
     *            The name of the file the screenshot will be saved to. If the
     *            file name is just an extension (.jpg, .jpeg or .pnm) the image
     *            data will be sent to the SVDRP connection encoded in base64.
     *            The same happens if '-' (a minus sign) is given as file name,
     *            in which case the image format defaults to JPEG.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public static class Resolution {
        private int width;
        private int height;
        
        public Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        
        @Override
        public String toString() {
            return width + " " + height;
        }
    }
}