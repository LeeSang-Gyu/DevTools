package com.ssteam.nolcam.Interface;

import com.ssteam.nolcam.generic.MapSlideMsg;
import com.ssteam.nolcam.generic.ResultMsg;

public interface MapReSetting {
    void setMapReSetting(String location, String choiceArea, String choiceCity, String cityMsg, ResultMsg resultMsg, MapSlideMsg mapSlideMsg);
}