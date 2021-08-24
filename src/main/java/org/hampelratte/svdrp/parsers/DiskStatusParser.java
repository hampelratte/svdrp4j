package org.hampelratte.svdrp.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hampelratte.svdrp.responses.highlevel.DiskStatus;

public class DiskStatusParser {
	private DiskStatusParser() {}

    public static DiskStatus parse(String responseString) {
        Pattern p = Pattern.compile("(\\d+)MB (\\d+)MB (\\d+)%");
        Matcher m = p.matcher(responseString);
        if (m.matches()) {
            long totalInMiB = Long.parseLong(m.group(1));
            long freeInMiB = Long.parseLong(m.group(2));
            int usage = Integer.parseInt(m.group(3));
            DiskStatus diskStatus = new DiskStatus();
            diskStatus.setSpaceTotalInBytes(totalInMiB * 1024 * 1024);
            diskStatus.setSpaceFreeInBytes(freeInMiB * 1024 * 1024);
            diskStatus.setUsage(usage);
            return diskStatus;
        } else {
            throw new RuntimeException("stat disk response has an unknown format: [" + responseString + "]");
        }
    }
}
