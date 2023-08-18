package com.ssteam.nolcam.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ssteam.nolcam.db.SettingSharedPred;

import java.util.HashMap;
import java.util.Map;

public class PermissionMgr {
    private AppCompatActivity appCompatActivity;
    private Context context;
    private SettingSharedPred settingSharedPred;

    ActivityResultLauncher<String[]> permissionLauncher;
    ActivityResultLauncher<Intent> detailPermissionLauncher;

    String[] permission;

    public PermissionMgr(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        context = appCompatActivity.getApplicationContext();

        settingSharedPred = new SettingSharedPred(appCompatActivity);
    }

    // 앱 설정으로 intent해서 권한의 허용/거부 여부를 현 액티비티로 가져오기위한 메소드
    // intent는 requestAppDetailPermission를 이용한다.
    public void initAppDetailPermission(String[] permissions) {
        detailPermissionLauncher = appCompatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                HashMap<String, Boolean> map = new HashMap<>();
                for (int i = 0; i < permissions.length; i++) {
                    boolean isCheck = false;
                    if (ContextCompat.checkSelfPermission(appCompatActivity, permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                        isCheck = true;
                    }
                    map.put(permissions[i], isCheck);
                }
                permissionEventListener.onResultMsg(map);
            }
        });
    }

    public void requestAppDetailPermission() {
        Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + appCompatActivity.getPackageName()));
        detailPermissionLauncher.launch(appDetail);
    }

    public void initRequestPermission(String[] permissions) {
        this.permission = permissions;

        permissionLauncher = appCompatActivity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                HashMap<String, Boolean> map = new HashMap<>();
                for (int i = 0; i < permissions.length; i++) {
                    boolean isCheck = false;
                    if (ContextCompat.checkSelfPermission(appCompatActivity, permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                        isCheck = true;
                    }
                    map.put(permissions[i], isCheck);
                }

                permissionEventListener.onResultMsg(map);
            }
        });
    }

    public void requestPermission() {
        if (settingSharedPred.getGpsPermissionValue() == false) { // 권한 실행을 최초로 묻는지.
            settingSharedPred.setGpsPermissionValue(true);
            permissionLauncher.launch(permission);
        } else { // 권한 실행을 한번 이상 물어 봤다면 안내 다이얼로그 실행
            AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity)
                    .setTitle("권한 설정 안내")
                    .setMessage("위치 권한을 허용하지 않았습니다.\n\n권한을 허용하시려면\n설정 > 어플리케이션 관리 > 놀캠")
                    .setNegativeButton("설정하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestAppDetailPermission();
                        }
                    })
                    .setPositiveButton("확인", null);
            builder.create().show();
        }
    }

    public boolean check(String permission) {
        if (ContextCompat.checkSelfPermission(appCompatActivity, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    // 커스텀 이벤트 만들기
    public interface permissionEventListener {
        void onResultMsg(HashMap<String, Boolean> result);

    }

    private permissionEventListener permissionEventListener;

    public void setOnPermissionEventListener(permissionEventListener listener) {
        permissionEventListener = listener;
    }


    /* 권한요청이 단일 요청인 경우
     *    ActivityResultLauncher<String> permissionLauncher;
          permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGrandted -> {
             if(isGrandted) {
                 Toast.makeText(getApplication(), "허용", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(getApplication(), "거부", Toast.LENGTH_SHORT).show();
             }
          });

        //권한 요청할거 세팅
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
     */

    // 많은경우
//                String[] permissions = {
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//        };
//
//        isFlag = true;
//
//        ActivityResultLauncher<String[]> activityResultLauncher;
//        activityResultLauncher = appCompatActivity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGrandted -> {
//
//            for (int i = 0; i < permissions.length; i++) {
//
//                if (isGrandted.get(permissions[i]) == false) {
//                    isFlag = false;
//                }
//            }
//        });
//
//        activityResultLauncher.launch(permissions);
//
//        return isFlag;
}