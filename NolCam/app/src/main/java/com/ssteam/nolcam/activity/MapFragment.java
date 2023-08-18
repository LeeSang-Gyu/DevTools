/*
 * 수정일 : 2023-02-28
 * 용도 : 지도 탭
 * 특이사항 : 맵뷰는 1개만 존재해야한다 (검은색 화면 : 맵뷰가 2개 이상 존재하는 경우 생기는 현상, 회색 화면 : 맵뷰가 없어서 생기는 현상)
 * */
package com.ssteam.nolcam.activity;

import android.Manifest;
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

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.Interface.ActivityEvent;
import com.ssteam.nolcam.Interface.DialogMsgEvent2;
import com.ssteam.nolcam.Interface.MapReSetting;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.databinding.FragmentMapBinding;
import com.ssteam.nolcam.db.SettingSharedPred;
import com.ssteam.nolcam.dialog.MapAreaDialog;
import com.ssteam.nolcam.dialog.MapSlideDialog;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.MapSlideMsg;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.ConnectPHP;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.Map;

public class MapFragment extends Fragment implements DialogMsgEvent2, ParsingMsgEvent, MapView.POIItemEventListener, MapReSetting, ActivityEvent {
    private FragmentMapBinding binding;
    private ViewGroup mapViewContainer;
    private MapView mapView;
    private String campingID;

    private LocationManager locationManager;
    private SettingSharedPred settingSharedPred;


    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private int sensorCount;
    private double currentMapX;
    private double currentMapY;

    private String searchType;
    private ArrayList<Camping> campings;

    private ResultMsg resultMsg;
    private MapSlideMsg mapSlideMsg;

    private String color = "";

    private ArrayList<Camping> slideArray;
    private String slideResult = "";
    private MapAreaDialog areaChoiceDialog;
    private MapSlideDialog mapSlideDialog;


    private int setPanelHeight = 0;
    private int count = 0;

    private int originHeight = 0;

    private boolean isMap;


