package com.ssteam.nolcam.generic.mydbcamping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DbCampingResponse {
    public DbcampingHeader getHeader() {
        return header;
    }

    public void setHeader(DbcampingHeader header) {
        this.header = header;
    }

    @SerializedName("header")
    private DbcampingHeader header;

    public DbCampingBody getBody() {
        return body;
    }

    public void setBody(DbCampingBody body) {
        this.body = body;
    }

    @SerializedName("body")
    @Expose
    private DbCampingBody body;
}
