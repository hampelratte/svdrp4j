package org.hampelratte.svdrp.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SizeFormatter {

    public static final long KiB = 1024;
    public static final long MiB = KiB * 1024;
    public static final long GiB = MiB * 1024;

    private static NumberFormat nf = new DecimalFormat(".00");

    public static String format(long size) {
        double decimal = (double) size;
        return format(decimal);
    }

    public static String format(double size) {
        double decimal = (double) size;
        String unit = "Bytes";
        if (size >= GiB) {
            decimal = (double) size / (double) (1024 * 1024 * 1024);
            unit = "GiB";
        } else if (size >= MiB) {
            decimal = (double) size / (double) (1024 * 1024);
            unit = "MiB";
        } else if (size >= KiB) {
            decimal = (double) size / (double) 1024;
            unit = "KiB";
        }

        return nf.format(decimal) + " " + unit;
    }
}
