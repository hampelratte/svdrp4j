package org.hampelratte.svdrp.responses.highlevel;



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
		    System.err.println(chanConfLine);
			throw new Exception("Unknown format for channels.conf lines: " + chanConfLine);
		}
	}

    private static DVBChannel nullChannel = new DVBChannel();
	private static boolean isDvbChannel(String chanConfLine) {
	    String[] parts = chanConfLine.split(":");
        try {
            DVBChannelLineParser.parseParameters(nullChannel, parts[2]);
        } catch (RuntimeException e) {
            return false;
        }
        
        return true;
	}
}