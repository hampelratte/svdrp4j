package de.hampelratte.svdrp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net </a>
 * 
 * Holds the version of the VDR, which we are talking to
 */
public class VDRVersion {

    int major = 0;

    int minor = 0;

    int revision = 0;

    public VDRVersion(String versionString) {
        Pattern pattern = Pattern
                .compile("((?:\\d)+)\\.((?:\\d)+)\\.((?:\\d)+)");
        Matcher m = pattern.matcher(versionString);

        if (m.matches()) {
            major = Integer.parseInt(m.group(1));
            minor = Integer.parseInt(m.group(2));
            revision = Integer.parseInt(m.group(3));
        }
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int major) {
        this.minor = major;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int minor) {
        this.revision = minor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int version) {
        this.major = version;
    }

    public String toString() {
        return major + "." + minor + "." + revision;
    }
}