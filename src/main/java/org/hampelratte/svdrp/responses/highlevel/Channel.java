package org.hampelratte.svdrp.responses.highlevel;

import java.io.Serializable;

public abstract class Channel implements Serializable {
	
	private int channelNumber = -1;
    
    private String name = "";
    
    private String shortName = "";
    
    private String serviceProviderName = "";
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name.replaceAll(":", "|");
        if(name.indexOf(';') > 0) {
            String[] parts = name.split(";");
            name = parts[0];
            this.serviceProviderName = parts[1];
        }
        if(name.indexOf(',') > 0) {
            String[] parts = name.split(",");
            name = parts[0];
            this.shortName = parts[1];
        }
        this.name = name;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public String getServiceProviderName() {
        return serviceProviderName;
    }
    
    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }
    
}
