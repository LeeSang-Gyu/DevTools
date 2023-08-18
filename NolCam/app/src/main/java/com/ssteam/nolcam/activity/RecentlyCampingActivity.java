package com.ssteam.nolcam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.ssteam.nolcam.Interface.DMLMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.FavoritesCampingAdapter;
import com.ssteam.nolcam.adapter.RecentlyCampingAdapter;
import com.ssteam.nolcam.adapter.RecentlyKeywordAdapter;
import com.ssteam.nolcam.databinding.ActivityRecentlyCampingBinding;
import com.ssteam.nolcam.db.FavoritesCampingDBHelper;
import com.ssteam.nolcam.db.RecentlyCampingDBHelper;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class RecentlyCampingActivity extends AppCompatActivity {
    ActivityRecentlyCampingBinding binding;
    RecentlyCampingDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recently_camping);

        dbHelper = new RecentlyCampingDBHelper(this);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.recentlyCampingAllDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteAll();

                countZero();
            }
        });

        ArrayList<Camping> arrayList = dbHelper.select();
        if (arrayList.size() == 0) {
            binding.recentlyCampingList.setVisibility(View.GONE);
            binding.recentlyCampingNoCountText.setVisibility(View.VISIBLE);
        } else {
            RecentlyCampingAdapter adapter = new RecentlyCampingAdapter(this, R.layout.item_search_result1, arrayList);
            //adapter.setActivity(this);
            binding.recentlyCampingList.setAdapter(adapter);

            binding.recentlyCampingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(RecentlyCampingActivity.this, CampingDetailActivity.class);
                    intent.putExtra("CampingID", arrayList.get(i).id);
                    startActivity(intent);
                }
            });

        }
    }

    public void countZero() {
        binding.recentlyCampingList.setVisibility(View.GONE);
        binding.recentlyCampingNoCountText.setVisibility(View.VISIBLE);
    }
}