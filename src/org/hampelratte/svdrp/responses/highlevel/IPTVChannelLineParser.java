package org.hampelratte.svdrp.responses.highlevel;

public class IPTVChannelLineParser extends ChannelLineParser {

	@Override
	public Channel parse(String chanConfLine) {
	    IPTVChannel channel = new IPTVChannel();
        String line = chanConfLine;
        // parse channelNumber
        channel.setChannelNumber(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        // remove channelNumber
        line = line.substring(line.indexOf(" ") + 1);
        
        // parse other parts
        String[] parts = line.split(":");
        // name
        channel.setName(parts[0]);
        
        // TODO parse the other parameters
        
		return channel;
	}

}
