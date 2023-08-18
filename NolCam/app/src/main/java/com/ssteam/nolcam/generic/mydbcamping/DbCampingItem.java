package com.ssteam.nolcam.generic.mydbcamping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DbCampingItem {
//    public String getTotalCount() {
//        return totalCount;
//    }
//
//    public void setTotalCount(String totalCount) {
//        this.totalCount = totalCount;
//    }
//
//    @SerializedName("totalCount")
//    @Expose
//    private String totalCount;

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    @SerializedName("updatetime")
    @Expose
    private String updatedate;


}
