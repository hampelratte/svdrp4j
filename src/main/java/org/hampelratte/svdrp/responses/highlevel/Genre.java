package org.hampelratte.svdrp.responses.highlevel;

import java.io.Serializable;

public class Genre implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int code;
    private final String category;
    private final String description;

    public Genre(int code, String category, String description) {
        super();
        this.code = code;
        this.category = category;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
