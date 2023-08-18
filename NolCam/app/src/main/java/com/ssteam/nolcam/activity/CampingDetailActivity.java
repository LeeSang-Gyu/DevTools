package com.ssteam.nolcam.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.CampingSlideAdpater;
import com.ssteam.nolcam.adapter.FacilityAdapter;
import com.ssteam.nolcam.adapter.ImageSlideAdpater;
import com.ssteam.nolcam.adapter.IndicatorAdapter;
import com.ssteam.nolcam.databinding.ActivityCampingDetailBinding;
import com.ssteam.nolcam.db.FavoritesCampingDBHelper;
import com.ssteam.nolcam.db.RecentlyCampingDBHelper;
import com.ssteam.nolcam.dialog.CampingReserveDialog;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.generic.campingimg.CampingImg;
import com.ssteam.nolcam.util.CalendarEngine;
import com.ssteam.nolcam.util.ConnectPHP;
import com.ssteam.nolcam.viewmodel.CampingDetailViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class CampingDetailActivity extends AppCompatActivity implements ParsingMsgEvent, MapView.MapViewEventListener {
    ActivityCampingDetailBinding binding;
    CampingDetailViewModel viewModel;

    FavoritesCampingDBHelper favoritesCampingDBHelper;

    String campingID;
    String isRecently;

    Camping camping;

    boolean isIntroExapnd;

    ViewGroup mapViewContainer;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camping_detail);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(CampingDetailViewModel.class);
        binding.setViewModel(viewModel);

        favoritesCampingDBHelper = new FavoritesCampingDBHelper(this);

        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);

        Intent intent = getIntent();
        campingID = intent.getStringExtra("CampingID");
        isRecently = intent.getStringExtra("Recently");

        // 파싱
        ConnectPHP insertConnect = new ConnectPHP();
        insertConnect.setEvent(this, new ResultMsg("CampingDetail"));
        insertConnect.setCampingID(campingID);
        insertConnect.createCall(6);

        ConnectPHP detailConnect = new ConnectPHP();
        detailConnect.setEvent(this, new ResultMsg("CampingDetail"));
        detailConnect.setCampingID(campingID);
        detailConnect.createCall(2);

        binding.backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public View.OnClickListener favoritesListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            boolean isExist = favoritesCampingDBHelper.getFavritesExist(campingID);
            if (isExist) {
                binding.favoritesBtn1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                binding.favoritesBtn2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                camping.favorites = 0;
                favoritesCampingDBHelper.delete(campingID);
            } else {
                binding.favoritesBtn1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));
                binding.favoritesBtn2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));

                camping.favorites = 1;
                favoritesCampingDBHelper.insert(camping);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        if (resultMsg.getType().equals("CampingDetail")) {
            ArrayList<Camping> campings = (ArrayList<Camping>) resultMsg.getArrayList();
            camping = campings.get(0);

            boolean isExist = favoritesCampingDBHelper.getFavritesExist(campingID);
            if (isExist) {
                binding.favoritesBtn1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));
                binding.favoritesBtn2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));


                camping.favorites = 1;
            } else {
                binding.favoritesBtn1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                binding.favoritesBtn2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                camping.favorites = 0;
            }

            binding.favoritesBtn1.setOnClickListener(favoritesListener);
            binding.favoritesBtn2.setOnClickListener(favoritesListener);

            // 최근 본 캠핑장에 등록하기
            if (isRecently == null) {
                RecentlyCampingDBHelper recentlyCampingDBHelper = new RecentlyCampingDBHelper(this);
                recentlyCampingDBHelper.insert(camping);
            }

            // 데이터 세팅하기
            // 이미지
            ArrayList<String> images = new ArrayList<>();
            images.add(camping.firstImageURL);
            viewModel.firstImageURLs.setValue(images);

            ImageSlideAdpater slideAdpater = new ImageSlideAdpater(this, viewModel.firstImageURLs.getValue(), R.layout.item_image_slide2);
            binding.detailCampingContainer.setAdapter(slideAdpater);

            viewModel.facltNm.setValue(camping.facltNm);
            viewModel.indutyAndLctCl.setValue(getInduty(camping.induty) + " " + getInduty(camping.lctCl));
            viewModel.createdtime.setValue("등록일 " + camping.createdtime);
            if (camping.intro.equals("")) {
                binding.lineIntroText.setVisibility(View.GONE);
            } else {
                viewModel.lineIntro.setValue(camping.lineIntro);
            }
            if (camping.intro.equals("")) {
                viewModel.intro.setValue("소개정보가 입력되지 않았습니다.");

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 20, 10, 10);
                binding.introText.setLayoutParams(lp);

                binding.introExpandBtn.setVisibility(View.GONE);
            } else {
                ArrayList<String> introSplit = getIntro(camping.intro);
                String value = "";
                if (introSplit.size() > 0) {
                    for (int i = 0; i < introSplit.size(); i++) {
                        value += introSplit.get(i) + "\n" + "\n";
                    }
                }
                viewModel.intro.setValue(value);

                ViewGroup.LayoutParams introParams = binding.introText.getLayoutParams();
                binding.introExpandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isIntroExapnd) {
                            binding.introText.setLayoutParams(introParams);
                            isIntroExapnd = false;

                            binding.introExpandBtn.setText("캠핑장 소개 더보기");
                        } else {
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(0, 20, 0, 0);
                            binding.introText.setLayoutParams(lp);
                            isIntroExapnd = true;

                            binding.introExpandBtn.setText("캠핑장 소개 접기");
                        }
                    }
                });
            }
            viewModel.operDeCl.setValue(camping.operDeCl);
            viewModel.operPdCl.setValue(camping.operPdCl);
            viewModel.addr1.setValue(camping.addr1);
            viewModel.addr2.setValue(camping.addr2);
            viewModel.tel.setValue(camping.tel);
            viewModel.homepage.setValue(camping.homepage);
            viewModel.caravAcmpnyAt.setValue(getAcmpnyAt(camping.caravAcmpnyAt));
            viewModel.trlerAcmpnyAt.setValue(getAcmpnyAt(camping.trlerAcmpnyAt));

            if (camping.extshrCo == 0 && camping.frprvtWrppCo == 0 && camping.frprvtSandCo == 0 && camping.fireSensorCo == 0) {
                viewModel.firefightingFacilities.setValue("화재관리 시설 없음");
            } else {
                viewModel.firefightingFacilities.setValue(getFirefightingFacility(camping.extshrCo, camping.frprvtWrppCo, camping.frprvtSandCo, camping.fireSensorCo));
            }

            if (camping.sbrsCl.equals("")) {
                binding.convenienceFacilityBox.setVisibility(View.GONE);
            } else {
                String[] convenienceFacilitys = getFacilityList(camping.sbrsCl);
                FacilityAdapter convenienceFacilityAdapter = new FacilityAdapter(this, getFacilityList(camping.sbrsCl));
                binding.convenienceFacilityList.setLayoutParams(getViewSizeParams(binding.convenienceFacilityList, convenienceFacilitys.length));
                binding.convenienceFacilityList.setAdapter(convenienceFacilityAdapter);
            }

            if (camping.glampInnerFclty.equals("")) {
                binding.glampingFacilityBox.setVisibility(View.GONE);
            } else {
                String[] glampingFacilitys = getFacilityList(camping.glampInnerFclty);
                FacilityAdapter glampingFacilityAdapter = new FacilityAdapter(this, glampingFacilitys);
                binding.glampingFacilityList.setLayoutParams(getViewSizeParams(binding.glampingFacilityList, glampingFacilitys.length));
                binding.glampingFacilityList.setAdapter(glampingFacilityAdapter);
            }

            if (camping.caravInnerFclty.equals("")) {
                binding.caravanFacilityBox.setVisibility(View.GONE);
            } else {
                String[] caravFacilitys = getFacilityList(camping.caravInnerFclty);
                FacilityAdapter caravFacilityAdapter = new FacilityAdapter(this, caravFacilitys);
                binding.caravanFacilityList.setLayoutParams(getViewSizeParams(binding.caravanFacilityList, caravFacilitys.length));
                binding.caravanFacilityList.setAdapter(caravFacilityAdapter);
            }
            setMapView(camping.facltNm, camping.mapX, camping.mapY);

            binding.campingReserveCheckBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (camping.tel.equals("") && camping.homepage.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CampingDetailActivity.this)
                                .setTitle("예약정보 확인")
                                .setMessage("해당 캠핑장의 예약정보를 확인할 수 없습니다.\n캠핑장에서 등록하지 않았거나 일시적인 오류 입니다.")
                                .setPositiveButton("확인", null);
                        builder.create().show();
                    } else {
                        CampingReserveDialog campingReserveDialog = new CampingReserveDialog(CampingDetailActivity.this, camping.tel, camping.homepage);
                        campingReserveDialog.show();
                    }
                }
            });

            //top_bar_box
            binding.campingScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > 1020) {
                        binding.topBarBox.setVisibility(View.VISIBLE);
                    } else {
                        binding.topBarBox.setVisibility(View.GONE);
                    }
                }
            });


            // 기타
            binding.campingUpdateRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] address = {"gfs6307@gmail.com"}; // 임시
                    intent.putExtra(Intent.EXTRA_EMAIL, address);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "캠핑장 정보수정 요청");
                    intent.putExtra(Intent.EXTRA_TEXT, "문의내용\n\n캠핑장 이름 : " + camping.facltNm + "\n\n내용 : ");
                    intent.setType("message/rfc822");

                    startActivity(Intent.createChooser(intent, "어떤 앱을 이용해서 문의 하시겠습니까?"));
                }
            });

            binding.topMoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.defaultMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.topMoveBtn.setTextColor(getResources().getColor(R.color.main_color));
                    binding.facilityMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.mapMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.introMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.updateMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));

                    scrollToView(binding.backBtn1);
