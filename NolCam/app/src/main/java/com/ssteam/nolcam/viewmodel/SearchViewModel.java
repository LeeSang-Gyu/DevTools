package com.ssteam.nolcam.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    public MutableLiveData<Integer> categoryNum = new MutableLiveData<>();

    public SearchViewModel() {
        categoryNum.setValue(0);
    }


    public void categoryChoice(int num) {
        categoryNum.setValue(num);
    }

}
