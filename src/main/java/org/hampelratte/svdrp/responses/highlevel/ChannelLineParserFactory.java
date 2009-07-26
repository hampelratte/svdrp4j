package org.hampelratte.svdrp.responses.highlevel;




public class ChannelLineParserFactory {
	
	private static DVBChannelLineParser dvb;
	
	//private static IPTVChannelLineParser iptv;
	
	public static ChannelLineParser createChannelParser(String chanConfLine) throws Exception {
		/* TODO enable if IPTV is fully supported if(chanConfLine.toLowerCase().contains("iptv")) {
			if(iptv == null) iptv = new IPTVChannelLineParser();
			return iptv;
		} else*/ 
	    if(isDvbChannel(chanConfLine)) { 
			if(dvb == null) dvb = new DVBChannelLineParser();
			return dvb;
	    } else {
			throw new Exception("Unknown format for channels.conf lines: " + chanConfLine);
		}
	}

	private static boolean isDvbChannel(String chanConfLine) {
	    String[] parts = chanConfLine.split(":");
        if (parts.length >= 4 && (parts[3].startsWith("S") || parts[3].startsWith("C") || parts[3].startsWith("T"))) {
            return true;
        }
        
        return false;
	}
}