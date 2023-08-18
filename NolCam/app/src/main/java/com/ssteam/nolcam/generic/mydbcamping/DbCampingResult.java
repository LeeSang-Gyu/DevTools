package com.ssteam.nolcam.generic.mydbcamping;

import com.google.gson.annotations.SerializedName;

public class DbCampingResult {
    public DbCampingResponse getResponse() {
        return response;
    }

    public void setResponse(DbCampingResponse response) {
        this.response = response;
    }

    @SerializedName("response")
    private DbCampingResponse response;
}
