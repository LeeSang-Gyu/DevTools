package com.ssteam.nolcam.generic.mydbcamping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DbcampingHeader {
    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    @SerializedName("resultMsg")
    @Expose
    private String resultMsg;



}
