package com.ssteam.nolcam.generic.gocampingbasedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoCampingResult {

    @SerializedName("response")
    @Expose
    private GoCampingResponse response;

    public void response(GoCampingResponse response){
        this.response = response;
    }

    public GoCampingResponse getResponse(){
        return response;
    }
}
