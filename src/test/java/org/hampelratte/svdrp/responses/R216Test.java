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
