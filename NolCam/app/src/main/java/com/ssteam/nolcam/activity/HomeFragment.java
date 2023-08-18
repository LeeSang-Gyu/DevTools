package com.ssteam.nolcam.activity;

import static android.view.View.GONE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.CampingSlideAdpater;
import com.ssteam.nolcam.adapter.IndicatorAdapter;
import com.ssteam.nolcam.databinding.FragmentHomeBinding;
import com.ssteam.nolcam.db.SettingSharedPred;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingItem;
import com.ssteam.nolcam.util.ConnectPHP;
import com.ssteam.nolcam.util.PermissionMgr;
import com.ssteam.nolcam.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements ParsingMsgEvent {
    FragmentHomeBinding binding;
    MainActivity mainActivity;
    LocationManager locationManager;

    double mapX = 0;
    double mapY = 0;

    public HomeFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DataBindingUtil.bind(view);
        binding.setLifecycleOwner(requireActivity());
        HomeViewModel viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding.setViewModel(viewModel);

        // Setting
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);


        // 권한상태에 따라 GPS센서 수신 / 권한요청 수행
        newCampingParsing();
        mainActivity.requestHomeGps();

        // events
        binding.newCampingRefreshBtn.setOnClickListener(v -> {
            newCampingParsing();
        });

        binding.gpsCampingRefreshBtn.setOnClickListener(v -> {
            if (mainActivity.getMapXY()[0] == -1) {
                mainActivity.requestHomeGps();
            } else {
                executeGpsParsing(mainActivity.getMapXY());
            }
        });

        // 텍스트 강조 효과
        String newCamping = (String) binding.newCampingTv.getText();
        String newCampingwantWord = "신규";
        String neweCampingfontColor = "#FF7F00";
        SpannableString newSpannableString = setTextChange(newCamping, newCampingwantWord, neweCampingfontColor);
        binding.newCampingTv.setText(newSpannableString);
        String around = (String) binding.aroundCampingTv.getText();
        String wantWord = "주변";
        String fontColor = "#3CB371";
        SpannableString aroundSpannableString = setTextChange(around, wantWord, fontColor);
        binding.aroundCampingTv.setText(aroundSpannableString);

        viewModel.category.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(requireActivity(), SearchResultActivity.class);
                intent.putExtra("Keyword", "전체");
                intent.putExtra("Area", "전체");
                intent.putExtra("Sigungu", "전체");
                intent.putExtra("Category", s);
                startActivity(intent);
            }
        });

        //애드몹
        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                binding.animationView.cancelAnimation();
                binding.animationView.setVisibility(GONE);
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        // TODO: Add adView to your view hierarchy.
        MobileAds.initialize(requireActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


    }

    //텍스트뷰 폰트 정보 변경
    private SpannableString setTextChange(String text, String wantWord, String textColor) {

        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf(wantWord);
        int end = start + wantWord.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(textColor)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public void newCampingParsing() {
        ConnectPHP newConnect = new ConnectPHP();
        newConnect.setEvent((ParsingMsgEvent) this, new ResultMsg("NewCamping"));
        newConnect.createCall(1);
    }

    public void executeGpsParsing(double[] maps) {
        ConnectPHP gpsConnect = new ConnectPHP();
        gpsConnect.setEvent((ParsingMsgEvent) HomeFragment.this, new ResultMsg("GpsCamping"));
        gpsConnect.setGps(maps[0], maps[1]);
        gpsConnect.createCall(1);
    }

    public void showMsgBox(String value) {
        if (value.equals("LoadingMsg")) { // 로딩중 메세지 박스
            gpsCampingBoxShow(binding.gpsCampingLoadingText.getId());
        } else if (value.equals("SensorMsg")) { // 수신중 메세지 박스
            gpsCampingBoxShow(binding.gpsSensorMsgBox.getId());
            binding.gpsSensorRefreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.requestHomeGps();
                }
            });
        } else if (value.equals("PermissionMsg")) {
            gpsCampingBoxShow(binding.gpsPermissionErrorMsgBox.getId());
            binding.gpsPermissionErrorRefreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.requestHomeGps();
                }
            });
        }
    }

    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        // 신규 캠핑장
        if (resultMsg.getType().equals("NewCamping")) {
            binding.newCampingIndicator.removeAllViews();

            ArrayList<Camping> campings = (ArrayList<Camping>) resultMsg.getArrayList();
            if (campings.size() != 0) {
                newCampingBoxShow(binding.newCampingListBox.getId());

                CampingSlideAdpater slideAdpater = new CampingSlideAdpater(requireActivity(), campings);
                binding.newCampingContainer.setAdapter(slideAdpater);
                IndicatorAdapter indicatorAdapter = new IndicatorAdapter(requireActivity(), campings.size());
                indicatorAdapter.setSize((indicatorAdapter.getWidth() / 2), (indicatorAdapter.getHeight() / 2));
                indicatorAdapter.setIndicator(binding.newCampingIndicator);
                indicatorAdapter.setupWithViewPager(binding.newCampingContainer);

                viewLog(campings);
            } else {
                newCampingBoxShow(binding.newNetworkMsgBox.getId());

                binding.newNetworkRefreshBtn.setOnClickListener(view -> {
                    newCampingParsing();
                });
            }
        } else if (resultMsg.getType().equals("GpsCamping")) {
            binding.gpsCampingIndicator.removeAllViews();

            ArrayList<Camping> campings = (ArrayList<Camping>) resultMsg.getArrayList();

            if (campings.size() != 0) {
                gpsCampingBoxShow(binding.gpsCampingListBox.getId());

                CampingSlideAdpater slideAdpater = new CampingSlideAdpater(requireActivity(), campings);
                binding.gpsCampingContainer.setAdapter(slideAdpater);
                IndicatorAdapter indicatorAdapter = new IndicatorAdapter(requireActivity(), campings.size());
                indicatorAdapter.setSize((indicatorAdapter.getWidth() / 2), (indicatorAdapter.getHeight() / 2));
                indicatorAdapter.setIndicator(binding.gpsCampingIndicator);
                indicatorAdapter.setupWithViewPager(binding.gpsCampingContainer);
            } else {
                gpsCampingBoxShow(binding.gpsNetworkErrorMsgBox.getId());

                binding.gpsNetworkErrorRefreshBtn.setOnClickListener(view -> {
                    if (mainActivity.getMapXY()[0] == -1) {
                        mainActivity.requestHomeGps();
                    } else {
                        executeGpsParsing(mainActivity.getMapXY());
                    }
                });
            }
        }
    }

    public void newCampingBoxShow(int id) {
        View[] views = new View[]{binding.newCampingListBox, binding.newNetworkMsgBox};
        for (int i = 0; i < views.length; i++) {
            if (views[i].getId() == id) {
                views[i].setVisibility(View.VISIBLE);

                continue;
            }

            views[i].setVisibility(View.GONE);
        }
    }

    public void gpsCampingBoxShow(int id) {
        View[] views = new View[]{binding.gpsCampingListBox, binding.gpsNetworkErrorMsgBox, binding.gpsPermissionErrorMsgBox, binding.gpsCampingLoadingText, binding.gpsSensorMsgBox};
        for (int i = 0; i < views.length; i++) {
            if (views[i].getId() == id) {
                views[i].setVisibility(View.VISIBLE);
                continue;
            }
            views[i].setVisibility(View.GONE);
        }
    }

    public double[] getMapXY() {
        return new double[]{mapX, mapY};
    }

    private void viewLog(ArrayList<Camping> num1size) {
        for (int i = 0; i < num1size.size(); i++) {

            String contentId = num1size.get(i).getContentId();
            String createdtime = num1size.get(i).getCreatedtime();
            String facltNm = num1size.get(i).getFacltNm();
            String lineIntro = num1size.get(i).getLineIntro();
            String intro = num1size.get(i).getIntro();
            String featureNm = num1size.get(i).getFeatureNm();
            String induty = num1size.get(i).getInduty();
            String firstImageURL = num1size.get(i).getFirstImageURL();
            String lctCl = num1size.get(i).getLctCl();
            String addr1 = num1size.get(i).getAddr1();
            String addr2 = num1size.get(i).getAddr2();
            String tel = num1size.get(i).getTel();
            String homepage = num1size.get(i).getHomepage();
            String resveUrl = num1size.get(i).getResveUrl();
            String resveCl = num1size.get(i).getResveCl();
            String exprnProgrm = num1size.get(i).getExprnProgrm();
            String posblFcltyCl = num1size.get(i).getPosblFcltyCl();
            String sbrsCl = num1size.get(i).getSbrsCl();
            double mapX = num1size.get(i).getMapX();
            double mapY = num1size.get(i).getMapY();
            String caravSiteCo = num1size.get(i).getCaravSiteCo();
            String glampSiteCo = num1size.get(i).getGlampSiteCo();
            String indvdlCaravSiteCo = num1size.get(i).getIndvdlCaravSiteCo();
            String siteMg1Width = num1size.get(i).getSiteMg1Width();
            String siteMg2Width = num1size.get(i).getSiteMg2Width();
            String siteMg3Width = num1size.get(i).getSiteMg3Width();
            String siteMg1Vrticl = num1size.get(i).getSiteMg1Vrticl();
            String siteMg2Vrticl = num1size.get(i).getSiteMg2Vrticl();
            String siteMg3Vrticl = num1size.get(i).getSiteMg3Vrticl();
            String siteMg1Co = num1size.get(i).getSiteMg1Co();
            String siteMg2Co = num1size.get(i).getSiteMg2Co();
            String siteMg3Co = num1size.get(i).getSiteMg3Co();
            String siteBottomCl1 = num1size.get(i).getSiteBottomCl1();
            String siteBottomCl2 = num1size.get(i).getSiteBottomCl2();
            String siteBottomCl3 = num1size.get(i).getSiteBottomCl3();
            String siteBottomCl4 = num1size.get(i).getSiteBottomCl4();
            String siteBottomCl5 = num1size.get(i).getSiteBottomCl5();
            String tooltip = num1size.get(i).getTooltip();
            String glampInnerFclty = num1size.get(i).getGlampInnerFclty();
            String caravInnerFclty = num1size.get(i).getCaravInnerFclty();
            String operPdCl = num1size.get(i).getOperPdCl();
            String operDeCl = num1size.get(i).getOperDeCl();
            String trlerAcmpnyAt = num1size.get(i).getTrlerAcmpnyAt();
            String caravAcmpnyAt = num1size.get(i).getCaravAcmpnyAt();
            String toiletCo = num1size.get(i).getToiletCo();
            String swrmCo = num1size.get(i).getSwrmCo();
            String wtrplCo = num1size.get(i).getWtrplCo();

            int extshrCo = num1size.get(i).getExtshrCo();
            int frprvtWrppCo = num1size.get(i).getFrprvtWrppCo();
            int frprvtSandCo = num1size.get(i).getFrprvtSandCo();
            int fireSensorCo = num1size.get(i).getFireSensorCo();
            String newdatacheck = num1size.get(i).getSyncStatus();
            String updatetime = num1size.get(i).getModifiedtime();

            Log.v("데이터 체크 ", contentId + ", " + createdtime + ", " + facltNm + ", " + lineIntro + ", " + intro + ", " +
                    featureNm + ", " + induty + ", " + firstImageURL + ", " + lctCl + " , " + addr1 + ", " + addr2 + ", " + tel + ", " +
                    homepage + ", " + resveUrl + ", " + resveCl + ", " + exprnProgrm + ", " + posblFcltyCl + ", " + sbrsCl + ", " + mapX + ", " + mapY + ", " +
                    caravSiteCo + ", " + glampSiteCo + ", " + indvdlCaravSiteCo + ", " + siteMg1Width + ", " + siteMg2Width + ", " + siteMg3Width + ", " + siteMg1Vrticl + ", " + siteMg2Vrticl
                    + ", " + siteMg3Vrticl + ", " + siteMg1Co + ", " + siteMg2Co + ", " + siteMg3Co + ", " + siteBottomCl1 + ", " + siteBottomCl2 + ", " + siteBottomCl3 + ", " + siteBottomCl4
                    + ", " + siteBottomCl5 + ", " + tooltip + ", " + glampInnerFclty + ", " + caravInnerFclty + ", " + operPdCl + ", " + operDeCl + ", " + trlerAcmpnyAt + ", " + caravAcmpnyAt + ", " +
                    toiletCo + ", " + swrmCo + ", " + wtrplCo + ", " + extshrCo + ", " + frprvtWrppCo + ", " + frprvtSandCo + ", " + fireSensorCo + ", " + newdatacheck + ", " + updatetime);

        }
    }
}