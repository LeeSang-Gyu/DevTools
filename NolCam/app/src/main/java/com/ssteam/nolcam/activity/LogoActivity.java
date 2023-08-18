package com.ssteam.nolcam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ssteam.nolcam.Interface.DBDataMsg;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.databinding.ActivityLogoBinding;
import com.ssteam.nolcam.db.SettingSharedPred;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.ConnectPHP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity implements ParsingMsgEvent, DBDataMsg {
    ActivityLogoBinding binding;

    SettingSharedPred sp;

    private int goCampingTotalcount;
    private int myDbTotalCount;
    private int checkFirstStart;
    private int errorCount;

    private ArrayList<Camping> campList;

    private boolean db_update_check = false;
    Timer timer;
    TimerTask tt;

    private int requestdate = 0;

    private ArrayList<Camping> insertArray;
    private ArrayList<Camping> updateArray;
    private ArrayList<Camping> deleteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logo);

        // setting
        sp = new SettingSharedPred(this);

        timerStart();
    }

    public void timerStart() {
         timer = new Timer();
         tt = new TimerTask() {
            @Override
            public void run() {
                boolean isPermissionExplain = sp.getPermissionExplainValue();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isPermissionExplain) { // 첫실행인 경우
                            checkFirstStart++;
                            binding.logoBox.setVisibility(View.GONE);
                            binding.permissionBox.setVisibility(View.VISIBLE);

                            binding.permissionRequestBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sp.setPermissionExplainValue(true);

                                    binding.logoBox.setVisibility(View.VISIBLE);
                                    binding.permissionBox.setVisibility(View.GONE);

                                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                                    ArrayList<Camping> arrayList = new ArrayList<>();
                                    startActivity(intent);

                                    finish();
                                }
                            });
                        } else {
                            Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                            startActivity(intent);
                            checkFirstStart = 0;

                            finish();
                        }
                    }
                });
            }
        };

        DBDataRecentInsertChceck();

        if(db_update_check){
            timer.schedule(tt, 3000); // 3초후 실행
            Log.v("logo", "순서확인");
        }else{
            Log.v("logo", "순서확인2");
        }

    }

    private void DBDataRecentInsertChceck() {
        ConnectPHP connectPHP = new ConnectPHP();
        connectPHP.setDBEvent((DBDataMsg) LogoActivity.this, new ResultMsg("Logo"));
        connectPHP.createCall(12);

    }

    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        campList = new ArrayList<Camping>();
        int num = Integer.valueOf(resultMsg.getCallIndex().get("callIndex"));

        if (num == 1) {
            this.campList = resultMsg.getArrayList();
        } else if (num == 8) {
            this.goCampingTotalcount = Integer.valueOf(resultMsg.getCallIndex().get("totalCount"));
        } else if (num == 9) {
            this.myDbTotalCount = Integer.valueOf(resultMsg.getCallIndex().get("totalCount"));
        } else if (num == 10) {
            Log.e("테스트 쿼리", resultMsg.getCallIndex().get("totalCount"));
        }


        Log.e("goCampingTotalcount/2", String.valueOf(goCampingTotalcount));
        Log.e("myDbTotalCount2", String.valueOf(myDbTotalCount));

        this.errorCount = goCampingTotalcount - myDbTotalCount;
        Log.e("errorCount", String.valueOf(errorCount));


    }

    private void setGocampingSync(int date, String synctype) {
        ConnectPHP connectPHP = new ConnectPHP();

        if (synctype.equals("A")) {
            connectPHP.setDBEvent((DBDataMsg) LogoActivity.this, new ResultMsg("Logo"));
            connectPHP.setBaseParameter(1, 20, 1);
            connectPHP.setSyncModParameter(synctype, date);
            connectPHP.createCall(13);


        } else if (synctype.equals("U")) {

            connectPHP.setDBEvent((DBDataMsg) LogoActivity.this, new ResultMsg("Logo"));
            connectPHP.setBaseParameter(1, 20, 1);
            connectPHP.setSyncModParameter(synctype, date);
            connectPHP.createCall(13);



        } else if (synctype.equals("D")) {

            connectPHP.setDBEvent((DBDataMsg) LogoActivity.this, new ResultMsg("Logo"));
            connectPHP.setBaseParameter(1, 20, 1);
            connectPHP.setSyncModParameter(synctype, date);
            connectPHP.createCall(13);
        }else{

        }
    }

    @Override
    public void onDBDataMsg(ResultMsg resultMsg) {
        String dbday;
        String nowday;

        //resultMsg.getArrayList() != null
        if (resultMsg.getSyncType().equals("A")) {
            this.insertArray =  new ArrayList<>();
            this.insertArray = resultMsg.getArrayList();
            setGocampingSync(requestdate, "U");

            Log.v("결과 확인/" + resultMsg.getSyncType() , String.valueOf(insertArray.size()));


        }// 업데이트
        else if (resultMsg.getSyncType().equals("U")) {
            this.updateArray =  new ArrayList<>();
            this.updateArray = resultMsg.getArrayList();
            setGocampingSync(requestdate, "D");

            Log.v("결과 확인/" + resultMsg.getSyncType(), String.valueOf(updateArray.size()));

        }// 삭제
        else if (resultMsg.getSyncType().equals("D")){
            this.deleteArray = new ArrayList<>();
            this.deleteArray = resultMsg.getArrayList();
            db_update_check = true;
            Log.v("결과 확인/" +resultMsg.getSyncType(), String.valueOf(deleteArray.size()));

            ConnectPHP connectPHP = new ConnectPHP();

            //신규 (A) 캠핑장 DB에 추가 작업
            if (insertArray.size() != 0 || updateArray.size() != 0 || deleteArray.size() != 0){
                connectPHP.setMuiltQuery(insertArray, updateArray, deleteArray);
                connectPHP.createCall(10);
            }
            connectPHP.createCall(14); // DB 최근 업데이트 된 날짜 수정


            timer.schedule(tt, 3000);

        }else {
            String update_date = resultMsg.getResult();
            Log.e("업데이트 날짜", update_date);


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            //DB 업데이트 날짜
            try {
                Date dbdate = format.parse(update_date);
                dbday = format.format(dbdate);
                Log.e("업데이트 날짜 변환값", dbday);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //오늘 날짜
            Date currentTime = new Date();
            String current = format.format(currentTime);

            Log.e("업데이트 데이터 확인", current);
            try {
                Date nowdate = format.parse(current);
                nowday = format.format(nowdate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (!dbday.equals(nowday)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.getDefault());
                Date date = new Date();
                this.requestdate = Integer.valueOf(sdf.format(date));

                Log.e("업데이트 날짜 변환 확인", requestdate + "");

                setGocampingSync(requestdate, "A");

                Log.e("업데이트 날짜 비교 확인", dbday + " : " + nowday);
            } else {
                Log.v("return 체크", "리턴");
                db_update_check = true;
                timer.schedule(tt, 3000); // 3초후 실행
                return;
            }
        }
    }
}