package com.ssteam.nolcam.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

//Map 리사이클러뷰 item 간격 조절하는 클래스

public class MapItemDecoration extends RecyclerView.ItemDecoration {

    private final int divWidth;

    public MapItemDecoration(int divWidth)
    {
        this.divWidth = divWidth;
    }

    /*
    * Rect 클래스 : 사각형 정보를 가지고 있는 클래스
    */


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divWidth;
    }
}
