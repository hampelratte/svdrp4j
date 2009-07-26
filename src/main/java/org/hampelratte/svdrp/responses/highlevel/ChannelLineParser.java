package org.hampelratte.svdrp.responses.highlevel;

public abstract class ChannelLineParser {

	public abstract Channel parse(String chanConfLine);
	
	protected static int parseNumberParam(String string, int startIndex) {
        int endIndex = -1;
        for(int j=startIndex+1; j<string.length(); j++) {
            if(Character.isDigit(string.charAt(j))) {
                endIndex = j+1;
            } else {
                break;
            }
        }
        
        return Integer.parseInt(string.substring(startIndex+1, endIndex));
    }
}
