package de.hampelratte.svdrp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="hampelratte@users.sf.net>hampelratte@users.sf.net </a>
 * 
 * Holds the version of the VDR, which we are talking to
 */
public class VDRVersion {

  int version = 0;

  int major = 0;

  int minor = 0;


  public VDRVersion(String versionString) {
    Pattern pattern = Pattern
        .compile("((?:\\d)+)\\.((?:\\d)+)\\.((?:\\d)+)");
    Matcher m = pattern.matcher(versionString);

    if (m.matches()) {
      version = Integer.parseInt(m.group(1));
      major = Integer.parseInt(m.group(2));
      minor = Integer.parseInt(m.group(3));
    }
  }


  public int getMajor() {
    return major;
  }


  public void setMajor(int major) {
    this.major = major;
  }


  public int getMinor() {
    return minor;
  }


  public void setMinor(int minor) {
    this.minor = minor;
  }


  public int getVersion() {
    return version;
  }


  public void setVersion(int version) {
    this.version = version;
  }
  
  
  public String toString() {
    return version+"."+major+"."+minor;
  }
}