package com.ssteam.nolcam.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultMsg {
    private String type;
    String result;
    private int callIndex;
    private ArrayList arrayList;
    private ArrayList<Camping> campList;

    private String syncType;


    public ResultMsg(){};
    
    public ResultMsg(ArrayList<Camping> campList) {
        this.campList = campList;
    }

    public ResultMsg(String type, String result, ArrayList arrayList) {
        this.type = type;
        this.result = result;
        this.arrayList = arrayList;
    }

    public ResultMsg(String type, String result) {
        this.type = type;
        this.result = result;
    }

    public void setCallIndex(int callIndex, String result) {
        this.callIndex = callIndex;
        this.result = result;
    }


    public HashMap<String, String> getCallIndex() {
        HashMap<String, String> map = new HashMap<>();
        map.put("callIndex", String.valueOf(callIndex));
        map.put("totalCount", result);
       return map;
    }

    public ResultMsg(String type) {
        this.type = type;
    }
    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public String getType() {
        return type;
    }

    public String getSyncType() {
        return syncType;
    }


    public void setSyncType(String syncType){
        this.syncType  = syncType;
    }

    public ArrayList<Camping> getCampList() {
        return campList;
    }

    public void setCampList(ArrayList<Camping> campList) {
        this.campList = campList;
    }
}
