package com.ssteam.nolcam.adapter;

import android.content.Context;
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
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class ImageSlideAdpater extends PagerAdapter {
    ArrayList<String> images;
    Context context;
    LayoutInflater inflater;
    int layout;

    public ImageSlideAdpater(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        layout = R.layout.item_image_slide;
    }

    public ImageSlideAdpater(Context context, ArrayList<String> images, int layout) {
        this.context = context;
        this.images = images;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, container, false);

        ImageView iv_campingImg = view.findViewById(R.id.iv_slide_image);

        String imageUrl = images.get(position);
        if (imageUrl.equals("")) {
            Glide.with(context).load(R.drawable.noimg).thumbnail(0.1f).into(iv_campingImg);
        } else {
            Glide.with(context).load(imageUrl).thumbnail(0.1f).placeholder(R.drawable.loading).into(iv_campingImg);
        }


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}