package com.ssteam.nolcam.generic.campingimg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampingImg {

    @SerializedName("contentId")
    @Expose
    private int contentId;

    @SerializedName("createdtime")
    @Expose
    private String createdtime;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("modifiedtime")
    @Expose
    private String modifiedtime;

    @SerializedName("serialnum")
    @Expose
    private int serialnum;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public int getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(int serialnum) {
        this.serialnum = serialnum;
    }


    @Override
    public String toString() {
        return "CampingImg{" +
                "contentId=" + contentId +
                ", createdtime='" + createdtime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", modifiedtime='" + modifiedtime + '\'' +
                ", serialnum=" + serialnum +
                '}';
    }
}