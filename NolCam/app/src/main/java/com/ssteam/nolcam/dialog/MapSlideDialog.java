/*
 * 수정일 : 2023-02-20
 * 용도 : 지도 탭 다이얼로그
 * 특이사항 : x
 * */

package com.ssteam.nolcam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.ssteam.nolcam.Interface.ActivityEvent;
import com.ssteam.nolcam.Interface.DialogMsgEvent2;
import com.ssteam.nolcam.Interface.MapReSetting;
import com.ssteam.nolcam.Interface.MapSlideMsgEvent;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.activity.MapFragment;
import com.ssteam.nolcam.adapter.IndicatorAdapter;
import com.ssteam.nolcam.adapter.MapAreaAdapter;
import com.ssteam.nolcam.adapter.MapSlideAdapter;
import com.ssteam.nolcam.databinding.DialogMapSlideBinding;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.MapSlideMsg;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.CirclePagerIndicatorDecoration;
import com.ssteam.nolcam.util.ConnectPHP;
import com.ssteam.nolcam.util.MapItemDecoration;
import com.ssteam.nolcam.viewmodel.CampingDetailViewModel;

import java.util.ArrayList;

public class MapSlideDialog extends Dialog implements MapReSetting, ParsingMsgEvent, MapSlideMsgEvent, DialogMsgEvent2, ActivityEvent {
    private DialogMapSlideBinding binding;

    private MapItemDecoration mapItemDecoration = null;
    private  CirclePagerIndicatorDecoration circlePagerIndicatorDecoration = null;

    private Context context;
    private ArrayList<Camping> slideArray;
    private String slideResult = "";

    private ParsingMsgEvent parsingMsgEvent;
    private MapReSetting mapReSetting;

    private MapAreaDialog areaChoiceDialog;

    private MapFragment mapFragment;
    private CampingDetailViewModel viewModel;

    private String typeMsg  = "";
    private int dialogEvent =  0;

    public MapSlideDialog(Context context, ParsingMsgEvent parsingMsgEvent, MapFragment mapFragment) {
        super(context);
        this.context = context;
        this.parsingMsgEvent = parsingMsgEvent;
        this.mapFragment = mapFragment;
    }

    public void setTypeMsg(String typeMsg){
        this.typeMsg = typeMsg;
    }

    public MapSlideDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setResult(ArrayList<Camping> arrayList){
        this.slideArray = arrayList;
    }

    public void setMapFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public void setConveyResult(MapReSetting mapReSetting) {
        this.mapReSetting = mapReSetting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_map_slide, null, false);
        setContentView(binding.getRoot());

        setCancelable(true);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();

            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            params.windowAnimations = R.style.DialogAnimation;
            window.setAttributes(params);

            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        if (typeMsg.equals("result")){
            setDialogResult();
        }else if (typeMsg.equals("MyGps")){
            setDialogResult();
        }else{
            binding.slideSearch.setVisibility(View.VISIBLE);
            binding.slideSearchResult.setVisibility(View.GONE);
        }

        binding.searchUp.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

        final String all = "전체";

