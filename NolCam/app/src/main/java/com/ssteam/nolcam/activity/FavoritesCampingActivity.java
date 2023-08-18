package com.ssteam.nolcam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.FavoritesCampingAdapter;
import com.ssteam.nolcam.databinding.ActivityFavoritesCampingBinding;
import com.ssteam.nolcam.db.FavoritesCampingDBHelper;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class FavoritesCampingActivity extends AppCompatActivity {
    ActivityFavoritesCampingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites_camping);

        FavoritesCampingDBHelper dbHelper = new FavoritesCampingDBHelper(this);

        ArrayList<Camping> arrayList = dbHelper.select();
        for(int i = 0; i < arrayList.size(); i++) {
        }
        if (arrayList.size() == 0) {
            binding.favoritesCampingList.setVisibility(View.GONE);
            binding.favoritesCampingNoCountText.setVisibility(View.VISIBLE);
        } else {
            FavoritesCampingAdapter adapter = new FavoritesCampingAdapter(this, R.layout.item_search_result1, arrayList);
            adapter.setActivity(this);
            binding.favoritesCampingList.setAdapter(adapter);

            binding.favoritesCampingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(FavoritesCampingActivity.this, CampingDetailActivity.class);
                    intent.putExtra("CampingID", arrayList.get(i).id);
                    startActivity(intent);
                }
            });

        }



        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void countZero() {
        binding.favoritesCampingList.setVisibility(View.GONE);
        binding.favoritesCampingNoCountText.setVisibility(View.VISIBLE);
    }
}