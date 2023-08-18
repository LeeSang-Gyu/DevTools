package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.activity.CampingDetailActivity;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.CampingSlide;

import java.util.ArrayList;

public class CampingSlideAdpater extends PagerAdapter {
    ArrayList<Camping> campings;
    Context context;
    LayoutInflater inflater;

    public CampingSlideAdpater(Context context, ArrayList<Camping> campings) {
        this.context = context;
        this.campings = campings;
    }

    @Override
    public int getCount() {
        return campings.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slide, container, false);

        TextView tv_title = view.findViewById(R.id.tv_slide_title);
        TextView tv_category = view.findViewById(R.id.tv_slide_category);
        ImageView iv_campingImg = view.findViewById(R.id.iv_slide_campingImg);
        LinearLayout ll_distanceBox = view.findViewById(R.id.ll_slide_distance_box);
        TextView tv_distance = view.findViewById(R.id.tv_slide_distnace);


        tv_title.setText(campings.get(position).facltNm);

        String[] categorys = campings.get(position).induty.split(",");
        String category = "";
        for (String item : categorys) {
            category += "#" + item + " ";
        }
        tv_category.setText(category);

        String imageUrl = campings.get(position).firstImageURL;

        if (imageUrl.equals("")) {
            Glide.with(context).load(R.drawable.noimg).into(iv_campingImg);
        } else {
            Glide.with(context).load(imageUrl).into(iv_campingImg);
        }

        if (campings.get(position).distance != 0.0) {
            ll_distanceBox.setVisibility(View.VISIBLE);
            tv_distance.setText(campings.get(position).distance + "Km");
        }

        iv_campingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CampingDetailActivity.class);
                intent.putExtra("CampingID", campings.get(position).id);
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}