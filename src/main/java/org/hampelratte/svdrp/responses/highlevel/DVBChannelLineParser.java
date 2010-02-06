/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project (Lazy Bones) nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp.responses.highlevel;

public class DVBChannelLineParser extends ChannelLineParser {

    private DVBChannel channel;
    
	public Channel parse(String chanConfLine) {
	    channel = new DVBChannel();
        String line = chanConfLine;
        // parse channelNumber
        channel.setChannelNumber(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        // remove channelNumber
        line = line.substring(line.indexOf(" ") + 1); 
        String[] parts = line.split(":");
        channel.setName(parts[0]);
        channel.setFrequency(Integer.parseInt(parts[1]));
        parseParameters(parts[2]);
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
	
	protected void parseParameters(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            switch(c) {
            case 'A':
                channel.setAlpha(parseNumberParam(string, i));
                break;
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
            case 'H':
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
            case 'O':
                channel.setRolloff(parseNumberParam(string, i));
                break;
            case 'P':
                channel.setPriority(parseNumberParam(string, i));
                break;
            case 'R':
                channel.setRightCircularPolarization(true);
                break;
            case 'T':
                channel.setTransmissionMode(parseNumberParam(string, i));
                break;
            case 'V':
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