    public void setBtnColor(String color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);


    }

    View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);

        this.view = view;
        // Setting
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        settingSharedPred = new SettingSharedPred(requireActivity());

        mapViewContainer = binding.aroundMapView;

        mapSlideDialog = new MapSlideDialog(requireActivity());

        initMap();


        ActivityResultLauncher<Intent> detailPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // 설정창에서 권한을 허용하고 온 경우
                if (ContextCompat.checkSelfPermission(requireActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) { // 허용된 경우
                    locationSensorReception();
                }
            }
        });

        ActivityResultLauncher<String[]> permissionLauncher;
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(permissions[0]).booleanValue()) {
                    // 권한 다이얼로그에서 허용 했으면 위치센서 작동
                    locationSensorReception();
                } else { // 권한 허용을 하지 않았으면
                    settingSharedPred.setGpsPermissionValue(true);

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                            .setTitle("권한 설정 안내")
                            .setMessage("위치 권한을 허용하지 않았습니다.\n\n권한을 허용하시려면\n설정 > 어플리케이션 관리 > 놀캠")
                            .setNegativeButton("설정하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + requireActivity().getPackageName()));
                                    startActivity(appDetail);
                                    detailPermissionLauncher.launch(appDetail);
                                }
                            })
                            .setPositiveButton("확인", null);
                    builder.create().show();
                }
            }
        });


        // 내 위치 버튼
        binding.myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(requireActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) { // 허용된 경우
                    locationSensorReception();
                } else if (ContextCompat.checkSelfPermission(requireActivity(), permissions[0]) == PackageManager.PERMISSION_DENIED) {
                    if (!settingSharedPred.getGpsPermissionValue()) {
                        permissionLauncher.launch(permissions);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                                .setTitle("권한 설정 안내")
                                .setMessage("위치 권한을 허용하지 않았습니다.\n\n권한을 허용하시려면\n설정 > 어플리케이션 관리 > 놀캠")
                                .setNegativeButton("설정하기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + requireActivity().getPackageName()));
                                        startActivity(appDetail);
                                        detailPermissionLauncher.launch(appDetail);
                                    }
                                })
                                .setPositiveButton("확인", null);
                        builder.create().show();
                    }
                }
            }
        });

        setMapView("126.980832", "37.566222", null);

        //지역설정 버튼
        binding.choiceMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapSlideDialog.setMapFragment(MapFragment.this);
                mapSlideDialog.setDialogShow();
                mapSlideDialog.setConveyResult((MapReSetting) MapFragment.this);
            }
        });


        //마커 아이템 닫기 버튼
        binding.campingItemViewCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.campingItemViewBox.setVisibility(View.GONE);
                mapView.setZoomLevel(7, true);
            }
        });


        //버튼 더 보기 버튼
        binding.zoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mapBtns.getVisibility() == View.GONE) {
                    binding.mapBtns.setVisibility(View.VISIBLE);
                } else {
                    binding.mapBtns.setVisibility(View.GONE);
                }
            }
        });

        //마커 아이템 크기 변경 버튼
        binding.campingItemViewBoxChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.campingItemViewDataBox1.getVisibility() == View.VISIBLE) {
                    binding.campingItemViewDataBox1.setVisibility(View.GONE);
                    binding.campingItemViewDataBox2.setVisibility(View.VISIBLE);
                    binding.campingItemViewBoxChangeBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);


                } else {
                    binding.campingItemViewDataBox1.setVisibility(View.VISIBLE);
                    binding.campingItemViewBoxChangeBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    binding.campingItemViewDataBox2.setVisibility(View.GONE);
                }
            }
        });

        //마커 아이템 자세히보기 버튼
        binding.campingItemDetailMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CampingDetailActivity.class);
                intent.putExtra("CampingID", campingID);
                startActivity(intent);
            }
        });

        //검색버튼
        binding.searchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //우마노 대신 사용할 커스텀 다이얼로그
                mapSlideDialog = new MapSlideDialog(requireActivity(), (ParsingMsgEvent) MapFragment.this, MapFragment.this);
                mapSlideDialog.setEvent(0);
                mapSlideDialog.setTypeMsg("search");
                mapSlideDialog.show();
            }
        });


        binding.campingListBtn  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (campings != null) {
                    mapSlideDialog = new MapSlideDialog(requireActivity());
                    mapSlideDialog.setMapFragment(MapFragment.this);
                    mapSlideDialog.setResult(campings);
                    mapSlideDialog.setTypeMsg("result");
                    mapSlideDialog.show();
                } else {
                    Toast.makeText(requireActivity(), "저장되어 있는 정보가 없습니다, 먼저 검색을 진행 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void locationSensorReception() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) { // GPS, NetWork GPS 사용 가능 여부) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Toast.makeText(requireActivity(), "GPS 정보를 수신중입니다.\n건물, 지하에서는 수신이 지연되거나 안될 수 있습니다.", Toast.LENGTH_SHORT).show();
            sensorCount = 0;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        } else {
            Toast.makeText(requireActivity(), "GPS(위치 서비스)를 사용 설정 후 다시 시도하세요.", Toast.LENGTH_SHORT).show();
        }
    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double mapY = location.getLatitude();
            double mapX = location.getLongitude();
            currentMapY = mapY;
            currentMapX = mapX;
            sensorCount++;


            if (sensorCount >= 2) { // 처음 받는 값은 튀는값으로 넓은 범위의 좌표를 받을 수 있기때문에 버린다
                Toast.makeText(getContext(), "현재 위치정보 수신이 완료되었습니다." + "\n" + "검색을 시작합니다.", Toast.LENGTH_SHORT).show();
                // 값은 한번만 받으면 되기때문에 위치값 수신을 정지한다.
                locationManager.removeUpdates(locationListener);

                ConnectPHP gpsConnect = new ConnectPHP();
                gpsConnect.setEvent((ParsingMsgEvent) MapFragment.this, new ResultMsg("MyGps"));
                gpsConnect.setMapTab(1, mapX, mapY, "", "");
                gpsConnect.createCall(7);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void setMapView(String mapX, String mapY, String cityName) {

        try {
            MapPOIItem[] mapPOIItems = mapView.getPOIItems();
            for (int i = 0; i < mapPOIItems.length; i++) {
                mapView.removePOIItem(mapPOIItems[i]);
            }

            //지역설정
            if (campings.size() == 0) {

                // 마커 찍기
                MapPoint point = MapPoint.mapPointWithGeoCoord(Double.valueOf(mapY), Double.valueOf(mapX));
                mapView.setMapCenterPoint(point, true);
                mapView.setZoomLevel(7, true);

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(cityName);
                marker.setTag(9508);
                marker.setMapPoint(point);
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.

                mapView.addPOIItem(marker);
            } else {
                ArrayList<MapPOIItem> markerArr = new ArrayList<MapPOIItem>();
                Log.v("테스트", "테스트");
                // 마커 찍기
                for (int i = 0; i < resultMsg.getCampList().size(); i++) {
                    MapPoint point = MapPoint.mapPointWithGeoCoord(resultMsg.getCampList().get(i).mapY, resultMsg.getCampList().get(i).mapX);
                    mapView.setMapCenterPoint(point, true);
                    mapView.setZoomLevel(7, true);

                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName(resultMsg.getCampList().get(i).facltNm);
                    marker.setTag(i);
                    marker.setMapPoint(point);
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setCustomImageResourceId(R.drawable.marker); // 마커 이미지.
                    marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
//                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    markerArr.add(marker);
                }
                mapView.addPOIItems(markerArr.toArray(new MapPOIItem[markerArr.size()]));;
            }
            mapView.setPOIItemEventListener(this);
            mapViewContainer.addView(mapView);

        } catch (UnsatisfiedLinkError error) {
            error.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setMapView() {

        try {

            // 기존에 로드된 마커 삭제 수행
            MapPOIItem[] mapPOIItems = mapView.getPOIItems();
            for (int i = 0; i < mapPOIItems.length; i++) {
                mapView.removePOIItem(mapPOIItems[i]);
            }

            //내위치
            if (searchType.equals("MyGps")) {

                for (int i = 0; i < campings.size() + 1; i++) {
                    MapPOIItem marker;
                    if (i == 0) {
                        MapPoint point = MapPoint.mapPointWithGeoCoord(currentMapY, currentMapX);
                        mapView.setMapCenterPoint(point, true);
                        mapView.setZoomLevel(7, true);
                        mapView.setPOIItemEventListener(this);

                        marker = new MapPOIItem();
                        marker.setItemName("내 위치");
                        marker.setTag(9508);
                        marker.setMapPoint(point);
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.

                    } else {
                        Log.v("테스트/셋맵뷰/forX", String.valueOf(i));
                        MapPoint point = MapPoint.mapPointWithGeoCoord(campings.get(i).mapY, campings.get(i).mapX);
                        mapView.setMapCenterPoint(point, true);
                        mapView.setZoomLevel(7, true);
                        mapView.setPOIItemEventListener(this);

                        marker = new MapPOIItem();
                        marker.setItemName(campings.get(i-1).getFacltNm());
                        marker.setTag(i-1);
                        marker.setMapPoint(point);
                        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                        marker.setCustomImageResourceId(R.drawable.marker); // 마커 이미지.
                        marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

                    }
                    mapView.addPOIItem(marker);
                }

                binding.mapText.setVisibility(View.GONE);
                mapViewContainer.addView(mapView);

            } else {
                ArrayList<MapPOIItem> markerArr = new ArrayList<MapPOIItem>();

                // 마커 찍기
                for (int i = 0; i < campings.size(); i++) {
                    MapPoint point = MapPoint.mapPointWithGeoCoord(campings.get(i).mapY, campings.get(i).mapX);
                    mapView.setMapCenterPoint(point, true);
                    mapView.setZoomLevel(7, true);

                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName(resultMsg.getCampList().get(i).facltNm);
                    marker.setTag(i);
                    marker.setMapPoint(point);
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    marker.setCustomImageResourceId(R.drawable.marker); // 마커 이미지.
                    marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                    markerArr.add(marker);
                }
                mapView.addPOIItems(markerArr.toArray(new MapPOIItem[markerArr.size()]));
                mapView.setPOIItemEventListener(this);
                mapViewContainer.addView(mapView);
            }

        } catch (UnsatisfiedLinkError error) {
            error.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        searchType = resultMsg.getType();
        campings = (ArrayList<Camping>) resultMsg.getArrayList();
        this.resultMsg = resultMsg;

        if (resultMsg.getType().equals("MyGps")) {
            MapSlideDialog gpsDialog = new MapSlideDialog(requireActivity());
            gpsDialog.setMapFragment(MapFragment.this);
            gpsDialog.setResult(campings);
            gpsDialog.setTypeMsg("MyGps");
            gpsDialog.show();
        }
        setMapView();

    }

    @Override
    public void onResult(String area, String city) {
//        binding.searchMap.setText(area + " > " + city);

        Toast.makeText(requireActivity(), "선택한 지역으로 검색을 시작합니다.", Toast.LENGTH_SHORT).show();

        ConnectPHP connectPHP = new ConnectPHP();
        connectPHP.setEvent((ParsingMsgEvent) MapFragment.this, new ResultMsg("AreaMap"));  //첫번째 인자는 클래스가 들어가야함.
        connectPHP.setMapTab(2, 1, 1, area, city);
        connectPHP.createCall(7);
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        if (mapPOIItem.getTag() == 9508) {
            return;
        }

        Camping camping = campings.get(mapPOIItem.getTag());
        campingID = camping.id;

        MapPoint point = MapPoint.mapPointWithGeoCoord(camping.getMapY(), (camping.getMapX()));
        mapView.setMapCenterPoint(point, true);
        mapView.setZoomLevel(2, true);

        binding.campingItemViewBox.setVisibility(View.VISIBLE);


        if (camping.firstImageURL.equals("")) {
            Glide.with(requireActivity()).load(R.drawable.noimg).thumbnail(0.1f).into(binding.campingDetailImg1);
            Glide.with(requireActivity()).load(R.drawable.noimg).thumbnail(0.1f).into(binding.campingDetailImg2);

        } else {
            Glide.with(requireActivity()).load(camping.firstImageURL).thumbnail(0.1f).placeholder(R.drawable.loading).into(binding.campingDetailImg1);
            Glide.with(requireActivity()).load(camping.firstImageURL).thumbnail(0.1f).placeholder(R.drawable.loading).into(binding.campingDetailImg2);
        }
        binding.campingDetailInduty1.setText(camping.induty);
        binding.campingDetailName1.setText(camping.facltNm);

        if (camping.addr2 != null) {
            binding.campingDetailAddr1.setText(camping.addr1 + camping.addr2);
        } else {
            binding.campingDetailAddr1.setText(camping.addr1);
        }

        binding.campingDetailInduty2.setText(camping.induty);
        binding.campingDetailName2.setText(camping.facltNm);

        if (camping.addr2 != null) {
            binding.campingDetailAddr2.setText(camping.addr1 + camping.addr2);
        } else {
            binding.campingDetailAddr2.setText(camping.addr1);
        }

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem
            mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint
            mapPoint) {

    }

    @Override
    public void setMapReSetting(String location, String choiceArea, String choiceCity, String
            cityMsg, ResultMsg resultMsg, MapSlideMsg mapSlideMsg) {

        this.resultMsg = resultMsg;
        this.mapSlideMsg = mapSlideMsg;
        this.campings = resultMsg.getArrayList();


        if (resultMsg.getArrayList().size() == 0) {
            binding.mapText.setVisibility(View.VISIBLE);
            mapSlideDialog.show();
            setMapViewAreaLocition(location, choiceArea, choiceCity, cityMsg);

        } else {
            binding.mapText.setVisibility(View.VISIBLE);
            mapSlideDialog.show();

            setMapViewAreaLocition(location, choiceArea, choiceCity, cityMsg);
        }
    }

    private void setMapViewAreaLocition(String location, String choiceArea, String
            choiceCity, String cityMsg) {

        String[] areaLocation;
        areaLocation = location.split(",");

        for (int i = 0; i < areaLocation.length; i++) {
            areaLocation[i] = areaLocation[i].replaceAll(" ", "");
        }


        if (cityMsg.equals("checkCity")) {
            binding.mapText.setText(choiceArea + " > " + choiceCity);

            setMapView(areaLocation[1], areaLocation[0], null);
            tx = areaLocation[1];
            ty = areaLocation[0];
        } else {
            binding.mapText.setText(choiceArea);

            setMapView(areaLocation[1], areaLocation[0], choiceArea);
            tx = areaLocation[1];
            ty = areaLocation[0];
        }
    }


    String tx, ty;


    @Override
    public void onStart() {
        super.onStart();
    }

    int moveActivity = 0;

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //처음 실행(MianActivity)
    public void initMap() {
        if (mapView == null) {
            mapView = new MapView(requireActivity());

            setMapView(tx, ty, null);
            Log.v("셋맵뷰", "ㅋㄴ");
        }
        if (mapViewContainer.getChildCount() == 0) {
            mapViewContainer.addView(mapView);
        }

    }

    //ParsingMsgEvent로 실행
    public void initMap3(String tx, String ty) {
        this.tx = tx;
        this.ty = ty;

        setMapView();


    }

    public void initMap2() {
        if (mapView != null) {

            mapViewContainer.removeView(mapView);
            mapView = null;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mapViewContainer.removeView(mapView);
        mapView = null;
        binding.campingItemViewBox.setVisibility(View.GONE);
        binding.campingItemViewDataBox1.setVisibility(View.VISIBLE);
        binding.campingItemViewDataBox2.setVisibility(View.GONE);
        binding.campingItemViewBoxChangeBtn.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);

    }

    @Override
    public void setEvent(int event) {
        mapSlideDialog = new MapSlideDialog(requireActivity(), (ParsingMsgEvent) MapFragment.this, MapFragment.this);
        mapSlideDialog.show();

    }
}