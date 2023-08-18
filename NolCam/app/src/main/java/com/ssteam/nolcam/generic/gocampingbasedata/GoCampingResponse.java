package com.ssteam.nolcam.generic.gocampingbasedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoCampingResponse {

    @SerializedName("header")
    @Expose
    private GoCampingHeader header;

    @SerializedName("body")
    @Expose
    private GoCampingBody body;


    private void setHeader(){this.header = header;}

    private GoCampingHeader getHeader(){
        return header;
    }

    public GoCampingBody getBody() {
        return body;
    }

    public void setBody(GoCampingBody body) {
        this.body = body;
    }

}
