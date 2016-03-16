/*
 * Copyright (c) Henrik Niehaus
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

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.junit.Test;

public class R216Test {

    /**
     * This is a base64 representation of /src/test/resources/testscreen.png
     */
    private static final String base64Png = "iVBORw0KGgoAAAANSUhEUgAAAAMAAAABCAIAAACUgoPjAAAAAXNSR0IArs4c6QAAAAlwSFlzAAAL\n"
            + "EwAACxMBAJqcGAAAAAd0SU1FB9wBEhACIHsmIWoAAAAdaVRYdENvbW1lbnQAAAAAAENyZWF0ZWQg\n"
            + "d2l0aCBHSU1QZC5lBwAAAA5JREFUCNdj+M/AwADGAA77Av65N4t5AAAAAElFTkSuQmCC";

    @Test
    public void testGetImage() throws IOException {
        R216 response = new R216(base64Png + "\nimage/png\nbla bla");
        ImageIcon icon = response.getImage();

        // convert the image icon to a BufferedImage, so that we can check the image contents
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(icon.getImage(), 0, 0, icon.getImageObserver());

        // height has to be 1px
        assertEquals(1, image.getHeight());

        // width has to be 3px
        assertEquals(3, image.getWidth());

        // check pixel colors
        checkColor(image, 0, 0, 255, 0, 0);
        checkColor(image, 1, 0, 0, 255, 0);
        checkColor(image, 2, 0, 0, 0, 255);
    }

    private void checkColor(BufferedImage img, int x, int y, int r, int g, int b) {
        int clr = img.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;

        assertEquals(r, red);
        assertEquals(g, green);
        assertEquals(b, blue);
    }

    @Test
    public void testToString() {
        assertEquals("216 - Image grab data (base 64)", new R216("").toString());
    }
}
