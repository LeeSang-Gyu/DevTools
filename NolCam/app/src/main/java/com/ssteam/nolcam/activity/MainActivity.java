package com.ssteam.nolcam.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.databinding.ActivityMainBinding;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.ConnectPHP;
import com.ssteam.nolcam.util.PermissionMgr;
import com.ssteam.nolcam.viewmodel.MainViewModel;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public static Context mainContext;

    ActivityMainBinding binding;

    PermissionMgr permissionMgr;

    FragmentManager fm;
    FragmentTransaction ft;
    HomeFragment homeFragment;
    RecommendFragment recommendFragment;
    SearchFragment searchFragment;
    MapFragment mapFragment;

    ImageView[] bottomBarImg;
    TextView[] bottomBarTv;

    int fragmentId;
    boolean isMenuBar;

    String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);

        mainContext = this;

        // setting
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        permissionMgr = new PermissionMgr(this);
        permissionMgr.initAppDetailPermission(permissions);
        permissionMgr.initRequestPermission(permissions);
        permissionMgr.setOnPermissionEventListener(new PermissionMgr.permissionEventListener() {
            @Override
            public void onResultMsg(HashMap<String, Boolean> result) {
                requestHomeGps();
            }
        });

        bottomBarImg = new ImageView[]{binding.bottomHomeImg, binding.bottomRecommendImg, binding.bottomSearchImg, binding.bottomMapImg};
        bottomBarTv = new TextView[]{binding.bottomHomeTv, binding.bottomRecommendTv, binding.bottomSearchTv, binding.bottomMapTv};

        fm = getSupportFragmentManager();
        fragmentId = binding.fragmentContainer.getId();

        ft = fm.beginTransaction();
        homeFragment = new HomeFragment(MainActivity.this);
        ft.add(fragmentId, homeFragment);
        ft.commitAllowingStateLoss();

        // event
        binding.recentlyCampingBtn.setOnClickListener(onClickListener);
        binding.favoritesCampingBtn.setOnClickListener(onClickListener);
        binding.appReviewBtn.setOnClickListener(onClickListener);
        binding.devMsgBtn.setOnClickListener(onClickListener);
        binding.appDetailBtn.setOnClickListener(onClickListener);

        viewModel.isMenu.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isMenuBar = aBoolean;
                if (aBoolean) {
                    binding.myPageBox.openDrawer(Gravity.RIGHT);
                } else {
                    binding.myPageBox.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        viewModel.fragmentCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                ft = fm.beginTransaction();

                switch (integer) {
                    case 0:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment(MainActivity.this);
                            ft.add(fragmentId, homeFragment);
                        }

                        if (homeFragment != null) ft.show(homeFragment);
                        if (recommendFragment != null) ft.hide(recommendFragment);
                        if (searchFragment != null) ft.hide(searchFragment);
                        if (mapFragment != null) ft.hide(mapFragment);
                        focusBottomBar(integer);
                        break;
                    case 1:
                        if (recommendFragment == null) {
                            recommendFragment = new RecommendFragment();
                            ft.add(fragmentId, recommendFragment);
                        }

                        if (homeFragment != null) ft.hide(homeFragment);
                        if (recommendFragment != null) ft.show(recommendFragment);
                        if (searchFragment != null) ft.hide(searchFragment);
                        if (mapFragment != null) ft.hide(mapFragment);
                        focusBottomBar(integer);
                        break;
                    case 2:
                        if (searchFragment == null) {
                            searchFragment = new SearchFragment(MainActivity.this);
                            ft.add(fragmentId, searchFragment);
                        }

                        if (homeFragment != null) ft.hide(homeFragment);
                        if (recommendFragment != null) ft.hide(recommendFragment);
                        if (searchFragment != null) ft.show(searchFragment);
                        if (mapFragment != null) ft.hide(mapFragment);
                        focusBottomBar(integer);
                        break;
                    case 3:
                        if (mapFragment == null) {
                            mapFragment = new MapFragment();
                            ft.add(fragmentId, mapFragment);
                        }

                        if (homeFragment != null) ft.hide(homeFragment);
                        if (recommendFragment != null) ft.hide(recommendFragment);
                        if (searchFragment != null) ft.hide(searchFragment);
                        if (mapFragment != null) ft.show(mapFragment);

                        focusBottomBar(integer);
                        break;
                }

                ft.commitAllowingStateLoss();
            }
        });
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == binding.appDetailBtn.getId()) {
                Intent intent = new Intent(MainActivity.this, AppDetailActivity.class);
                startActivity(intent);
            } else if (v.getId() == binding.devMsgBtn.getId()) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] address = {"gfs6307@gmail.com"}; // 임시
                intent.putExtra(Intent.EXTRA_EMAIL, address);
                intent.putExtra(Intent.EXTRA_SUBJECT, "놀캠 문의");
                intent.putExtra(Intent.EXTRA_TEXT, "문의내용\n===================\n");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "어떤 앱을 이용해서 문의 하시겠습니까?"));
            } else if (v.getId() == binding.appReviewBtn.getId()) {
                String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    // 플레이스토어가 설치되어있지 않은 경우 url로 이동
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            } else if (v.getId() == binding.favoritesCampingBtn.getId()) {
                Intent intent = new Intent(MainActivity.this, FavoritesCampingActivity.class);
                startActivity(intent);
            } else if (v.getId() == binding.recentlyCampingBtn.getId()) {
                Intent intent = new Intent(MainActivity.this, RecentlyCampingActivity.class);
                startActivity(intent);
            }
        }
    };

    public void focusBottomBar(int value) {
        for (int i = 0; i < bottomBarImg.length; i++) {
            if (i == value) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomBarImg[i].setImageTintList(ColorStateList.valueOf(Color.parseColor("#71C7F6")));
                    bottomBarTv[i].setTextColor(getApplicationContext().getResources().getColor(R.color.main_color));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomBarImg[i].setImageTintList(ColorStateList.valueOf(Color.parseColor("#A6A6A6")));
                    bottomBarTv[i].setTextColor(getApplicationContext().getResources().getColor(R.color.non_focus_color));
                }
            }
        }
    }

    long backTime;

    @Override
    public void onBackPressed() {
        if (isMenuBar) {
            binding.myPageBox.closeDrawer(Gravity.RIGHT);
            isMenuBar = false;

            return;
        } else {
            if (System.currentTimeMillis() > backTime + 2000) {
                backTime = System.currentTimeMillis();
                Toast.makeText(this, "앱을 종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show();
            } else if (System.currentTimeMillis() <= backTime + 2000) {
                finish();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mapFragment != null) {
            mapFragment.initMap();
        }
    }

    public void requestHomeGps() {
        if (permissionMgr.check(permissions[0])) {
            // GPS, NetWork GPS 사용 가능 여부)
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // 켜져있으면 listener 실행 후 좌표값 캐치 하면 GPS값 수신시작
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
                Toast.makeText(this, "GPS 정보를 수신중 입니다.\n건물, 지하에서는 수신이 지연 되거나 안될 수 있습니다.", Toast.LENGTH_SHORT).show();
                homeFragment.showMsgBox("LoadingMsg");
            } else { // 센서 사용설정 후 다시 시도 하라는 메세지를 보여준다.
                Toast.makeText(this, "GPS(위치 서비스)를 사용 설정 후 다시 시도 하세요.", Toast.LENGTH_SHORT).show();
                homeFragment.showMsgBox("SensorMsg");
            }
        } else {
            homeFragment.showMsgBox("PermissionMsg");
            permissionMgr.requestPermission();
        }
    }

    boolean isGps = true;
    double mapX = -1;
    double mapY = -1;

    public double[] getMapXY() {
        return new double[]{mapX, mapY};
    }


    boolean isGpsActive = false;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        isGpsActive = true;

        mapX = location.getLatitude();
        mapY = location.getLongitude();

        Log.v("mapX", String.valueOf(mapX));
        Log.v("mapY", String.valueOf(mapY));

        if (isGps) {
            homeFragment.executeGpsParsing(getMapXY());
            isGps = false;
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
}