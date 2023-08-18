package com.ssteam.nolcam.generic;

import com.google.gson.annotations.SerializedName;

public class CampingSlide {
    @SerializedName("id")
    public String id;

    @SerializedName("facltNm")
    public String facltNm;

    @SerializedName("induty")
    public String induty;

    @SerializedName("addr1")
    public String addr1;

    @SerializedName("addr2")
    public String addr2;

    @SerializedName("firstImageUrl")
    public String firstImageUrl;

    @SerializedName("createdtime")
    public String createdtime;

    @SerializedName("mapX")
    public String mapX;

    @SerializedName("mapY")
    public String mapY;

    @SerializedName("distance")
    public double distance;

    @Override
    public String toString() {
        return "Integration{" +
                "id='" + id + '\'' +
                ", facltNm='" + facltNm + '\'' +
                ", induty='" + induty + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", addr2='" + addr2 + '\'' +
                ", firstImageUrl='" + firstImageUrl + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", mapX='" + mapX + '\'' +
                ", mapY='" + mapY + '\'' +
                ", distance=" + distance +
                '}';
    }
}