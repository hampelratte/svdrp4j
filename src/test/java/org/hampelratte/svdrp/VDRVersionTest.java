package org.hampelratte.svdrp;

import static org.junit.Assert.*;
import org.junit.Test;

public class VDRVersionTest {

    @Test
    public void testConstructor() {
        VDRVersion v = new VDRVersion("1.7.14");
        assertEquals(1, v.getMajor());
        assertEquals(7, v.getMinor());
        assertEquals(14, v.getRevision());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidString() {
        new VDRVersion("InvalidVersion");
    }
    
    @Test
    public void testSetter() {
        VDRVersion v = new VDRVersion("1.7.14");
        v.setMajor(2);
        v.setMinor(8);
        v.setRevision(15);
        
        assertEquals(2, v.getMajor());
        assertEquals(8, v.getMinor());
        assertEquals(15, v.getRevision());
    }
    
    @Test
    public void testToString() {
        String version = "1.7.14";
        VDRVersion v = new VDRVersion(version);
        assertEquals(version, v.toString());
    }
}
