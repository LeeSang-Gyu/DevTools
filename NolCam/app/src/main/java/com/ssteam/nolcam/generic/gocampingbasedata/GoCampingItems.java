package com.ssteam.nolcam.generic.gocampingbasedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class GoCampingItems {

//    @SerializedName("item")
//    @Expose
//    private ArrayList<GoCampingItem> item = null;


    @SerializedName("item")
    @Expose
    private ArrayList<Camping> campings  = null;

    public ArrayList<Camping> getCampings() {
        return campings;
    }

    public void setCampings(ArrayList<Camping> campings) {
        this.campings = campings;
    }

//
//    public ArrayList<GoCampingItem> getItem() {
//        return item;
//    }
//
//    public void setItem(ArrayList<GoCampingItem> item) {
//        this.item = item;
//    }

}
