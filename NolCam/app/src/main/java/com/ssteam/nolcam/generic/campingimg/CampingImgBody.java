package com.ssteam.nolcam.generic.campingimg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampingImgBody {

    @SerializedName("items")
    @Expose
    private ItemCampingImg items;

    @SerializedName("numOfRows")
    @Expose
    private int numOfRows;

    @SerializedName("pageNo")
    @Expose
    private int pageNo;

    @SerializedName("totalCount")
    @Expose
    private int totalCount;

    public ItemCampingImg getItems() {
        return items;
    }

    public void setItems(ItemCampingImg items) {
        this.items = items;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "CampingBody{" +
                "numOfRows='" + numOfRows + '\'' +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }
}