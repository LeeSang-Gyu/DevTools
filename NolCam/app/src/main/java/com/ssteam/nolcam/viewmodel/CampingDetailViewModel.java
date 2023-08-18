package com.ssteam.nolcam.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CampingDetailViewModel extends ViewModel {
    public MutableLiveData<String> id = new MutableLiveData<>();
    public MutableLiveData<String> facltNm = new MutableLiveData<>();
    public MutableLiveData<String> indutyAndLctCl = new MutableLiveData<>();
    public MutableLiveData<String> lineIntro = new MutableLiveData<>();
    public MutableLiveData<String> createdtime = new MutableLiveData<>();
    public MutableLiveData<String> intro = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> firstImageURLs = new MutableLiveData<>();
    public MutableLiveData<String> addr1 = new MutableLiveData<>();
    public MutableLiveData<String> addr2 = new MutableLiveData<>();
    public MutableLiveData<String> tel = new MutableLiveData<>();
    public MutableLiveData<String> homepage = new MutableLiveData<>();
    public MutableLiveData<String> firefightingFacilities = new MutableLiveData<>();
        public MutableLiveData<String> resveUrl = new MutableLiveData<>();
    public MutableLiveData<String> resveCl = new MutableLiveData<>();
    public MutableLiveData<String> exprnProgrm = new MutableLiveData<>();
    public MutableLiveData<String> posblFcltyCl = new MutableLiveData<>();
    public MutableLiveData<String> sbrsCl = new MutableLiveData<>();
    public MutableLiveData<String> mapX = new MutableLiveData<>();
    public MutableLiveData<String> mapY = new MutableLiveData<>();
    public MutableLiveData<String> caravSiteCo = new MutableLiveData<>();
    public MutableLiveData<String> glampSiteCo = new MutableLiveData<>();
    public MutableLiveData<String> indvdlCaravSiteCo = new MutableLiveData<>();
    public MutableLiveData<String> siteMg1Width = new MutableLiveData<>();
    public MutableLiveData<String> siteMg2Width = new MutableLiveData<>();
    public MutableLiveData<String> siteMg3Width = new MutableLiveData<>();
    public MutableLiveData<String> siteMg1Vrticl = new MutableLiveData<>();
    public MutableLiveData<String> siteMg2Vrticl = new MutableLiveData<>();
    public MutableLiveData<String> siteMg3Vrticl = new MutableLiveData<>();
    public MutableLiveData<String> siteMg1Co = new MutableLiveData<>();
    public MutableLiveData<String> siteMg2Co = new MutableLiveData<>();
    public MutableLiveData<String> siteMg3Co = new MutableLiveData<>();
    public MutableLiveData<String> siteBottomCl1 = new MutableLiveData<>();
    public MutableLiveData<String> siteBottomCl2 = new MutableLiveData<>();
    public MutableLiveData<String> siteBottomCl3 = new MutableLiveData<>();
    public MutableLiveData<String> siteBottomCl4 = new MutableLiveData<>();
    public MutableLiveData<String> siteBottomCl5 = new MutableLiveData<>();
    public MutableLiveData<String> tooltip = new MutableLiveData<>();
    public MutableLiveData<String> glampInnerFclty = new MutableLiveData<>();
    public MutableLiveData<String> caravInnerFclty = new MutableLiveData<>();
    public MutableLiveData<String> operPdCl = new MutableLiveData<>();
    public MutableLiveData<String> operDeCl = new MutableLiveData<>();
    public MutableLiveData<String> trlerAcmpnyAt = new MutableLiveData<>();
    public MutableLiveData<String> caravAcmpnyAt = new MutableLiveData<>();
    public MutableLiveData<String> toiletCo = new MutableLiveData<>();
    public MutableLiveData<String> swrmCo = new MutableLiveData<>();
    public MutableLiveData<String> wtrplCo = new MutableLiveData<>();
}
