package com.ssteam.nolcam.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ssteam.nolcam.databinding.ActivityMainBinding;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Boolean> isMenu = new MutableLiveData<>();
    public MutableLiveData<Integer> fragmentCount = new MutableLiveData<>();

    public void menuSlide(boolean isMenu) {
        this.isMenu.setValue(isMenu);
    }
    public void fragmentUpdate(int count) {
        fragmentCount.setValue(count);
    }
}