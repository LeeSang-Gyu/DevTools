package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.activity.FavoritesCampingActivity;
import com.ssteam.nolcam.db.FavoritesCampingDBHelper;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class FavoritesCampingAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Camping> campings;
    int layoutId;

    FavoritesCampingDBHelper dbHelper;

    FavoritesCampingActivity activity;

    public FavoritesCampingAdapter(Context context, int id, ArrayList<Camping> campings) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = id;
        this.campings = campings;

        dbHelper = new FavoritesCampingDBHelper(context);
    }

    public void setActivity(FavoritesCampingActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return campings.size();
    }

    @Override
    public Object getItem(int position) {
        return campings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FavoritesCampingViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutId, parent, false);

            vh = new FavoritesCampingViewHolder();

            // findByView 작성
            vh.iv_camping = convertView.findViewById(R.id.iv_searchResult);
            vh.tv_title = convertView.findViewById(R.id.tv_searchResult_title);
            vh.tv_addr = convertView.findViewById(R.id.tv_searchResult_addr);
            vh.tv_intro_line = convertView.findViewById(R.id.tv_searchResult_intro_line);
            vh.iv_favorites = convertView.findViewById(R.id.iv_searchResult_favorites);
            vh.tv_induty = convertView.findViewById(R.id.tv_searchResult_induty);

            convertView.setTag(vh);
        } else {
            vh = (FavoritesCampingViewHolder) convertView.getTag();
        }


        vh.tv_title.setText(campings.get(position).facltNm);
        vh.tv_addr.setText(campings.get(position).addr1);
        vh.tv_intro_line.setText(campings.get(position).lineIntro);
        vh.tv_induty.setText(getInduty(campings.get(position).induty));

        String imageUrl = campings.get(position).firstImageURL;
        if (imageUrl.equals("")) {
            Glide.with(context).load(R.drawable.noimg).thumbnail(0.1f).into(vh.iv_camping);
        } else {
            Glide.with(context).load(imageUrl).thumbnail(0.1f).placeholder(R.drawable.loading).into(vh.iv_camping);
        }

        vh.iv_favorites.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));

        vh.iv_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.delete(campings.get(position).id);
                campings.remove(position);


                notifyDataSetChanged();

                if(campings.size() == 0) {
                    activity.countZero();
                }
            }
        });

        return convertView;
    }

    public void addCamping(Camping camping) {
        campings.add(camping);
    }

    public String getInduty(String value) {
        String induty = "";
        String[] indutys = value.split(",");

        for (int i = 0; i < indutys.length; i++) {
            induty += "#" + indutys[i] + " ";
        }

        return induty;
    }
}


class FavoritesCampingViewHolder {
    TextView tv_title;
    ImageView iv_camping;
    TextView tv_addr;
    TextView tv_intro_line;
    TextView tv_induty;
    ImageView iv_favorites;
}