        binding.mapSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.mapSearchEt.length() > 1) {
                    ConnectPHP connectPHP = new ConnectPHP();
                    Editable keyword;
                    keyword = binding.mapSearchEt.getText();

                    //첫 번째 인자에 (interface name) class name의 의미는 적혀있는 class에서 interface로 정의해놓은 함수를 동작시키겠다는 의미(동작하고 다시 돌아와서 나머지 코드 수행)
                    connectPHP.setEvent((ParsingMsgEvent) MapSlideDialog.this, new ResultMsg("GpsMap"));
                    connectPHP.setSearchForm(String.valueOf(keyword), all, all, all);
                    connectPHP.createCall(3);
                    connectPHP.setSlideEvent((MapSlideMsgEvent) MapSlideDialog.this, new MapSlideMsg("Slide"));
                    connectPHP.setMapParsingNum(1);
                } else if (binding.mapSearchEt.length() == 1) {
                    Toast.makeText(context, "검색어를 두 글자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.searchUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setDialogResult() {

        if (slideArray.size() != 0) {

            binding.slideSearch.setVisibility(View.GONE);
            binding.slideSearchResult.setVisibility(View.VISIBLE);

            if (binding.slideSearchResult.getLayoutParams().height == 210) {
                binding.slideSearchResult.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }


            String text = "총  " + slideArray.size() + "개의 캠핑장을 발견했어요!";
            String wantWord = slideArray.size() + "개";
            String fontColor = "#ffead8";

            SpannableString spannableString = setTextChange(text, wantWord, fontColor);

            binding.slideText.setText(spannableString);
            setItemIndecator();

        } else {
            binding.slideSearch.setVisibility(View.GONE);
            binding.slideSearchResult.setVisibility(View.VISIBLE);
            binding.slideSearchResult.getLayoutParams().height = 210;
            binding.slideText.setText("캠핑장이 없습니다");
        }
    }


    @Override
    public void setArray(MapSlideMsg mapSlideMsg, ResultMsg resultMsg, int msgNum) {


        slideArray = new ArrayList<Camping>();

        if (msgNum == 1) {
            slideArray = (ArrayList<Camping>) mapSlideMsg.getArrayList();
            slideResult = mapSlideMsg.getResultMsg();

        } else if (msgNum == 2) {
            slideArray = (ArrayList<Camping>) resultMsg.getArrayList();
            slideResult = resultMsg.getType();
        }

        if (slideResult.equals("MapSlideMsg")) {

            if (binding.slideSearch.getVisibility() == View.VISIBLE && binding.slideSearchResult.getVisibility() == View.GONE) {
                setUpPanel(binding.slideSearch.getVisibility(), binding.slideSearchResult.getVisibility());
            }

            resultMsg.setArrayList(mapSlideMsg.getArrayList());
            resultMsg.setCampList(mapSlideMsg.getArrayList());
            Log.v("맵뷰 테스트/MapSlideDialog", String.valueOf(resultMsg.getArrayList().size()));

            parsingMsgEvent.onParSingMsg(resultMsg);

            if (slideArray.size() != 0) {
                mapFragment.initMap3(String.valueOf(slideArray.get(0).mapY), String.valueOf(slideArray.get(0).mapX));
            }
            setDialogResult();

        } else if (slideResult.equals("DialogMsg")) {


            if (binding.slideSearch.getVisibility() == View.VISIBLE && binding.slideSearchResult.getVisibility() == View.GONE) {
                setUpPanel(binding.slideSearch.getVisibility(), binding.slideSearchResult.getVisibility());
            } else {
                binding.slideSearch.setVisibility(View.GONE);
                binding.slideSearchResult.setVisibility(View.VISIBLE);
            }

            setDialogResult();

        } else {

        }

    }

    //텍스트뷰 폰트 정보 변경
    private SpannableString setTextChange(String text, String wantWord, String textColor) {

        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf(wantWord);
        int end = start + wantWord.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(textColor)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;

    }


    public void setDialogShow() {
        areaChoiceDialog = new MapAreaDialog(context, (DialogMsgEvent2) MapSlideDialog.this, 2);
        areaChoiceDialog.setAreaResult((MapSlideMsgEvent) MapSlideDialog.this, new ResultMsg("categoreMsg"));
        areaChoiceDialog.ChoiceEvent((ActivityEvent) MapSlideDialog.this);
        areaChoiceDialog.setReroadMap((MapReSetting) MapSlideDialog.this);
        areaChoiceDialog.show();
    }

    @Override
    public void onResult(String area, String city) {
//        binding.searchMap.setText(area + " > " + city);

        Toast.makeText(context, "선택한 지역으로 검색을 시작합니다.", Toast.LENGTH_SHORT).show();

        ConnectPHP connectPHP = new ConnectPHP();
        connectPHP.setEvent((ParsingMsgEvent) MapSlideDialog.this, new ResultMsg("AreaMap"));  //첫번째 인자는 클래스가 들어가야함.
        connectPHP.setMapTab(2, 1, 1, area, city);
        connectPHP.createCall(7);
    }


    /*
     * VISIBLE = 0
     * GONE = 8
     * */
    private void setUpPanel(int resultSlide, int slideSearchReuslt) {

        if (resultSlide == 0 && slideSearchReuslt == 8) {
            binding.slideSearch.setVisibility(View.GONE);
            binding.slideSearchResult.setVisibility(View.VISIBLE);

        } else if (resultSlide == 8 && slideSearchReuslt == 0) {
            binding.slideSearch.setVisibility(View.VISIBLE);
            binding.slideSearchResult.setVisibility(View.GONE);
        }
    }

    //검색결과 어댑터 설정 및 item 인디케이터 설정
    private void setItemIndecator() {
//        ImageSlideAdpater slideAdpater = new ImageSlideAdpater(context, str, R.layout.item_image_slide2);
        MapSlideAdapter mapSlideAdapter = new MapSlideAdapter(context, slideArray, R.layout.item_map_slide, mapFragment);
        ArrayList<String> strArray = new ArrayList<>();


        for (int i = 0; i < slideArray.size(); i++) {
            strArray.add(i, String.valueOf(slideArray.get(i).firstImageURL));
        }

        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(context, strArray.size());
        indicatorAdapter.setSize((indicatorAdapter.getWidth() / 2), (indicatorAdapter.getHeight() / 2));
        indicatorAdapter.setChoiceColor("#FFFFFF");
        indicatorAdapter.setIndicator(binding.detailCampingIndicator);
        indicatorAdapter.setupWithViewPager(binding.detailCampingContainer);


        int dpValue = 54;
        int topBottom = 32;

        float d = mapFragment.getResources().getDisplayMetrics().density;

        int margin = (int) (dpValue * d);
        int top = (int) (topBottom * d);

        binding.detailCampingContainer.setClipToPadding(false);
        binding.detailCampingContainer.setPadding(margin, top, margin, top);
        binding.detailCampingContainer.setPageMargin(margin / 2);

        binding.detailCampingContainer.setAdapter(mapSlideAdapter);
    }


    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        //MapFragment로 값 이동
        parsingMsgEvent.onParSingMsg(resultMsg);
    }

    @Override
    public void setMapReSetting(String location, String choiceArea, String choiceCity, String cityMsg, ResultMsg  resultMsg, MapSlideMsg mapSlideMsg) {
        int mapX = 0;
        int mapY = 0;

        Log.v("맵뷰 테스트/MapSlideDailog/2", String.valueOf(resultMsg.getArrayList().size()));
        //MapFragment로 한번 더 값 이동
        mapReSetting.setMapReSetting(location, choiceArea, choiceCity, cityMsg,resultMsg, mapSlideMsg);
        Log.v("맵뷰 테스트/MapSlideDailog", String.valueOf(resultMsg.getArrayList().size()));
    }

    @Override
    public void setEvent(int event) {

        areaChoiceDialog = new MapAreaDialog(context, (DialogMsgEvent2) MapSlideDialog.this, 2);
        areaChoiceDialog.setAreaResult((MapSlideMsgEvent) MapSlideDialog.this, new ResultMsg("categoreMsg"));
        areaChoiceDialog.ChoiceEvent((ActivityEvent) MapSlideDialog.this);
        areaChoiceDialog.setReroadMap((MapReSetting) MapSlideDialog.this);
        areaChoiceDialog.show();
        areaChoiceDialog.dismiss();
//        binding.choiceMap.setBackgroundColor(Color.parseColor("#FAAC58"));
    }

}


