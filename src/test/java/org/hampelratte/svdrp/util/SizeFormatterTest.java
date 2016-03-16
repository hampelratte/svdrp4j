package org.hampelratte.svdrp.util;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class SizeFormatterTest {
    @Test
    public void testBytesLong() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("123.00 Bytes", SizeFormatter.format(123));
    }

    @Test
    public void testBytesDouble() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("123.45 Bytes", SizeFormatter.format(123.45));
    }

    @Test
    public void testKibLong() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("1.00 KiB", SizeFormatter.format(SizeFormatter.KiB));
        Assert.assertEquals("1.50 KiB", SizeFormatter.format(SizeFormatter.KiB + 512));
    }

    @Test
    public void testKibDouble() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("1.00 KiB", SizeFormatter.format(1024d));
        Assert.assertEquals("1.50 KiB", SizeFormatter.format(1536d));
    }

    @Test
    public void testMibLong() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("1.00 MiB", SizeFormatter.format(SizeFormatter.MiB));
        Assert.assertEquals("1.50 MiB", SizeFormatter.format(SizeFormatter.MiB + 512 * SizeFormatter.KiB));
    }

    @Test
    public void testMibDouble() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("1.00 MiB", SizeFormatter.format(1024 * 1024d));
        Assert.assertEquals("1.50 MiB", SizeFormatter.format(1024 * 1536d));
    }

    @Test
    public void testGibLong() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("1.00 GiB", SizeFormatter.format(SizeFormatter.GiB));
        Assert.assertEquals("1.50 GiB", SizeFormatter.format(SizeFormatter.GiB + 512 * SizeFormatter.MiB));
    }

    @Test
    public void testGibDouble() {
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("1.00 GiB", SizeFormatter.format(1024 * 1024 * 1024d));
        Assert.assertEquals("1.50 GiB", SizeFormatter.format(1024 * 1024 * 1536d));
    }
}
