package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.activity.CampingDetailActivity;
import com.ssteam.nolcam.activity.MapFragment;
import com.ssteam.nolcam.activity.SearchResultActivity;
import com.ssteam.nolcam.generic.Camping;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MapSlideAdapter extends PagerAdapter {

    private ArrayList<Camping> arrayList = new ArrayList<>();

    Context context;
    LayoutInflater inflater;
    int layout;
    MapFragment mapFragment;


    public MapSlideAdapter(Context context, ArrayList<Camping> arrayList, int layout, MapFragment mapFragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
        this.mapFragment = mapFragment;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == (View) object;
    }


    //화면에 표시할 뷰 생성
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, container, false);

        TextView facltNm = view.findViewById(R.id.slide_name);
        TextView addr = view.findViewById(R.id.slide_addr);
        ImageView firstImg = view.findViewById(R.id.Slide_img);


        String address = arrayList.get(position).getAddr1();
        String name = arrayList.get(position).getFacltNm();
        String img = arrayList.get(position).getFirstImageURL();

        addr.setText(address);
        facltNm.setText(name);

        if (arrayList.get(position).getFirstImageURL().equals("")){
            firstImg.setImageResource(R.drawable.noimg);

        }else{
            Glide.with(context)
                    .load(img)
                    .into(firstImg);
        }


        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CampingDetailActivity.class);
                mapFragment.initMap2();
                intent.putExtra("CampingID", arrayList.get(position).id);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
