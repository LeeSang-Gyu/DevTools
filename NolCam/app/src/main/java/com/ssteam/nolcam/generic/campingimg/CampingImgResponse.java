package com.ssteam.nolcam.generic.campingimg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampingImgResponse {

    @SerializedName("header")
    @Expose
    private com.ssteam.nolcam.generic.campingimg.CampingImgHeader header;
    @SerializedName("body")
    @Expose
    private CampingImgBody body;

    public com.ssteam.nolcam.generic.campingimg.CampingImgHeader getHeader() {
        return header;
    }

    public void setHeader(com.ssteam.nolcam.generic.campingimg.CampingImgHeader header) {
        this.header = header;
    }

    public CampingImgBody getBody() {
        return body;
    }

    public void setBody(CampingImgBody body) {
        this.body = body;
    }
}