//                    moveScrollView(binding.backBtn1);
                }
            });
            binding.defaultMoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.defaultMoveBtn.setTextColor(getResources().getColor(R.color.main_color));
                    binding.topMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.facilityMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.mapMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.introMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.updateMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));

                    scrollToView(binding.defaultCoordinate);
//                    moveScrollView(binding.defaultCoordinate);
                }
            });
            binding.introMoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.defaultMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.topMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.facilityMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.mapMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.introMoveBtn.setTextColor(getResources().getColor(R.color.main_color));
                    binding.updateMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));

                    scrollToView(binding.introCoordinate);
//                    moveScrollView(binding.introCoordinate);
                }
            });
            binding.facilityMoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.defaultMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.topMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.facilityMoveBtn.setTextColor(getResources().getColor(R.color.main_color));
                    binding.mapMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.introMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.updateMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));

                    scrollToView(binding.facilityCoordinate);
//                    moveScrollView(binding.facilityCoordinate);
                }
            });
            binding.mapMoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.defaultMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.topMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.facilityMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.mapMoveBtn.setTextColor(getResources().getColor(R.color.main_color));
                    binding.introMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.updateMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));

                    scrollToView(binding.mapCoordinate);
//                    moveScrollView(binding.mapCoordinate);
                }
            });

            binding.updateMoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.defaultMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.topMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.facilityMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.mapMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.introMoveBtn.setTextColor(getResources().getColor(R.color.text_color2));
                    binding.updateMoveBtn.setTextColor(getResources().getColor(R.color.main_color));

                    scrollToView(binding.updateCoordinate);
