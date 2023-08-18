package com.ssteam.nolcam.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ssteam.nolcam.Interface.DialogMsgEvent;
import com.ssteam.nolcam.Interface.FilterMsgEvent;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.SearchResultAdapter;
import com.ssteam.nolcam.databinding.ActivitySearchResultBinding;
import com.ssteam.nolcam.db.SettingSharedPred;
import com.ssteam.nolcam.dialog.FilterDialog;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.ConnectPHP;
import com.ssteam.nolcam.util.PermissionMgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SearchResultActivity extends AppCompatActivity implements ParsingMsgEvent, DialogMsgEvent, FilterMsgEvent, LocationListener {
    ActivitySearchResultBinding binding;

    FilterDialog filterDialog;

    String categoryName;
    int viewPos = 0;
    int categoryPos = 0;
    int sortPos = 0;

    boolean isListEnd;
    boolean isUpdate;

    int currentIndex;

    ArrayList<Camping> originalCampings;

    String[] categoryNames = new String[]{"전체", "일반야영장", "자동차야영장", "글램핑", "카라반"};

    double mapX;
    double mapY;

    String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    PermissionMgr permissionMgr;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);

        // Setting
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        permissionMgr = new PermissionMgr(this);
        permissionMgr.initAppDetailPermission(permissions);
        permissionMgr.initRequestPermission(permissions);
        permissionMgr.setOnPermissionEventListener(new PermissionMgr.permissionEventListener() {
            @Override
            public void onResultMsg(HashMap<String, Boolean> result) {
                if (result.get(permissions[0]).booleanValue()) {
                    requestGps();
                }
            }
        });

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("Keyword");
        String area = intent.getStringExtra("Area");
        String sigungu = intent.getStringExtra("Sigungu");
        mapX = intent.getDoubleExtra("MapX", -1);
        mapY = intent.getDoubleExtra("MapY", -1);
        categoryName = intent.getStringExtra("Category");

        for (int i = 0; i < categoryNames.length; i++) {
            if (categoryNames[i].equals(categoryName)) {
                categoryPos = i;
                break;
            }
        }

        if (mapX != -1) {
            sortPos = 1;
        }

        ConnectPHP connectPHP = new ConnectPHP();
        connectPHP.setEvent((ParsingMsgEvent) this, new ResultMsg());
        connectPHP.setSearchForm(keyword, area, sigungu, categoryName);
        connectPHP.createCall(3);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.filterBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog = new FilterDialog(SearchResultActivity.this, (DialogMsgEvent) SearchResultActivity.this, (FilterMsgEvent) SearchResultActivity.this);
                filterDialog.setViewPos(viewPos);
                filterDialog.setCategoryPos(categoryPos);
                filterDialog.setSortPos(sortPos);
                filterDialog.setOnItemSettingListener(new FilterDialog.onItemSettingListener() {
                    @Override
                    public void OnItem(int viewPos, int categoryPos, int sortPos, String categoryName) {
                        filterDialog.dismiss();

                        SearchResultActivity.this.viewPos = viewPos;
                        SearchResultActivity.this.categoryPos = categoryPos;
                        SearchResultActivity.this.sortPos = sortPos;

                        if (sortPos == 1) {
                            if (mapX == -1) {
                                Toast.makeText(SearchResultActivity.this, "위치정보가 아직 수신되지 않았습니다. 기본 정렬로 목록을 제공합니다.", Toast.LENGTH_SHORT).show();
                                SearchResultActivity.this.sortPos = 0;
                            }
                        }
                        setSearchList(getFilterArray(categoryName, SearchResultActivity.this.sortPos), viewPos);
                    }
                });
                filterDialog.show();
            }
        });
    }

    public void requestGps() {
        if (permissionMgr.check(permissions[0])) { // 권한 체크
            // GPS, NetWork GPS 사용 가능 여부)
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // 켜져있으면 listener 실행 후 좌표값 캐치 하면 GPS값 수신시작
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
                binding.gpsLoadingBox.setVisibility(View.VISIBLE);
                Toast.makeText(this, "GPS 정보를 수신중 입니다.\n건물, 지하에서는 수신이 지연 되거나 안될 수 있습니다.", Toast.LENGTH_SHORT).show();
            } else { // 센서 사용설정 후 다시 시도 하라는 메세지를 보여준다.
                Toast.makeText(this, "GPS(위치 서비스)를 사용 설정 후 다시 시도 하세요.", Toast.LENGTH_SHORT).show();
            }
        } else {
            permissionMgr.requestPermission();
        }
    }

    public ArrayList<Camping> getFilterArray(String categoryName, int sortPos) {
        ArrayList<Camping> sortArray = new ArrayList<>();

        // 데이터 복사
        // 밑에서 사용한 스왑이 데이터를 유지시키는듯한 문제가 있는거같아서
        // 거리값 초기화
        for(int i = 0; i < originalCampings.size(); i++) {
            sortArray.add(originalCampings.get(i));
            sortArray.get(i).setDistance(0);
        }

        if (sortPos == 1) {
            // 거리별 계산값 추가하기
            if (mapX != -1) {
                for (int i = 0; i < sortArray.size(); i++) {
                    double distance = getDistance(mapX, mapY, sortArray.get(i).mapY, sortArray.get(i).mapX, "kilometer");
                    sortArray.get(i).setDistance(distance);
                }

                // 거리별로 정렬
                for (int i = 0; i < sortArray.size() - 1; i++) {
                    boolean change = false;
                    for (int j = 0; j < sortArray.size() - 1 - i; j++) {
                        if (sortArray.get(j).distance >= sortArray.get(j + 1).distance) {
                            change = true;
                            Collections.swap(sortArray, j, j + 1);
                        }
                    }
                    if (change == false) break;
                }
            }
        }

        ArrayList<Camping> returnArray = new ArrayList<>();
        for (int i = 0; i < sortArray.size(); i++) {
            if (categoryName.equals("전체")) {
                returnArray.add(sortArray.get(i));
            } else {
                if (sortArray.get(i).induty.contains(categoryName)) {
                    returnArray.add(sortArray.get(i));
                }
            }
        }

        return returnArray;
    }

    public void setSearchList(ArrayList<Camping> arrayList, int view) {
        if (arrayList.size() == 0) {
            binding.searchResultText.setText("검색결과가 없습니다.");

            binding.searchResultList.setVisibility(View.GONE);
            binding.searchResultNoCountText.setVisibility(View.VISIBLE);
        } else {
            binding.searchResultList.setVisibility(View.VISIBLE);
            binding.searchResultNoCountText.setVisibility(View.GONE);

            int viewLayoutId = R.layout.item_search_result1;
            if (view == 1) {
                viewLayoutId = R.layout.item_search_result2;
            }
            binding.searchResultText.setText("검색결과 (" + arrayList.size() + ") 중 0개 표시중");

            SearchResultAdapter adapter;

            if (arrayList.size() <= 20) {
                adapter = new SearchResultAdapter(this, viewLayoutId, arrayList);
                binding.searchResultList.setAdapter(adapter);

                binding.searchResultText.setText("검색결과 (" + arrayList.size() + ") 중 " + arrayList.size() + "개 표시중");
            } else {
                currentIndex = 0;
                int lastIndex = arrayList.size();

                ArrayList<Camping> campings = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    campings.add(arrayList.get(currentIndex++));
                }

                adapter = new SearchResultAdapter(this, viewLayoutId, campings);
                binding.searchResultList.setAdapter(adapter);
                binding.searchResultText.setText("검색결과 (" + arrayList.size() + ") 중 " + currentIndex + "개 표시중");

                binding.searchResultList.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {
                        if (i == 0 && isListEnd && !isUpdate) {
                            isUpdate = true;
                            isListEnd = false;

                            int count = 20;
                            if ((lastIndex - currentIndex) < 20) {
                                count = (lastIndex - currentIndex);
                            }

                            for (int j = 0; j < count; j++) {
                                adapter.addCamping(arrayList.get(currentIndex++));
                            }

                            Timer timer = new Timer();
                            TimerTask tt = new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // ui 적용
                                            binding.searchResultText.setText("검색결과 (" + arrayList.size() + ") 중 " + currentIndex + "개 표시중");
                                            adapter.notifyDataSetChanged();

                                            binding.listLoading.setVisibility(View.GONE);
                                        }
                                    });

                                    isUpdate = false;
                                }
                            };

                            timer.schedule(tt, 250);
                        }
                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                        if ((i + i1) == i2) {
                            isListEnd = true;
                        }
                    }
                });
            }

            binding.searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(SearchResultActivity.this, CampingDetailActivity.class);
                    intent.putExtra("CampingID", arrayList.get(i).id);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        originalCampings = (ArrayList<Camping>) resultMsg.getArrayList();

        setSearchList(getFilterArray("전체", sortPos), viewPos);
    }

    @Override
    public void onResult(String value, ArrayList values) {
    }

    @Override
    public void filterMsg(int msg) {
        if (!isGpsActive) {
            requestGps();
        }
    }

    boolean isGpsActive = false;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mapX = location.getLatitude();
        mapY = location.getLongitude();

        // gps값이 들어오면 검색리스트 수정
        if (!isGpsActive) {
            binding.gpsLoadingBox.setVisibility(View.GONE);
            sortPos = 1;
            setSearchList(getFilterArray(categoryNames[categoryPos], sortPos), viewPos);

            isGpsActive = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isGpsActive) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
        }
    }

    @Override
    protected void onStop() {
        if (isGpsActive) {
            locationManager.removeUpdates(this);
        }

        super.onStop();
    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if (unit == "meter") {
            dist = dist * 1609.344;
        }

        return (dist);
    }
}