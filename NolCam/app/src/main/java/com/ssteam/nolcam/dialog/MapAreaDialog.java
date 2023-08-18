package com.ssteam.nolcam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.Interface.ActivityEvent;
import com.ssteam.nolcam.Interface.DialogMsgEvent2;
import com.ssteam.nolcam.Interface.ItemChoiceEvent;
import com.ssteam.nolcam.Interface.MapReSetting;
import com.ssteam.nolcam.Interface.MapSlideMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.activity.MapFragment;
import com.ssteam.nolcam.adapter.MapAreaAdapter;
import com.ssteam.nolcam.adapter.MapCityAdapter;
import com.ssteam.nolcam.databinding.DialogMapAreaBinding;
import com.ssteam.nolcam.generic.MapSlideMsg;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.ConnectPHP;

public class MapAreaDialog extends Dialog implements ItemChoiceEvent, MapSlideMsgEvent {
    DialogMsgEvent2 dialogMsgEvent;

    String[] areas = {"서울시", "인천시", "대전시", "대구시", "광주시", "부산시", "울산시", "세종시", "경기도", "강원도", "충청북도", "충청남도", "경상북도", "경상남도", "전라북도", "전라남도", "제주도"};

    String seoul = "37.540705, 126.956764";
    String incheon = "37.469221, 126.573234";
    String daejeon = "36.321655, 127.378953";
    String daegu = "35.798838, 128.583052";
    String gwangju = "35.126033, 126.831302";
    String busan = "35.198362, 129.053922";
    String ulsan = "35.519301, 129.239078";
    String sejong = "36.5040736, 127.2494855";
    String gyeonggi = "37.567167, 127.190292";
    String gangwon = "37.555837, 128.209315";
    String chungbuk = "36.628503, 127.929344";
    String chungnam = "36.557229, 126.779757";
    String gyeongbuk = "36.248647, 128.664734";
    String gyeongnam = "35.259787, 128.664734";
    String jeolbuk = "35.716705, 127.144185";
    String jealnam = "34.819400, 126.893113";
    String jeju = "33.364805, 126.542671";

    String city1 = "강남구,강동구,강북구,강서구,관악구,광진구,구로구,금천구,노원구,도봉구,동대문구,동작구,마포구,서대문구,서초구,성동구,성북구,송파구,양천구,영등포구,용산구,은평구,종로구,중구,중랑구";
    String city2 = "강화군,계양구,미추홀구,남동구,동구,부평구,서구,연수구,옹진군,중구";
    String city3 = "대덕구,동구,서구,유성구,중구";
    String city4 = "남구,달서구,달성군,동구,북구,서구,수성구,중구";
    String city5 = "광산구,남구,동구,북구,서구";
    String city6 = "강서구,금정구,기장군,남구,동구,동래구,부산진구,북구,사상구,사하구,서구,수영구,연제구,영도구,중구,해운대구";
    String city7 = "중구,남구,동구,북구,울주군";
    String city8 = "조치원,연기면,연동면,부강면,금남면,장군면,연서면,전의면,소정면";
    String city9 = "가평군,고양시,과천시,광명시,광주시,구리시,군포시,김포시,남양주시,동두천시,부천시,성남시,수원시,시흥시,안산시,안성시,안양시,양주시,양평군,여주시,연천군,오산시,용인시,의왕시,의정부시,이천시,파주시,평택시,포천시,하남시,화성시";
    String city10 = "강릉시,고성군,동해시,삼척시,속초시,양구군,양양군,영월군,원주시,인제군,정선군,철원군,춘천시,태백시,평창군,홍천군,화천군,횡성군";
    String city11 = "괴산군,단양군,보은군,영동군,옥천군,음성군,제천시,진천군,청원시,청주시,충주시,증평군";
    String city12 = "공주시,금산군,논산시,당진시,보령시,부여군,서산시,서천군,아산시,예산군,천안시,청양군,태안군,홍성군,계룡시";
    String city13 = "경산시,경주시,고령군,구미시,군위군,김천시,문경시,봉화군,상주시,성주군,안동시,영덕군,영양군,영주시,영천시,예천군,울릉군,울진군,의성군,청도군,청송군,칠곡군,포항시";
    String city14 = "거제시,거창군,고성군,김해시,남해군,마산시,밀양시,사천시,산청군,양산시,의령군,진주시,진해시,창녕군,창원시,통영시,하동군,함안군,함양군,합천군";
    String city15 = "고창군,군산시,김제시,남원시,무주군,부안군,순창군,완주군,익산시,임실군,장수군,전주시,정읍시,진안군";
    String city16 = "강진군,고흥군,곡성군,광양시,구례군,나주시,담양군,목포시,무안군,보성군,순천시,신안군,여수시,영광군,영암군,왕도군,장성군,장흥군,진도군,함평군,해남군,화순군";
    String city17 = "남제주군,북제주군,서귀포시,제주시";




