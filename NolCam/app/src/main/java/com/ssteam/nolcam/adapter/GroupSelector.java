package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public class GroupSelector {
    Context context;
    ArrayList<View> viewList;

    int selectDrawable;
    int nonSelectDrawable;

    public GroupSelector(Context context) {
        this.context = context;
        viewList = new ArrayList<>();
    }

    public GroupSelector(Context context, int selectDrawable, int nonSelectDrawable) {
        this.context = context;
        viewList = new ArrayList<>();
        this.selectDrawable = selectDrawable;
        this.nonSelectDrawable = nonSelectDrawable;
    }

    public void setSelectDrawable(int selectDrawable) {
        this.selectDrawable = selectDrawable;
    }

    public void setNonSelectDrawable(int nonSelectDrawable) {
        this.nonSelectDrawable = nonSelectDrawable;
    }

    public void addView(View view) {
        this.viewList.add(view);
    }

    public void selectView(int id) {
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).getId() == id) {
                viewList.get(i).setBackground(context.getResources().getDrawable(selectDrawable));
            } else {
                viewList.get(i).setBackground(context.getResources().getDrawable(nonSelectDrawable));
            }
        }
    }
}