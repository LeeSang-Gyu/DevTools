package com.ssteam.nolcam.generic.campingimg;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemCampingImg {

    @SerializedName("item")
    @Expose
    private ArrayList<CampingImg> item = null;

    public ArrayList<CampingImg> getItem() {
        return item;
    }

    public void setItem(ArrayList<CampingImg> item) {
        this.item = item;
    }

}