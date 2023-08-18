
package com.ssteam.nolcam.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssteam.nolcam.BuildConfig;
import com.ssteam.nolcam.Interface.DBDataMsg;
import com.ssteam.nolcam.Interface.MapParsingEvent;
import com.ssteam.nolcam.Interface.MapSlideMsgEvent;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.campingimg.CampingImgBody;
import com.ssteam.nolcam.generic.MapSlideMsg;
import com.ssteam.nolcam.generic.campingimg.CampingImg;
import com.ssteam.nolcam.generic.campingimg.Result;
import com.ssteam.nolcam.generic.ItemCamping;
import com.ssteam.nolcam.Interface.RetrofitInterface;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingBody;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingItem;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingItems;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingResponse;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingResult;
import com.ssteam.nolcam.generic.mydbcamping.DbCampingBody;
import com.ssteam.nolcam.generic.mydbcamping.DbCampingItem;
import com.ssteam.nolcam.generic.mydbcamping.DbCampingItems;
import com.ssteam.nolcam.generic.mydbcamping.DbCampingResponse;
import com.ssteam.nolcam.generic.mydbcamping.DbCampingResult;
import com.ssteam.nolcam.generic.mydbcamping.DbcampingHeader;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class ConnectPHP {
    String TAG = "Retrofit";
    int queryNum;

    double mapX;
    double mapY;

    String keyword;
    String area;
    String city;
    String category;

    int ranking;

    String campingID = "";

    protected int contentId;

    //이미지 파싱
    protected String serviceKey = "0Dcrlzpi0vClkhL4IFM9IX2fuwcLWHx6Ggf7Q1Ju0q8eMpzbhMXAtdh%2FJ%2B0zeX9Q087xx3WcoMv%2BXwqZ%2FI8MXg%3D%3D";
    protected String MobileOS = "AND";
    protected String MobileApp = "NolCam";
    protected String type = "json";

    private ParsingMsgEvent parsingMsgEvent;
    private ResultMsg resultMsg;
    private MapSlideMsgEvent mapSlideMsgEvent;
    private MapSlideMsg mapSlideMsg;
    private DBDataMsg dbDataMsg;

    private MapParsingEvent mapParsingEvent;
    private int parsingNum;

    private int numofrows;
    private int pageno;
    private int baseCall;

    private String syncType;
    private int syncday;

    private ArrayList<Camping> insertArray;
    private ArrayList<Camping> updateArray;
    private ArrayList<Camping> deleteArray;


    public void setMapTab(int queryNum, double mapY, double mapX, String setDoNm, String setSigunguNm) {
        this.queryNum = queryNum;
        this.mapY = mapY;
        this.mapX = mapX;
        this.area = setDoNm;
        this.city = setSigunguNm;
    }

    public void setGps(double mapX, double mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public void setMapEvent(MapSlideMsgEvent mapSlideMsgEvent, ResultMsg resultMsg, MapSlideMsg mapSlideMsg) {
        this.mapSlideMsgEvent = mapSlideMsgEvent;
        this.resultMsg = resultMsg;
        this.mapSlideMsg = mapSlideMsg;
    }

    public void setEvent(ParsingMsgEvent parsingMsgEvent, ResultMsg resultMsg) {
        this.parsingMsgEvent = parsingMsgEvent;
        this.resultMsg = resultMsg;
    }

    public void setDBEvent(DBDataMsg dbDataMsg, ResultMsg resultMsg) {
        this.dbDataMsg = dbDataMsg;
        this.resultMsg = resultMsg;
    }

    public void setSearchForm(String keyword, String area, String city, String category) {
        this.keyword = keyword;
        this.area = area;
        this.city = city;
        this.category = category;
    }

    public void setSlideEvent(MapSlideMsgEvent mapSlideMsgEvent, MapSlideMsg mapSlideMsg) {
        this.mapSlideMsgEvent = mapSlideMsgEvent;
        this.mapSlideMsg = mapSlideMsg;
    }

    public void setBaseParameter(int baseCall, int numofrows, int pageno) {
        this.baseCall = baseCall;
        this.numofrows = numofrows;
        this.pageno = pageno;
    }

    public void setSyncModParameter(String syncType, int syncday) {
        this.syncType = syncType;
        this.syncday = syncday;
    }

    public void setMapParsingNum(int parsingNum) {
        this.parsingNum = parsingNum;
    }

    public void setCampingID(String campingID) {
        this.campingID = campingID;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public void setMuiltQuery(ArrayList<Camping> insertArray, ArrayList<Camping> updateArray, ArrayList<Camping> deleteArray) {
        this.insertArray = insertArray;
        this.updateArray = updateArray;
        this.deleteArray = deleteArray;
    }


    //okhttp로 통신상태 확인
    private HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
            }
        });
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private RetrofitInterface setRetrofit(int callIndex) {
        retrofit2.Retrofit retrofit = null;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        Gson gson = new GsonBuilder().setLenient().create();

        if (callIndex == 5 || callIndex == 8 || callIndex == 1 || callIndex == 13) {
            //GoCamping
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


            if (BuildConfig.DEBUG) {
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.level(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("http://apis.data.go.kr/B551011/GoCamping/")    // baseUrl 등록
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Gson 변환기 등록
                    .client(client)
                    .build();
        } else {
            //phpmyadmin

            if (BuildConfig.DEBUG) {
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.level(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://isungu09.cafe24.com/")    // baseUrl 등록
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Gson 변환기 등록
                    .client(client)
                    .build();
        }

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);   // 레트로핏 인터페이스 객체 구현

        return service;
    }

    //getGsonString() 메소드에서 사용되는 메소드
    private JsonObject getQueryList(ArrayList<Camping> arrayList, String arrayType) {
        JsonArray jsonArray = new JsonArray();
        JsonArray jsonArray1 = new JsonArray();
        JsonObject jsonObject1 = new JsonObject();

            for (int i = 0; i < arrayList.size(); i++) {
                Camping camping = arrayList.get(i);

                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("contentId", camping.contentId);       //콘텐츠 ID
                jsonObject.addProperty("facltNm", camping.facltNm);     //야영장명
                jsonObject.addProperty("lineIntro", camping.lineIntro);       //한줄소개
                jsonObject.addProperty("intro", camping.intro);       //소개
                jsonObject.addProperty("allar", camping.allar);      //전체면적
                jsonObject.addProperty("insrncAt", camping.insrncAt);       //영업배상책임보험 가입여부(Y:사용, N:미사용)
                jsonObject.addProperty("trsagntNo",camping.trsagntNo);      //관광사업자번호
                jsonObject.addProperty("bizrno", camping.bizrno);     //사업자번호
                jsonObject.addProperty("mgcDiv", camping.mgcDiv);         //운영기관.관리기관
                jsonObject.addProperty("manageSttus", camping.manageSttus);        //운영상태.관리상태
                jsonObject.addProperty("hvofBgnde", camping.hvofBgnde);      //휴장기간.휴무기간 시작일
                jsonObject.addProperty("hvofEnddle", camping.hvofEnddle);     //휴장기간.휴무기간 종료일
                jsonObject.addProperty("featureNm", camping.featureNm);      //특징
                jsonObject.addProperty("induty", camping.induty);     //업종
                jsonObject.addProperty("lctCl", camping.lctCl);       //입지구분
                jsonObject.addProperty("doNm", camping.doNm);       //도
                jsonObject.addProperty("sigunguNm", camping.sigunguNm);       //시,군,구
                jsonObject.addProperty("zipcode", camping.zipcode);        //우편번호
                jsonObject.addProperty("addr1", camping.addr1);      //주소 "
                jsonObject.addProperty("addr2", camping.addr2);      //상세주소
                jsonObject.addProperty("mapX", camping.mapX);       //경도(X)
                jsonObject.addProperty("mapY", camping.mapY);        //위도(Y)
                jsonObject.addProperty("direction", camping.direction);      //오시는 길 컨텐츠
                jsonObject.addProperty("tel", camping.tel);        //전화
                jsonObject.addProperty("homepage", camping.homepage);        //홈페이지
                jsonObject.addProperty("resveUrl", camping.resveUrl);       //예약 페이지
                jsonObject.addProperty("resveCl", camping.resveCl);       //예약 구분
                jsonObject.addProperty("manageNmpr", camping.manageNmpr);     //상주관리인원
                jsonObject.addProperty("mangeDivNm", camping.mangeDivNm);     //운영주체.관리주체 (직영, 위탁)
                jsonObject.addProperty("gnrlSiteCo", camping.gnrlSiteCo);     //주요시설 일반야영장
                jsonObject.addProperty("autoSiteCo", camping.autoSiteCo);      //주요시설 자동차야영장
                jsonObject.addProperty("glampSiteCo", camping.glampSiteCo);        //주요시설 글램핑
                jsonObject.addProperty("caravSiteCo", camping.caravSiteCo);        //주요시설 카라반
                jsonObject.addProperty("indvdlCaravSiteCo", camping.indvdlCaravSiteCo);      //주요시설 개인 카라반

                jsonObject.addProperty("sitedStnc", camping.sitedStnc);      //사이트간 거리

                jsonObject.addProperty("siteMg1Width", camping.siteMg1Width);       //사이트 크기1 가로
                jsonObject.addProperty("siteMg2Width", camping.siteMg2Width);       //사이트 크기2 가로
                jsonObject.addProperty("siteMg3Width", camping.siteMg3Width);       //사이트 크기3 가로

                jsonObject.addProperty("siteMg1Vrticl", camping.siteMg1Vrticl);      //사이트 크기1 세로
                jsonObject.addProperty("siteMg2Vrticl", camping.siteMg2Vrticl);      //사이트 크기2 세로
                jsonObject.addProperty("siteMg3Vrticl" , camping.siteMg3Vrticl);      //사이트 크기3 세로

                jsonObject.addProperty("siteMg1Co", camping.siteMg1Co);
                jsonObject.addProperty("siteMg2Co", camping.siteMg2Co);      //사이트 크기2 수량
                jsonObject.addProperty("siteMg3Co" , camping.siteMg2Co);      //사이트 크기3 수량

                jsonObject.addProperty("siteBottomCl1", camping.siteBottomCl1);      //잔디
                jsonObject.addProperty("siteBottomCl2" , camping.siteBottomCl2);      //파쇄석
                jsonObject.addProperty("siteBottomCl3", camping.siteBottomCl3);      //테크
                jsonObject.addProperty("siteBottomCl4", camping.siteBottomCl4);      //자갈
                jsonObject.addProperty("siteBottomCl5", camping.siteBottomCl5);      //맨흙

                jsonObject.addProperty("tooltip", camping.tooltip);        //툴팁
                jsonObject.addProperty("glampInnerFclty", camping.glampInnerFclty);        //글램핑 - 내부시설
                jsonObject.addProperty("caravInnerFclty", camping.caravInnerFclty);        //카라반 - 내부시설
                jsonObject.addProperty("prmisnDe", camping.prmisnDe);       //인허가일자
                jsonObject.addProperty("operPdCl", camping.operPdCl);       //운영기간
                jsonObject.addProperty("operDeCl", camping.operDeCl);       //운영일

                jsonObject.addProperty("trlerAcmpnyAt", camping.trlerAcmpnyAt);      //개인 트레일러 동반 여부(Y:사용, N:미사용)
                jsonObject.addProperty("caravAcmpnyAt", camping.caravAcmpnyAt);      //개인 카라반 동반 여부(Y:사용, N:미사용)
                jsonObject.addProperty("toiletCo", camping.toiletCo);       //화장실 개수
                jsonObject.addProperty("swrmCo", camping.swrmCo);     //샤워실 개수
                jsonObject.addProperty("brazierCl", camping.brazierCl);    //화로대
                jsonObject.addProperty("sbrsCl", camping.sbrsCl);      //부대시설
                jsonObject.addProperty("wtrplCo", camping.wtrplCo);     //개수대 개수
                jsonObject.addProperty("sbrsEtc", camping.sbrsEtc);     //부대시설 기타
                jsonObject.addProperty("posblFcltyCl", camping.posblFcltyCl);   //주변이용가능시설
                jsonObject.addProperty("posblFcltyEtc", camping.posblFcltyCl);  //주변이용가능시설 기타
                jsonObject.addProperty("clturEventAt", camping.clturEventAt);   //자체문화행사 여부(Y:사용, N:미사용)
                jsonObject.addProperty("clturEvent", camping.clturEvent);     //자체문화행사명
                jsonObject.addProperty("exprnProgrmAt", camping.exprnProgrmAt);  //체험프로그램 여부(Y:사용, N:미사용)
                jsonObject.addProperty("exprnProgrm", camping.exprnProgrm);     //체험프로그램명
                jsonObject.addProperty("extshrCo", camping.extshrCo);       //소화기 개수
                jsonObject.addProperty("frprvtWrppCo", camping.frprvtWrppCo);       //방화수 개수
                jsonObject.addProperty("frprvtSandCo", camping.frprvtSandCo);       //방화사 개수
                jsonObject.addProperty("fireSensorCo", camping.fireSensorCo);       //화재감지기 개수
                jsonObject.addProperty("themaEnvrnCl", camping.themaEnvrnCl);       //테마환경
                jsonObject.addProperty("eqpmnLendCl", camping.eqpmnLendCl);        //캠핑장비대여
                jsonObject.addProperty("animalCmgCl", camping.animalCmgCl);        //애완동물출입
                jsonObject.addProperty("tourEraCl", camping.tourEraCl);      //여행시기
                jsonObject.addProperty("firstImageUrl", camping.firstImageURL);      //대표이미지
                jsonObject.addProperty("createdtime", camping.createdtime);        //등록일
                jsonObject.addProperty("modifiedtime", camping.modifiedtime);       //수정일

                jsonArray.add(jsonObject);
            }
        jsonObject1.add(arrayType,jsonArray);

        return jsonObject1;
    }

    private MultiReturnGson getGsonString(ArrayList<Camping> insertArray, ArrayList<Camping> updateArray, ArrayList<Camping> delectArray) {

        JsonObject jsonObject1 = new JsonObject();
        JsonArray jsonArray1 = new JsonArray();
        JsonObject query_list_object = new JsonObject();

        if (insertArray.size() != 0) {

            jsonObject1 = getQueryList(insertArray, "insertArray");
            jsonArray1.add(jsonObject1);
        }

        if (updateArray.size() != 0) {


            jsonObject1 = getQueryList(updateArray, "updateArray");
            jsonArray1.add(jsonObject1);

        }

        if (deleteArray.size() != 0){


            jsonObject1 = getQueryList(delectArray, "deleteArray");
            jsonArray1.add(jsonObject1);
        }

        query_list_object.add("response",jsonArray1);





        Gson gson = new GsonBuilder().setLenient().create();

        MultiReturnGson multiReturnGson = new MultiReturnGson(gson.toJson(query_list_object), gson);

        return multiReturnGson;

    }


    public void createCall(int callIndex) {

        if (callIndex == 1) { // 신규, 주변 캠핑장
            if (syncday == 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.getDefault());
                Date date = new Date();
                syncday = Integer.valueOf(sdf.format(date));

                if (resultMsg.getType().equals("NewCamping")){
                    this.syncType = "A";
                }
            }

            Call<GoCampingResult> call = setRetrofit(1).getSlideCamping(serviceKey, numofrows, pageno, MobileOS, MobileApp, syncType, syncday, type);
            Log.e("retrofit test " + callIndex + " :", call.request().url() + "");

            if (mapX != 0 && resultMsg.getType().equals("GpsCamping") ) {
                call = setRetrofit(1).getSlideCamping(serviceKey, numofrows, pageno, MobileOS, MobileApp, mapY, mapX, 5000, type);
            }

            call.enqueue(new Callback<GoCampingResult>() {
                @Override
                public void onResponse(Call<GoCampingResult> call, Response<GoCampingResult> response) {
                    String success = "SUCCESS";

                    if (response.isSuccessful()) {

//                        GoCampingResult result = response.body();
//                        GoCampingResponse goCampingResponse = result.getResponse();
//                        GoCampingItems items = goCampingResponse.getBody().getItems();
//
//                        ArrayList<GoCampingItem> arrayList = items.getItem();
//                        getData(arrayList, callIndex);

                        GoCampingResult result = response.body();
                        GoCampingResponse goCampingResponse = result.getResponse();
                        GoCampingItems items = goCampingResponse.getBody().getItems();
//                        ArrayList<Camping> arrayList = getData(items.getCampings(), callIndex);
                        ArrayList<Camping> arrayList = null;

                        if (resultMsg.getType().equals("GpsCamping")){

                            arrayList = getData(items.getCampings(), callIndex);

                        }else {
                            if (syncType.equals("A")) {

                                arrayList = getData(items.getCampings(), callIndex);

                            } else if (syncType.equals("U") && resultMsg.getType().equals("Logo")) {
                                arrayList = getData(items.getCampings(), callIndex);

                            } else if (syncType.equals("D") && resultMsg.getType().equals("Logo")) {
                                arrayList = getData(items.getCampings(), callIndex);
                            } else {
                                arrayList = new ArrayList<>();
                            }
                        }


                        resultMsg.setArrayList(arrayList);
                        resultMsg.setResult(resultMsg.getResult());

                        Log.v("retrofit result msg", resultMsg.getType());
                    } else {

                        success = "FAIL";
                        Log.v("retrofit result msg", success);
                    }

                    resultMsg.setResult(success);
                    parsingMsgEvent.onParSingMsg(resultMsg);
                }

                @Override
                public void onFailure(Call<GoCampingResult> call, Throwable t) {
                    Log.v("retrofit result msg", resultMsg.getType());

                    ArrayList<Camping> arrayList = new ArrayList<>();
                    resultMsg.setResult(syncType);
                    resultMsg.setArrayList(arrayList);
                    parsingMsgEvent.onParSingMsg(resultMsg);
                    getStackTrace(t, callIndex);
                }
            });

        } else if (callIndex == 2) { // 캠핑장 상세정보
            Call<DbCampingResult> call = setRetrofit(2).getCampingDetail(campingID);
            call.enqueue(new Callback<DbCampingResult>() {
                @Override
                public void onResponse(Call<DbCampingResult> call, Response<DbCampingResult> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        DbCampingResult result = response.body();
                        DbCampingResponse dbCampingResponse = result.getResponse();
                        DbCampingItems dbCampingItems = dbCampingResponse.getBody().getItems();
                        ArrayList<Camping> arrayList = getAllData(dbCampingItems.getItems(), callIndex);

                        Log.e("개수학인", String.valueOf(arrayList.size()));

                        resultMsg.setArrayList(arrayList);
                    } else {
                        success = "FAIL";
                    }

                    resultMsg.setResult(success);
                    parsingMsgEvent.onParSingMsg(resultMsg);
                }

                @Override
                public void onFailure(Call<DbCampingResult> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }

            });

        } else if (callIndex == 3) { // 캠핑장 검색 결과
            Call<ItemCamping> call = setRetrofit(3).getKeyword(keyword, area, city, category);

            call.enqueue(new Callback<ItemCamping>() {
                @Override
                public void onResponse(Call<ItemCamping> call, Response<ItemCamping> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        ItemCamping result = response.body();
                        ArrayList<Camping> arrayList = result.itmes;
                        ArrayList<Camping> slideArray = new ArrayList<>();

                        String id = "";
                        String contentId = "";
                        String addr1 = "";
                        String facltNm = "";
                        String firstImageUrl = "";
                        String induty = "";
                        double distance = 0.0;

                        //parsingNum 1 : 키워드 검색

                        if (parsingNum == 1) {
                            slideArray = getData(arrayList, callIndex);

                            mapSlideMsg.setArrayList(slideArray);
                            mapSlideMsg.setResultMsg("MapSlideMsg");
                            resultMsg.setType("Fail");

                            mapSlideMsgEvent.setArray(mapSlideMsg, resultMsg, 1);

                        } else if (parsingNum == 2) {

                            slideArray = getData(arrayList, callIndex);

                            resultMsg.setArrayList(slideArray);
                            resultMsg.setCampList(slideArray);
                            resultMsg.setType("DialogMsg");
                            mapSlideMsg.setResultMsg("Fail");


                            //AreachoiceDialog로 이동
                            mapSlideMsgEvent.setArray(mapSlideMsg, resultMsg, 2);


                        } else if (parsingNum == 3) {

                            slideArray = getData(arrayList, callIndex);


                            resultMsg.setArrayList(slideArray);

                            parsingMsgEvent.onParSingMsg(resultMsg);
                        } else {
                            getData(arrayList, callIndex);

                            resultMsg.setArrayList(arrayList);
                            parsingMsgEvent.onParSingMsg(resultMsg);
                        }


                    } else {  // 실패시 실패사유도 같이 적어주면 좋긴 할 것 같다.
                        success = "FAIL";
                    }
                }

                @Override
                public void onFailure(Call<ItemCamping> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }

            });

        } else if (callIndex == 4) { // 인기 캠핑장
            /*
             * 주, 월 ,년 인기 캠핑장 구하기
             * getRanking의 queryNum에
             * 1은 주 단위로 집계
             * 2는 월 단위로 집계
             * 3은 년 단위로 집계
             * */
            Call<ItemCamping> call = setRetrofit(4).getRanking(ranking);
            call.enqueue(new Callback<ItemCamping>() {
                @Override
                public void onResponse(Call<ItemCamping> call, Response<ItemCamping> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        ItemCamping result = response.body();
                        ArrayList<Camping> arrayList = result.itmes;
//                        getData(arrayList, callIndex);

                        resultMsg.setArrayList(arrayList);
                        parsingMsgEvent.onParSingMsg(resultMsg);


                    } else {  // 실패시 실패사유도 같이 적어주면 좋긴 할 것 같다.
                        success = "FAIL";
                    }

                }

                @Override
                public void onFailure(Call<ItemCamping> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }
            });


        }
        //이미지 파싱
        else if (callIndex == 5) {
            Call<Result> call = setRetrofit(5).getImg(serviceKey, MobileOS, MobileApp, contentId, type);

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        Result result = response.body();
                        CampingImgBody campingImgBody = result.getResponse().getBody();
                        ArrayList<CampingImg> itemCampingImg = campingImgBody.getItems().getItem();

//                        getData((ArrayList) itemCampingImg, callIndex);

                        resultMsg.setArrayList(itemCampingImg);
                        parsingMsgEvent.onParSingMsg(resultMsg);
                        Log.e("goCamping", success);
                        Log.e("goCamping Link", call.request() + "");
                    } else {

                        success = "FAIL";
                    }