//                    moveScrollView(binding.updateCoordinate);
                }
            });

            binding.mapIntent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri location = Uri.parse("geo:" + camping.mapY + ", " + camping.mapX + "?z=14"); //map point based on latitude/longitude. z param is zoom level
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
                    boolean isIntentSafe = activities.size() > 0;

                    if (isIntentSafe) {
                        startActivity(mapIntent);
                    }
                }
            });


            ConnectPHP imgConnect = new ConnectPHP();
            imgConnect.setEvent(this, new ResultMsg("CampingImages"));
            imgConnect.setContentId(Integer.valueOf(camping.contentId));
            imgConnect.createCall(5);
        } else if (resultMsg.getType().equals("CampingImages")) {
            ArrayList<CampingImg> campingImgs = (ArrayList<CampingImg>) resultMsg.getArrayList();
            if (campingImgs.size() != 0) {
                int imageCount = 0;
                if (campingImgs.size() > 1) {
                    imageCount = 1;
                }
                for (int i = imageCount; i < campingImgs.size(); i++) {
                    viewModel.firstImageURLs.getValue().add(campingImgs.get(i).getImageUrl());
                }

                if (viewModel.firstImageURLs.getValue().size() != 1) {
                    ImageSlideAdpater slideAdpater = new ImageSlideAdpater(this, viewModel.firstImageURLs.getValue(), R.layout.item_image_slide2);
                    binding.detailCampingContainer.setAdapter(slideAdpater);
                    if (viewModel.firstImageURLs.getValue().size() > 1) {
                        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(this, viewModel.firstImageURLs.getValue().size());
                        indicatorAdapter.setSize((indicatorAdapter.getWidth() / 2), (indicatorAdapter.getHeight() / 2));
                        indicatorAdapter.setChoiceColor("#FFFFFF");
                        indicatorAdapter.setIndicator(binding.detailCampingIndicator);
                        indicatorAdapter.setupWithViewPager(binding.detailCampingContainer);
                    }
                }
            }
        }
    }

    public static void scrollToView(View view, final NestedScrollView scrollView, int count) {
        if (view != null && view != scrollView) {
            count += view.getTop();
            scrollToView((View) view.getParent(), scrollView, count);
        } else if (scrollView != null) {
            int finalCount = count;
            new Handler().postDelayed(new Runnable() {
                                          @Override
                                          public void run() {

                                              scrollView.smoothScrollTo(0, finalCount);
                                          }
                                      },
                    200);
        }
    }

    public int computeDistanceToView(View view) {
        return Math.abs(calculateRectOnScreen(binding.campingScrollView).top - (binding.campingScrollView.getScrollY() + calculateRectOnScreen(view).top));
    }

    public Rect calculateRectOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Rect(
                location[0],
                location[1],
                location[0] + view.getMeasuredWidth(),
                location[1] + view.getMeasuredHeight()
        );
    }

    public void scrollToView(View view) {
        int y = computeDistanceToView(view);
        binding.campingScrollView.scrollTo(0, (y - binding.topBarBox.getHeight()));
    }

    public void moveScrollView(View view) {
        binding.campingScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                Point p = new Point();
                binding.campingScrollView.getGlobalVisibleRect(rect, p);

                int a[] = new int[2];
                view.getLocationInWindow(a);

                binding.campingScrollView.smoothScrollBy(0, a[1] - p.y);
            }
        }, 400);
    }

    public String getInduty(String value) {
        String induty = "";
        try {
            String[] indutys = value.split(",");

            for (int i = 0; i < indutys.length; i++) {
                induty += "#" + indutys[i] + " ";
            }

            return induty;
        }catch (Exception e){
            Log.v("induty 확인:", induty);
            return induty;
        }
    }

    private ArrayList<String> getIntro(String sbr) {
        String[] value;
        String[] check;
        int size = 0;
        ArrayList<String> arrayList = new ArrayList<>();

        sbr = sbr.trim();
        value = sbr.split("[.]");

        for (int i = 0; i < value.length; i++) {
            int count = 0;
            value[i] = value[i].trim();
            check = value[i].split("[\\s]");
            size = check.length;

            String text = "";
            for (int y = 0; y < check.length; y++) {
                count++;
                check[y] = check[y].replace("\\s", "");

                if (count < size) {
                    text += check[y] + " ";
                } else if (count == size) {
                    text += check[y] + ".";
                }
            }
            arrayList.add(text);
        }

        return arrayList;
    }

    public String getAcmpnyAt(String value) {
        String possible = "동반 불가능";
        if (value.equals("Y")) {
            possible = "동반 가능";
        }

        return possible;
    }

    public String getFirefightingFacility(int a, int b, int c, int d) {
        String facilities = "화재관리시설 있음\n(소화기 " + a + " 방화수 " + b + " 방화사 " + c + " 화재감지기 " + d + ")";

        return facilities;
    }

    public String[] getFacilityList(String value) {
        String[] facilitys = value.split(",");

        if (value.contains("운동장")) {
            ArrayList<String> tempArray = new ArrayList<>();

            String[] tempStrs = value.split(",");
            for (int i = 0; i < tempStrs.length; i++) {
                if (tempStrs[i].equals("운동장")) {
                    continue;
                }
                tempArray.add(tempStrs[i]);
            }

            facilitys = tempArray.toArray(new String[tempArray.size()]);
        }

        return facilitys;
    }

    // girdview의 아이템 크기에따라 동적으로 height 크기를 변경하기 위해 사용
    public ViewGroup.LayoutParams getViewSizeParams(View view, int arrayLength) {
        int height = 100;
        int count = 1;
        if (arrayLength > 5) {
            count = 2;
        }
        if (arrayLength > 10) {
            count = 3;
        }
        int sumheight = (height * count);

        ViewGroup.LayoutParams params = view.getLayoutParams();
        int paramsHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sumheight, getResources().getDisplayMetrics());
        params.height = paramsHeight;

        return params;
    }

    private void setMapView(String campingName, double mapX, double mapY) {
        try {
            if (mapView == null) {
                mapView = new MapView(this);
                mapViewContainer.addView(mapView);
            }
            // 중심점 변경
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.valueOf(mapY), Double.valueOf(mapX));
            mapView.setMapCenterPoint(mapPoint, true);

            // 마커 찍기
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(campingName);
            marker.setTag(0);
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(marker);

            mapView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

        } catch (UnsatisfiedLinkError error) {
            error.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        binding.campingScrollView.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapViewContainer.removeView(mapView);
            mapView = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapViewContainer.removeView(mapView);
            mapView = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) {
            mapViewContainer.removeView(mapView);
            mapView = null;
        }
    }

    @Override
    public void finish() {
        if (mapView != null) {
            mapViewContainer.removeView(mapView);
            mapView = null;
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapViewContainer.removeView(mapView);
            mapView = null;
        }

        super.onDestroy();
    }
}