    String area;
    String[] cityList = new String[]{city1, city2, city3, city4, city5, city6, city7, city8, city9, city10, city11, city12, city13,
            city14, city15, city16, city17};

    String[] areaGps = new String[]{seoul, incheon, daejeon, daegu, gwangju, busan, ulsan, sejong, gyeonggi, gangwon, chungbuk, chungnam,
            gyeongbuk, gyeongnam, jeolbuk, jealnam,  jeju};

    int num = 0; // 1은 SearchFragment.class , 2는 MapFragment.class

    /*
     * 0 : 보이는 상태
     * -1 : 안보이는 상태
     */
    int areaPos = 0;
    int cityPos = -1;


    Context context;
    String result = "";

    String choiceArea = "";
    String choiceCity = "";
    String cityMsg="";

    String gpsX = "";
    String gpsY = "";


    String locationXY = "";


    int areaPosition;
    //private FragmentMapBinding binding;
    private MapSlideMsgEvent mapSlideMsgEvent;
    public ResultMsg resultMsg;
    private ActivityEvent activityEvent;
    private MapReSetting mapReSetting;

    public MapAreaDialog(@NonNull Context context,int num) {
        super(context);
        this.context = context;
        this.num = num;
    }

    public MapAreaDialog(@NonNull Context context, DialogMsgEvent2 dialogMsgEvent,int num) {
        super(context);
        this.dialogMsgEvent = dialogMsgEvent;
        this.context = context;
        this.num = num;
    }

    public void setAreaResult(MapSlideMsgEvent mapSlideMsgEvent, ResultMsg resultMsg ){
        this.mapSlideMsgEvent = mapSlideMsgEvent;
        this.resultMsg = resultMsg;
    }
    public void setReroadMap(MapReSetting mapReSetting){
        this.mapReSetting = mapReSetting;
    }

    public void ChoiceEvent(ActivityEvent activityEvent){
        this.activityEvent = activityEvent;
    }

    DialogMapAreaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_map_area, null, false);
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
        }

        ImageView dialogClose = findViewById(R.id.close_dialog);
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

                MapFragment mapFragment = new MapFragment();
                mapFragment.setBtnColor("#4D6A74");
            }
        });

        MapAreaAdapter areaChoiceAdapter = new MapAreaAdapter(areas, cityList);
        areaChoiceAdapter.setSlideEvent((ItemChoiceEvent) MapAreaDialog.this, new MapSlideMsg("SlideMsg"));

