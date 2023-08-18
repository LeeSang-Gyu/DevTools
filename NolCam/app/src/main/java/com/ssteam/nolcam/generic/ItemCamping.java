package com.ssteam.nolcam.generic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class ItemCamping {
    @SerializedName("item")// item의 모든 배열 값을 받아오는 주석
    public ArrayList<Camping> itmes;

    @SerializedName("totalCount")
    @Expose
    public int totalCount;
}