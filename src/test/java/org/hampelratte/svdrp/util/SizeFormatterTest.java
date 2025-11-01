package org.hampelratte.svdrp.util;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeFormatterTest {
    @Test
    void testBytesLong() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("123.00 Bytes", SizeFormatter.format(123));
    }

    @Test
    void testBytesDouble() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("123.45 Bytes", SizeFormatter.format(123.45));
    }

    @Test
    void testKibLong() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("1.00 KiB", SizeFormatter.format(SizeFormatter.KiB));
        assertEquals("1.50 KiB", SizeFormatter.format(SizeFormatter.KiB + 512));
    }

    @Test
    void testKibDouble() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("1.00 KiB", SizeFormatter.format(1024d));
        assertEquals("1.50 KiB", SizeFormatter.format(1536d));
    }

    @Test
    void testMibLong() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("1.00 MiB", SizeFormatter.format(SizeFormatter.MiB));
        assertEquals("1.50 MiB", SizeFormatter.format(SizeFormatter.MiB + 512 * SizeFormatter.KiB));
    }

    @Test
    void testMibDouble() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("1.00 MiB", SizeFormatter.format(1024 * 1024d));
        assertEquals("1.50 MiB", SizeFormatter.format(1024 * 1536d));
    }

    @Test
    void testGibLong() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("1.00 GiB", SizeFormatter.format(SizeFormatter.GiB));
        assertEquals("1.50 GiB", SizeFormatter.format(SizeFormatter.GiB + 512 * SizeFormatter.MiB));
    }

    @Test
    void testGibDouble() {
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("1.00 GiB", SizeFormatter.format(1024 * 1024 * 1024d));
        assertEquals("1.50 GiB", SizeFormatter.format(1024 * 1024 * 1536d));
    }
}
