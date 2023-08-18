package com.ssteam.nolcam.Interface;

import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.campingimg.Result;
import com.ssteam.nolcam.generic.ItemCamping;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingItem;
import com.ssteam.nolcam.generic.gocampingbasedata.GoCampingResult;
import com.ssteam.nolcam.generic.mydbcamping.DbCampingResult;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    // @GET( EndPoint-자원위치(URI) )
    // DataClass > 요청 GET에 대한 응답데이터를 받아서 DTO 객체화할 클래스 타입 지정
    // getName > 메소드 명. 자유롭게 설정 가능, 통신에 영향x
    // @Path("post") String post > 매개변수. 매개변수 post가 @Path("post")를 보고 @GET 내부 {post}에 대입

    //고캠핑 기본 정보 조회
    @GET("basedList?")
    Call<GoCampingResult> getGoCampingTotalCount(
            @Query(value = "ServiceKey", encoded = true) String serviceKey,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("_type") String type
    );
    //캠핑장 상세 정보
    @GET("php/DetailCamp.php")
    Call<DbCampingResult> getCampingDetail(
            @Query("_ID") String campid
    );

    //주변 캠핑장
    @GET("locationBasedList?")
    Call<GoCampingResult> getSlideCamping(
            @Query(value = "ServiceKey", encoded = true) String serviceKey,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("mapX") double mapX,
            @Query("mapY") double mapY,
            @Query("radius") int radius,
            @Query("_type") String type
    );

    //신규 캠핑장
    @GET("basedSyncList?")
    Call<GoCampingResult> getSlideCamping(
            @Query(value = "ServiceKey", encoded = true) String serviceKey,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("syncStatus") String syncStatus,
            @Query("syncModTime") int syncModTime,
            @Query("_type") String type
    );



    //인기 캠핑장
    @GET("php/Ranking.php")
    Call<ItemCamping> getRanking(
            @Query("queryNum") int queryNum
    );

    //검색
    @GET("php/keywordSelect.php")
    Call<ItemCamping> getKeyword(
            @Query("setKeyword") String setKeyword,
            @Query("setDoNm") String setDoNm,
            @Query("setSigunguNm") String setSigungu,
            @Query("setKategorie") String setKategorie
    );

    //검색된 캠핑장 기록 저장
    @GET("php/InsertSearchDate.php")
    Call<ItemCamping> getDateInsert(
            @Query("_ID") String campingid
    );

    //지도 탭 gps
    @FormUrlEncoded
    @POST("php/LocationMapTab.php")
    Call<ItemCamping> getGPS(
            @Field("setQuery") int queryNum,
            @Field("mapY") double mapY,
            @Field("mapX") double mapX,
            @Field("setDoNm") String setDoNm,
            @Field("setSigunguNm") String setSigunguNm
    );

    // 캠핑장 이미지 파싱
    @GET("imageList?")
    Call<Result> getImg(
            @Query(value = "ServiceKey", encoded = true) String serviceKey,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("contentId") int contentid,
            @Query("_type") String type
    );

    //DB totalCount 가져오기
    @GET("php/NewDataCheak.php")
    Call<DbCampingResult> getDBcount(
    );

    /*
    * DB 신규 데이터 추가
    * @FormUrlEncoded과 @Field은 같이 써야함
    * */

    @POST("php/NewDataInsert.php")
    Call<DbCampingResult> sendCampList(
            @Body RequestBody requestBody
            );

    @GET("php/DBUpdateCheck.php")
    Call<DbCampingResult> setDBUpdateCheck(
    );

    @GET("basedSyncList?")
    Call<GoCampingResult> getSyncCampList(
            @Query(value = "ServiceKey", encoded = true) String serviceKey,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("syncStatus") String syncStatus,
            @Query("syncModTime") int syncModTime,
            @Query("_type") String type
    );

    @GET("php/UpdateDateInsert.php")
    Call<DbCampingResult> setUpdateDB();

}