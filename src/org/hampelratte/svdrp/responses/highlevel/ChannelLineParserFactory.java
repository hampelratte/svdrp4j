package org.hampelratte.svdrp.responses.highlevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChannelLineParserFactory {
	
	private static DVBChannelLineParser dvb;
	
	//private static IPTVChannelLineParser iptv;
	
	
	public static ChannelLineParser createChannelParser(String chanConfLine) throws Exception {
		/* TODO enable id IPTV is fully supported if(chanConfLine.toLowerCase().contains("iptv")) {
			if(iptv == null) iptv = new IPTVChannelLineParser();
			return iptv;
		} else*/ if(isDvbChannel(chanConfLine)) { 
			if(dvb == null) dvb = new DVBChannelLineParser();
			return dvb;
		} else {
			throw new Exception("Unknown format for channels.conf lines");
		}
	}


	private static String parameterPattern = "(?:b[678])?(?:d\\d{1,2})?(?:d\\d{1,2})?(?:g\\d{1,2})?h?(?:i[01])?l?(?:m\\d{1,3})?r?(?:t[28])?v?(?:y[0124])?";
    private static Pattern p = Pattern.compile("^.*:.*:"+parameterPattern+":.*$");
    
	private static boolean isDvbChannel(String chanConfLine) {
	    Matcher m = p.matcher(chanConfLine.toLowerCase());
	    return m.matches();
	}
}