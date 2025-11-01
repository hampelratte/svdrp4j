package org.hampelratte.svdrp.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AUDITest {
    @Test
    void testAudi() {
        AUDI audi = new AUDI();
        assertEquals("AUDI", audi.getCommand());
    }

    @Test
    void testAudiWithParam() {
        AUDI audi = new AUDI(1);
        assertEquals("AUDI 1", audi.getCommand());
    }

    @Test
    void testSetParam() {
        AUDI audi = new AUDI();
        assertEquals("AUDI", audi.getCommand());

        audi.setParameter("32");
        assertEquals("AUDI 32", audi.getCommand());

        audi.setParameter(null);
        assertEquals("AUDI", audi.getCommand());

        audi.setParameter(" ");
        assertEquals("AUDI", audi.getCommand());
    }

    @Test
    void testGetParam() {
        AUDI audi = new AUDI(1);
        assertEquals("AUDI 1", audi.getCommand());

        assertEquals("1", audi.getParameter());

        audi.setParameter(null);
        assertNull(audi.getParameter());

        audi.setParameter(" ");
        assertEquals(" ", audi.getParameter());
    }

    @Test
    void testToString() {
        assertEquals("AUDI", new AUDI().toString());
        assertEquals("AUDI", new AUDI(1).toString());
    }
}