//                resultMsg.setResult(success);
//                parsingMsgEvent.onParSingMsg(resultMsg);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                    getStackTrace(t, callIndex);
                }
            });
        }

        // 검색기록 저장
        else if (callIndex == 6) {
            Call<ItemCamping> call = setRetrofit(6).getDateInsert(campingID);
            call.enqueue(new Callback<ItemCamping>() {
                @Override
                public void onResponse(Call<ItemCamping> call, Response<ItemCamping> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {


                    } else {
                        success = "FAIL";
                    }
                }

                @Override
                public void onFailure(Call<ItemCamping> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }
            });
        }
        //지도 탭
        else if (callIndex == 7) {
            Call<ItemCamping> call = setRetrofit(7).getGPS(queryNum, mapY, mapX, area, city);
            call.enqueue(new Callback<ItemCamping>() {
                @Override
                public void onResponse(Call<ItemCamping> call, Response<ItemCamping> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        ItemCamping result = response.body();
                        ArrayList<Camping> arrayList = result.itmes;
//                        getData(arrayList, callIndex);

                        resultMsg.setArrayList(arrayList);
                        parsingMsgEvent.onParSingMsg(resultMsg);
                    } else {
                        success = "FAIL";
                    }
                }

                @Override
                public void onFailure(Call<ItemCamping> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }
            });
        }
        // 고캠핑 기본 정보 조회
        else if (callIndex == 8) {
            Call<GoCampingResult> call = setRetrofit(8).getGoCampingTotalCount(serviceKey, 10, 1, MobileOS, MobileApp, type);
            call.enqueue(new Callback<GoCampingResult>() {
                @Override
                public void onResponse(Call<GoCampingResult> call, Response<GoCampingResult> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        GoCampingResult result = response.body();
                        GoCampingBody body = result.getResponse().getBody();
                        Log.e("goCamping", success);
                        Log.e("goCamping 성공", String.valueOf(body.getTotalCount()));
                        Log.e("goCamping Link", call.request() + "");

                        resultMsg.setCallIndex(8, String.valueOf(body.getTotalCount()));
                        parsingMsgEvent.onParSingMsg(resultMsg);

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<GoCampingResult> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }
            });
        }//DB 토탈 카운트
        else if (callIndex == 9) {
            Call<DbCampingResult> call = setRetrofit(9).getDBcount();
            call.enqueue(new Callback<DbCampingResult>() {
                @Override
                public void onResponse(Call<DbCampingResult> call, Response<DbCampingResult> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        DbCampingResult result = response.body();
                        String totalCount = result.getResponse().getBody().getTotalCount();

                        resultMsg.setCallIndex(9, totalCount);
                        parsingMsgEvent.onParSingMsg(resultMsg);
                        Log.e("Retrofit9", totalCount);
                    } else {
                        success = "FAIL";
                    }
                }

                @Override
                public void onFailure(Call<DbCampingResult> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }
            });
            //DB 신규 캠핑장 추가
        } else if (callIndex == 10) {

            retrofit2.Retrofit retrofit = null;
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            MultiReturnGson multiReturnGson = getGsonString(insertArray, updateArray, deleteArray);

            String jsonData = multiReturnGson.getStringValue();
            Gson customs_gson = multiReturnGson.getGson();

            if (BuildConfig.DEBUG) {
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.level(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://isungu09.cafe24.com/")    // baseUrl 등록
                    .addConverterFactory(ScalarsConverterFactory.create())   //Response 형태가 구조를 갖춘 json 형태가 아닌 string 형태로 받을 때 사용하는 컨버터 응답값을 String 형태로 받을 때 사용
                    .addConverterFactory(GsonConverterFactory.create(customs_gson))  // Gson 변환기 등록
                    .client(client)
                    .build();

            Log.e("testURL/ jsonData ",jsonData);

            RequestBody requestBody = RequestBody.create(ByteString.encodeUtf8(jsonData), MediaType.parse("application/json"));

            RetrofitInterface service = retrofit.create(RetrofitInterface.class);

            Call<DbCampingResult> call = service.sendCampList(requestBody);

            Log.e("testURL", call.request().url().toString());
            Log.e("testURL", call.request() + "");

            call.enqueue(new Callback<DbCampingResult>() {
                @Override
                public void onResponse(Call<DbCampingResult> call, Response<DbCampingResult> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

//                        resultMsg.setResult(respo);
//                        resultMsg.setCallIndex(10, db);
//                        parsingMsgEvent.onParSingMsg(resultMsg);
                    } else {
                        success = "FAIL";
                    }
                }

                @Override
                public void onFailure(Call<DbCampingResult> call, Throwable t) {

                    getStackTrace(t, callIndex);
                }
            });
        }
        //DB 최근 업데이트 날짜 받아오기
        else if (callIndex == 12) {
            Call<DbCampingResult> call = setRetrofit(12).setDBUpdateCheck();
            Log.e("testURL", call.request().url().toString());
            Log.e("testURL", call.request() + "");
            call.enqueue(new Callback<DbCampingResult>() {
                @Override
                public void onResponse(Call<DbCampingResult> call, Response<DbCampingResult> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        DbCampingResult result = response.body();
                        DbCampingBody body = result.getResponse().getBody();
                        DbCampingItems items = body.getItems();
                        String updatedate = items.getUitems().get(0).getUpdatedate();

//                        Log.e("Retrofit10 test ", updatedate);

                        resultMsg.setSyncType("null");
                        resultMsg.setResult(updatedate);
                        dbDataMsg.onDBDataMsg(resultMsg);
                    } else {
                        success = "FAIL";
                    }
                }

                @Override
                public void onFailure(Call<DbCampingResult> call, Throwable t) {
                    getStackTrace(t, callIndex);
                }
            });

        }// 고캠핑 캠핑장 업데이트 정보
        else if (callIndex == 13) {
            Call<GoCampingResult> call = setRetrofit(13).getSyncCampList(serviceKey, numofrows, pageno, MobileOS, MobileApp, syncType, syncday, type);
            Log.e("retrofit test " + callIndex + " :", call.request().url() + "");
            call.enqueue(new Callback<GoCampingResult>() {
                @Override
                public void onResponse(Call<GoCampingResult> call, Response<GoCampingResult> response) {
                    String success = "SUCCESS";
                    if (response.isSuccessful()) {

                        GoCampingResult result = response.body();
                        GoCampingResponse goCampingResponse = result.getResponse();
                        GoCampingItems items = goCampingResponse.getBody().getItems();
                        ArrayList<Camping> arrayList = getAllData(items.getCampings(), callIndex);

                        resultMsg.setArrayList(arrayList);

                    } else {
                        success = "FAIL";
                    }
                    resultMsg.setResult(success);
                    resultMsg.setSyncType(syncType);
                    dbDataMsg.onDBDataMsg(resultMsg);
                }

                @Override
                public void onFailure(Call<GoCampingResult> call, Throwable t) {
                    ArrayList<Camping> arrayList = new ArrayList<>();

                    resultMsg.setArrayList(arrayList);
                    resultMsg.setSyncType(syncType);
                    dbDataMsg.onDBDataMsg(resultMsg);
                    Log.v("retrofit result msg", resultMsg.getType());

                    getStackTrace(t, callIndex);
                }
            });
        }
        //DB 최근 업데이트 날짜 변경
        else if (callIndex == 14) {
            Call<DbCampingResult> call = setRetrofit(14).setUpdateDB();
            call.enqueue(new Callback<DbCampingResult>() {
                @Override
                public void onResponse(Call<DbCampingResult> call, Response<DbCampingResult> response) {

                    DbcampingHeader header = response.body().getResponse().getHeader();
                    String resultMsg = header.getResultMsg();

                    Log.v("retrofit callIndex " + callIndex + " : ", resultMsg);
                }

                @Override
                public void onFailure(Call<DbCampingResult> call, Throwable t) {
                    Log.v("retrofit result msg", resultMsg.getType());

                    getStackTrace(t, callIndex);
                }
            });
        }

    }

    private void getStackTrace(Throwable t, int callIndex) {
        try {
            Log.e("예외 리턴/" + callIndex, t.getMessage());
            throw t; // 예외 다시 던지기
        } catch (IOException e) {
            // 네트워크 예외 처리
            Log.e("네트워크 예외/" + callIndex, e.getMessage());
        } catch (Throwable throwable) {
            // 기타 예외 처리
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);

            String stackTraceString = sw.toString();
            Log.e("Retrofit/" + callIndex, "Exception Stack Trace:\n" + stackTraceString);
        }

        Log.e("Retrofit" + callIndex + " : ", t.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        String stackTraceString = sw.toString();
        Log.e("Retrofit/" + callIndex, "Exception Stack Trace:\n" + stackTraceString);

    }


    //커넥p
    private ArrayList<Camping> getAllData(ArrayList<Camping> arrayList, int callIndex) {
        ArrayList<Camping> campings = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {

            String id = String.valueOf(arrayList.get(i).getDb_id());
            String contentId = arrayList.get(i).getContentId();
            String createdtime = arrayList.get(i).getCreatedtime();
            String facltNm = arrayList.get(i).getFacltNm();
            String lineIntro = arrayList.get(i).getLineIntro();
            String intro = arrayList.get(i).getIntro();
            String featureNm = arrayList.get(i).getFeatureNm();
            String induty = arrayList.get(i).getInduty();
            String firstImageURL = arrayList.get(i).getFirstImageURL();
            String lctCl = arrayList.get(i).getLctCl();
            String allar = arrayList.get(i).getAllar();
            String addr1 = arrayList.get(i).getAddr1();
            String addr2 = arrayList.get(i).getAddr2();
            String tel = arrayList.get(i).getTel();
            String homepage = arrayList.get(i).getHomepage();
            String resveUrl = arrayList.get(i).getResveUrl();
            String resveCl = arrayList.get(i).getResveCl();
            String exprnProgrm = arrayList.get(i).getExprnProgrm();
            String posblFcltyCl = arrayList.get(i).getPosblFcltyCl();
            String sbrsCl = arrayList.get(i).getSbrsCl();
            double mapX = arrayList.get(i).getMapX();
            double mapY = arrayList.get(i).getMapY();
            String direction = arrayList.get(i).getDirection();
            String caravSiteCo = arrayList.get(i).getCaravSiteCo();
            String glampSiteCo = arrayList.get(i).getGlampSiteCo();
            String indvdlCaravSiteCo = arrayList.get(i).getIndvdlCaravSiteCo();
            String siteMg1Width = arrayList.get(i).getSiteMg1Width();
            String siteMg2Width = arrayList.get(i).getSiteMg2Width();
            String siteMg3Width = arrayList.get(i).getSiteMg3Width();
            String siteMg1Vrticl = arrayList.get(i).getSiteMg1Vrticl();
            String siteMg2Vrticl = arrayList.get(i).getSiteMg2Vrticl();
            String siteMg3Vrticl = arrayList.get(i).getSiteMg3Vrticl();
            String siteMg1Co = arrayList.get(i).getSiteMg1Co();
            String siteMg2Co = arrayList.get(i).getSiteMg2Co();
            String siteMg3Co = arrayList.get(i).getSiteMg3Co();
            String siteBottomCl1 = arrayList.get(i).getSiteBottomCl1();
            String siteBottomCl2 = arrayList.get(i).getSiteBottomCl2();
            String siteBottomCl3 = arrayList.get(i).getSiteBottomCl3();
            String siteBottomCl4 = arrayList.get(i).getSiteBottomCl4();
            String siteBottomCl5 = arrayList.get(i).getSiteBottomCl5();
            String tooltip = arrayList.get(i).getTooltip();
            String glampInnerFclty = arrayList.get(i).getGlampInnerFclty();
            String caravInnerFclty = arrayList.get(i).getCaravInnerFclty();
            String operPdCl = arrayList.get(i).getOperPdCl();
            String operDeCl = arrayList.get(i).getOperDeCl();
            String trlerAcmpnyAt = arrayList.get(i).getTrlerAcmpnyAt();
            String caravAcmpnyAt = arrayList.get(i).getCaravAcmpnyAt();
            String toiletCo = arrayList.get(i).getToiletCo();
            String swrmCo = arrayList.get(i).getSwrmCo();
            String wtrplCo = arrayList.get(i).getWtrplCo();
            int extshrCo = arrayList.get(i).getExtshrCo();
            int frprvtWrppCo = arrayList.get(i).getFrprvtWrppCo();
            int frprvtSandCo = arrayList.get(i).getFrprvtSandCo();
            int fireSensorCo = arrayList.get(i).getFireSensorCo();
            String modifiedtime = arrayList.get(i).getModifiedtime();
            String insrncAt = arrayList.get(i).getInsrncAt();
            String trsagntNo = arrayList.get(i).getTrsagntNo();
            String bizrno = arrayList.get(i).getBizrno();
            String mgcDiv = arrayList.get(i).getMgcDiv();
            String manageSttus = arrayList.get(i).getManageSttus();
            String hvofBgnde = arrayList.get(i).getHvofBgnde();
            String hvofEnddle = arrayList.get(i).getHvofEnddle();
            String doNm = arrayList.get(i).getDoNm();
            String sigunguNm = arrayList.get(i).getSigunguNm();
            String zipcode = arrayList.get(i).getZipcode();
            String manageNmpr = arrayList.get(i).getManageNmpr();
            String mangeDivNm = arrayList.get(i).getMangeDivNm();
            String gnrlSiteCo = arrayList.get(i).getGnrlSiteCo();
            String autoSiteCo = arrayList.get(i).getAutoSiteCo();
            String sitedStnc = arrayList.get(i).getSitedStnc();
            String prmisnDe = arrayList.get(i).getPrmisnDe();
            String brazierCl = arrayList.get(i).getBrazierCl();
            String sbrsEtc = arrayList.get(i).getSbrsEtc();
            String clturEventAt = arrayList.get(i).getClturEventAt();
            String clturEvent = arrayList.get(i).getClturEvent();
            String exprnProgrmAt = arrayList.get(i).getExprnProgrmAt();
            String themaEnvrnCl = arrayList.get(i).getThemaEnvrnCl();
            String eqpmnLendCl = arrayList.get(i).getEqpmnLendCl();
            String animalCmgCl = arrayList.get(i).getAnimalCmgCl();
            String tourEraCl = arrayList.get(i).getTourEraCl();

            campings.add(new Camping(id,contentId,createdtime,facltNm,lineIntro,intro,featureNm,induty,firstImageURL,lctCl,allar,addr1,addr2,tel,homepage,resveUrl,resveCl,exprnProgrm,posblFcltyCl,sbrsCl,mapX,
                    mapY,direction,caravSiteCo,glampSiteCo,indvdlCaravSiteCo,siteMg1Width,siteMg2Width,siteMg3Width,siteMg1Vrticl,siteMg2Vrticl,siteMg3Vrticl,siteMg1Co,siteMg2Co,siteMg3Co,
                    siteBottomCl1,siteBottomCl2,siteBottomCl3,siteBottomCl4,siteBottomCl5,tooltip,glampInnerFclty,caravInnerFclty,operPdCl,operDeCl,trlerAcmpnyAt,caravAcmpnyAt,toiletCo,
                    swrmCo,wtrplCo,extshrCo,frprvtWrppCo,frprvtSandCo,fireSensorCo,modifiedtime,insrncAt,trsagntNo,bizrno,mgcDiv,manageSttus,hvofBgnde,hvofEnddle,doNm,sigunguNm,zipcode,
                    manageNmpr,mangeDivNm,gnrlSiteCo,autoSiteCo,sitedStnc,prmisnDe,brazierCl,sbrsEtc,clturEventAt,clturEvent,exprnProgrmAt,themaEnvrnCl,eqpmnLendCl,animalCmgCl,tourEraCl));

            arrayList.get(i).toString();
        }

        return campings;
    }


    private ArrayList<Camping> getData(ArrayList<Camping> arrayList, int callIndex) {
        ArrayList<Camping> campings = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            String id = "";
            String contentId = "";
            String addr1 = "";
            String facltNm = "";
            String firstImageUrl = "";
            String induty = "";

            double distance = 0.0;

            id = arrayList.get(i).getId();
            contentId = arrayList.get(i).getContentId();
            addr1 = arrayList.get(i).getAddr1();
            facltNm = arrayList.get(i).getFacltNm();
            firstImageUrl = arrayList.get(i).getFirstImageURL();
            induty = arrayList.get(i).getInduty();

            mapX = arrayList.get(i).getMapX();
            mapY = arrayList.get(i).getMapY();
            distance = arrayList.get(i).getDistance();

            campings.add(new Camping(id, contentId, addr1, facltNm, firstImageUrl, induty, mapX, mapY, distance));

        }

        return campings;
    }

}