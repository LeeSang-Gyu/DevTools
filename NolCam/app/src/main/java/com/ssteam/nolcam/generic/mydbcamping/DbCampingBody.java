package com.ssteam.nolcam.generic.mydbcamping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DbCampingBody {
    public DbCampingItems getItems() {
        return items;
    }

    public void setItems(DbCampingItems items) {
        this.items = items;
    }

    @SerializedName("items")
    private DbCampingItems items;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @SerializedName("totalCount")
    @Expose
    private String totalCount;
}
