package com.ssteam.nolcam.Interface;

import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.CampingSlide;
import com.ssteam.nolcam.generic.MapSlideMsg;
import com.ssteam.nolcam.generic.ResultMsg;

import java.util.ArrayList;

public interface MapSlideMsgEvent {
     void setArray(MapSlideMsg mapSlideMsg, ResultMsg resultMsg, int msgNum);
}
