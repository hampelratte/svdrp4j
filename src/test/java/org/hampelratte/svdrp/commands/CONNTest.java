package org.hampelratte.svdrp.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CONNTest {

    @Test
    void testConstructor() {
        CONN conn = new CONN("name", 6419, "2.5.6", "1.2.3", 5000);
        assertEquals("CONN name:name port:6419 vdrversion:2.5.6 apiversion:1.2.3 timeout:5000", conn.getCommand());
    }

    @Test
    void testToString() {
        CONN conn = new CONN("name", 6419, "2.5.6", "1.2.3", 5000);
        assertEquals("CONN", conn.toString());
    }

}
