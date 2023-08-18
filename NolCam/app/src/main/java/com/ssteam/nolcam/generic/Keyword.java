package com.ssteam.nolcam.generic;

public class Keyword {
    int id;
    String keyword;

    public Keyword(int id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

