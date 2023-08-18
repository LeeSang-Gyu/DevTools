package com.ssteam.nolcam.generic;

import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class MapSlideMsg {

    public int position;
    private ArrayList<Camping> arrayList;
    private String resultMsg;
    private String result;
    private String city;

    public MapSlideMsg(){
        this.arrayList = arrayList;
    }

    public MapSlideMsg(String msg){

        this.resultMsg = msg;
    }
    public void setPosition(int position){this.position = position;}

    public int getPosition(){return position;}

    public void setCityResult(String city){
        this.city = city;
    }

    public String getCity(){return  city;}

    public void setResult(String result){
        this.result = result;
    }

    public String getResult(){
        return result;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }


    public ArrayList<Camping> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Camping> arrayList) {
        this.arrayList = arrayList;
    }
}
