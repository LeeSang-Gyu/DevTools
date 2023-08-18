package com.ssteam.nolcam.generic.campingimg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @SerializedName("response")
    @Expose
    private CampingImgResponse response;

    public CampingImgResponse getResponse() {
        return response;
    }

    public void setResponse(CampingImgResponse response) {
        this.response = response;
    }

}