package org.hampelratte.svdrp.responses.highlevel;

public class DVBChannelLineParser extends ChannelLineParser {

	public Channel parse(String chanConfLine) {
		DVBChannel channel = new DVBChannel();
        String line = chanConfLine;
        // parse channelNumber
        channel.setChannelNumber(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        // remove channelNumber
        line = line.substring(line.indexOf(" ") + 1); 
        String[] parts = line.split(":");
        channel.setName(parts[0]);
        channel.setFrequency(Integer.parseInt(parts[1]));
        parseParameters(channel, parts[2]);
        channel.setSource(parts[3]);
        channel.setSymbolRate(Integer.parseInt(parts[4]));
        channel.setVPID(parts[5]);
        channel.setAPID(parts[6]);
        channel.setTPID(parts[7]);
        channel.setConditionalAccess(parts[8]);
        channel.setSID(Integer.parseInt(parts[9]));
        channel.setNID(Integer.parseInt(parts[10]));
        channel.setTID(Integer.parseInt(parts[11]));
        channel.setRID(Integer.parseInt(parts[12]));
        
        return channel;
	}
	
	public static void parseParameters(DVBChannel channel, String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            switch(c) {
            case 'B':
                channel.setBandwidth(parseNumberParam(string, i));
                break;
            case 'C':
                channel.setCodeRateHP(parseNumberParam(string, i));
                break;
            case 'D':
                channel.setCodeRateLP(parseNumberParam(string, i));
                break;
            case 'G':
                channel.setGuardInterval(parseNumberParam(string, i));
                break;
            case 'h':
                channel.setHorizontalPolarization(true);
                break;
            case 'I':
                channel.setInversion(parseNumberParam(string, i));
                break;
            case 'L':
                channel.setLeftCircularPolarization(true);
                break;
            case 'M':
                channel.setModulation(parseNumberParam(string, i));
                break;
            case 'R':
                channel.setRightCircularPolarization(true);
                break;
            case 'T':
                channel.setTransmissionMode(parseNumberParam(string, i));
                break;
            case 'v':
                channel.setVerticalPolarization(true);
                break;
            case 'Y':
                channel.setHierarchy(parseNumberParam(string, i));
                break;
            default:
                break;
            }
        }
    }
}
