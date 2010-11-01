package org.hampelratte.svdrp.responses.highlevel;

public class Stream {
    public static enum CONTENT {
        VIDEO, AUDIO
    }

    private CONTENT content;
    private int type;
    private String language;
    private String description;
    
    public CONTENT getContent() {
        return content;
    }

    public void setContent(CONTENT content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
