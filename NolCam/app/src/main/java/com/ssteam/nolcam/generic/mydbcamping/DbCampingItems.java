package com.ssteam.nolcam.generic.mydbcamping;

import com.google.gson.annotations.SerializedName;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class DbCampingItems {
    public ArrayList<Camping> getItems() {
        return items;
    }

    public void setItem(ArrayList<Camping> items) {
        this.items = items;
    }

    @SerializedName("item")
    private ArrayList<Camping> items;


    public ArrayList<DbCampingItem> getUitems() {
        return uitems;
    }

    public void setUitems(ArrayList<DbCampingItem> uitems) {
        this.uitems = uitems;
    }

    @SerializedName("uitem")
    private ArrayList<DbCampingItem> uitems;
}
