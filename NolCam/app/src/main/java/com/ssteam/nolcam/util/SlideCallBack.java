package com.ssteam.nolcam.util;

public class SlideCallBack {

    public interface CallBack{
        void call(SlideCallBack slideCallBack);
    }

    private Boolean resultMsg;
    private  CallBack callBack;

    public SlideCallBack(){
        this.resultMsg = null;
        this.callBack = null;
    }

    public Boolean getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(Boolean resultMsg) {
        this.resultMsg = resultMsg;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void excute(){

    }





}
