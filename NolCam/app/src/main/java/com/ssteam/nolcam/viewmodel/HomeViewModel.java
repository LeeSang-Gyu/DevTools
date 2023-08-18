package com.ssteam.nolcam.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    public MutableLiveData<String> category = new MutableLiveData<>();

    public void setCategory(String value) {
        category.setValue(value);
    }
}
