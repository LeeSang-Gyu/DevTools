package com.ssteam.nolcam.util;

import com.google.gson.Gson;

public class MultiReturnGson {

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    private String stringValue;
    private Gson gson;

    public MultiReturnGson( String stringValue, Gson gson) {
            this.stringValue = stringValue;
            this.gson = gson;
        }



}