//        binding.areaList.setAdapter(areaChoiceAdapter);
        binding.areaChoiceBtn.setBackground(context.getResources().getDrawable(R.drawable.border_item_choice));

        RecyclerView recyclerView = findViewById(R.id.area_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new GridLayoutManager(getContext(), 3).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(areaChoiceAdapter);


        areaPos = 0;
        cityPos = -1;




        binding.areaChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.areaChoiceBtn.setBackgroundColor(R.drawable.border_item_choice);

                dismiss();
                activityEvent.setEvent(areaPos);


            }
        });


        binding.sigunguChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "지역을 선택해 주세요" , Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 3;
                outRect.right = 3;
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(areaChoiceAdapter);

        binding.choiceFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Null = "Not Choice";

                if (num == 1){
                    if (cityMsg.equals("checkCity")) {
                        dialogMsgEvent.onResult(choiceArea, choiceCity);
                    }else{
                        dialogMsgEvent.onResult(choiceArea, Null);
                    }

                    dismiss();

                }else {
                    String all = "전체";
                    ConnectPHP connectPHP = new ConnectPHP();
                    connectPHP.setMapEvent((MapSlideMsgEvent) MapAreaDialog.this, new ResultMsg("MapDialog"), new MapSlideMsg("Null"));

                    if (cityMsg.equals("checkCity")) {

                        connectPHP.setSearchForm(all, choiceArea, choiceCity, all);
                        connectPHP.createCall(3);
                        connectPHP.setMapParsingNum(2);


                    } else {
                        // 아래에서 콜백을 선언해서 얘가 멈추었고 아래 콜백 작업이 끝난 후 다시 코드가 돌아간다.
                        connectPHP.setSearchForm(all, choiceArea, all, all);
                        connectPHP.createCall(3);
                        connectPHP.setMapParsingNum(2);
                    }

                    dismiss();
                }
            }
        });


    }

    @Override
    public void itemChoice(int num, MapSlideMsg mapSlideMsg) {
        String checkMsg = "";
        if (num == 1) {
            this.choiceArea = mapSlideMsg.getResult();
            this.areaPosition = mapSlideMsg.getPosition();
            checkMsg = mapSlideMsg.getResultMsg();

            binding.areaChoiceBtn.setText(choiceArea);
        }else if (num  == 2){
            this.choiceCity = mapSlideMsg.getCity();
            this.areaPosition = mapSlideMsg.getPosition();
            this.cityMsg = mapSlideMsg.getResultMsg();

            binding.areaChoiceBtn.setText(choiceArea);
            binding.sigunguChoiceBtn.setText(choiceCity);

        }else{
        }

        if (checkMsg.equals("check")){
            String[] resultCity = setCityList(cityList[areaPosition]);
            MapCityAdapter cityChoiceAdapter = new MapCityAdapter(resultCity);
            cityChoiceAdapter.setSlideEvent((ItemChoiceEvent) MapAreaDialog.this, new MapSlideMsg("SlideMsg"));
            RecyclerView sigunguRv = findViewById(R.id.area_list);
            sigunguRv.setAdapter(cityChoiceAdapter);

            this.area = areaGps[areaPosition];
            String arrayLists = cityList[areaPosition];

            TextView area = (TextView) findViewById(R.id.area_choice_btn);
            area.setBackground(context.getResources().getDrawable(R.drawable.border_search_full));

            TextView sigungu = (TextView) findViewById(R.id.sigungu_choice_btn);
            sigungu.setBackground(context.getResources().getDrawable(R.drawable.border_item_choice));

            areaPos = -1;
            cityPos = 0;

        }



    }

    protected String[] setCityList(String city) {
        String[] cityList;
        cityList = city.split(",");

        for (int i=0; i<cityList.length; i++) {
        }

        return cityList;
    }

    /*AreaChoiceDialog -> MapSlideDialog */
    @Override
    public void setArray(MapSlideMsg mapSlideMsg, ResultMsg resultMsg, int msgNum) {
        this.result = resultMsg.getType();
//        Log.v("맵뷰/테스트 areaChoice", resultMsg.getCampList().get(0).getFacltNm());

        if (resultMsg.getArrayList().size() != 0){
            this.gpsX= String.valueOf(resultMsg.getCampList().get(0).getMapX());
            this.gpsY = String.valueOf(resultMsg.getCampList().get(0).getMapY());
            this.locationXY = gpsY +", "+gpsX;

            if (resultMsg.getType().equals("DialogMsg")){
                mapReSetting.setMapReSetting(locationXY, choiceArea, choiceCity, cityMsg, resultMsg, mapSlideMsg);
            }else{
                mapReSetting.setMapReSetting(locationXY, choiceArea, choiceCity, cityMsg, resultMsg, mapSlideMsg);
            }

            Log.v("맵뷰", resultMsg.getCampList().get(0).getFacltNm());

            mapSlideMsgEvent.setArray(mapSlideMsg,resultMsg,msgNum);

        }else{
            mapReSetting.setMapReSetting(area,choiceArea,choiceCity,"", resultMsg, mapSlideMsg);
            mapSlideMsgEvent.setArray(mapSlideMsg,resultMsg,msgNum);
        }


    }
}