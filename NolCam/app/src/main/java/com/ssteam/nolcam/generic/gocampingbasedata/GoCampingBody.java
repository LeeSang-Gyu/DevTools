package com.ssteam.nolcam.generic.gocampingbasedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoCampingBody {
    @SerializedName("items")
    @Expose
    private GoCampingItems items;


    @SerializedName("numOfRows")
    @Expose
    private int numOfRows;

    @SerializedName("pageNo")
    @Expose
    private int pageNo;

    @SerializedName("totalCount")
    @Expose
    private int totalCount;

    public GoCampingItems getItems() {
        return items;
    }

    public void setItems(GoCampingItems items) {
        this.items = items;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
