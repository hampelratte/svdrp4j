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

import static org.junit.Assert.*;

import org.hampelratte.svdrp.commands.GRAB.Resolution;
import org.junit.Test;

public class GRABTest {

    @Test
    public void testSimple() {
        GRAB grab = new GRAB();
        assertEquals("GRAB .jpg", grab.getCommand());
    }
    
    @Test
    public void testFilename() {
        GRAB grab = new GRAB("/tmp/screenshot.jpg");
        assertEquals("GRAB /tmp/screenshot.jpg", grab.getCommand());
    }
    
    @Test
    public void testQuality() {
        GRAB grab = new GRAB();
        grab.setQuality(75);
        assertEquals(75, grab.getQuality());
        assertEquals("GRAB .jpg 75", grab.getCommand());
        
        grab.setQuality(-1);
        assertEquals(-1, grab.getQuality());
        assertEquals("GRAB .jpg", grab.getCommand());
    }
    
    @Test
    public void testResolution() {
        GRAB grab = new GRAB();
        grab.setResolution(new GRAB.Resolution(640, 480));
        assertEquals(640, grab.getResolution().getWidth());
        assertEquals(480, grab.getResolution().getHeight());
        assertEquals("GRAB .jpg 80 640 480", grab.getCommand());
    }
    
    @Test
    public void testCombinations() {
        GRAB grab = new GRAB();
        grab.setResolution(new GRAB.Resolution(640, 480));
        assertEquals("GRAB .jpg 80 640 480", grab.getCommand());
        
        grab.setResolution(new GRAB.Resolution(640, 480));
        grab.setQuality(75);
        assertEquals("GRAB .jpg 75 640 480", grab.getCommand());
        
        grab.setResolution(null);
        assertEquals("GRAB .jpg 75", grab.getCommand());
        
        grab.setQuality(-1);
        assertEquals("GRAB .jpg", grab.getCommand());
        
        grab.setFilename("dingens.jpg");
        assertEquals("dingens.jpg", grab.getFilename());
        assertEquals("GRAB dingens.jpg", grab.getCommand());
    }
    
    @Test
    public void testResolutionClass() {
        Resolution res = new Resolution(1, 1);
        assertEquals("1 1", res.toString());
        
        res.setHeight(800);
        assertEquals(800, res.getHeight());
        
        res.setWidth(600);
        assertEquals(600, res.getWidth());
    }
    
    @Test
    public void testToString() {
        GRAB grab = new GRAB("/tmp/screenshot.jpg");
        assertEquals("GRAB", grab.toString());
    }
}
