package com.ssteam.nolcam.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecommendViewModel extends ViewModel {
    public MutableLiveData<Integer> ranking = new MutableLiveData<>();

    public void rankingChoice(int value) {
        ranking.setValue(value);
    }
}