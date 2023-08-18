package com.ssteam.nolcam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ssteam.nolcam.R;

import java.util.ArrayList;

public class AppDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        ImageView btn_back = findViewById(R.id.back_btn);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ListView lv_opensource = findViewById(R.id.lv_opensource);

        ArrayList<String> opensourceArray = new ArrayList<>();
        opensourceArray.add("고캠핑");
        opensourceArray.add("카카오맵 API");
        opensourceArray.add("Retrofit");
        opensourceArray.add("OKHttp");
        opensourceArray.add("Glide");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, opensourceArray);

        lv_opensource.setAdapter(adapter);
        lv_opensource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AppDetailActivity.this, OpenSourceActivity.class);
                intent.putExtra("Num", i);
                startActivity(intent);
            }
        });
    }
}