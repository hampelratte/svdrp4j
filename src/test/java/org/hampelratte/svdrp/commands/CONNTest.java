package org.hampelratte.svdrp.commands;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CONNTest {

    @Test
    public void testConstructor() {
        CONN conn = new CONN("name", 6419, "2.5.6", "1.2.3", 5000);
        assertEquals("CONN name:name port:6419 vdrversion:2.5.6 apiversion:1.2.3 timeout:5000", conn.getCommand());
    }

    @Test
    public void testToString() {
        CONN conn = new CONN("name", 6419, "2.5.6", "1.2.3", 5000);
        assertEquals("CONN", conn.toString());
    }

}
