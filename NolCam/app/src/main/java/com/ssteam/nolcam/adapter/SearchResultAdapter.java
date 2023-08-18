package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.db.FavoritesCampingDBHelper;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class SearchResultAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Camping> campings;
    int layoutId;

    FavoritesCampingDBHelper favoritesDbHelper;

    public SearchResultAdapter(Context context, int id, ArrayList<Camping> campings) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = id;
        this.campings = campings;

        favoritesDbHelper = new FavoritesCampingDBHelper(context);
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
        SearchResultViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutId, parent, false);

            vh = new SearchResultViewHolder();

            // findByView 작성
            vh.iv_camping = convertView.findViewById(R.id.iv_searchResult);
            vh.tv_title = convertView.findViewById(R.id.tv_searchResult_title);
            vh.tv_addr = convertView.findViewById(R.id.tv_searchResult_addr);
            vh.tv_intro_line = convertView.findViewById(R.id.tv_searchResult_intro_line);
            vh.tv_induty = convertView.findViewById(R.id.tv_searchResult_induty);
            vh.iv_favorites = convertView.findViewById(R.id.iv_searchResult_favorites);
            vh.lv_distance_box = convertView.findViewById(R.id.lv_searchResult_distance_box);
            vh.tv_distance = convertView.findViewById(R.id.tv_searchResult_distance);

            convertView.setTag(vh);
        } else {
            vh = (SearchResultViewHolder) convertView.getTag();
        }


        vh.tv_title.setText(campings.get(position).facltNm);
        vh.tv_addr.setText(campings.get(position).addr1);
        vh.tv_intro_line.setText(campings.get(position).lineIntro);
        vh.tv_induty.setText(getInduty(campings.get(position).induty));

        String imageUrl = campings.get(position).firstImageURL;
        if (imageUrl.equals("")) {
            Glide.with(context).load(R.drawable.noimg).into(vh.iv_camping);
        } else {
            Glide.with(context).load(imageUrl).into(vh.iv_camping);
        }

        if (favoritesDbHelper.getFavritesExist(campings.get(position).id)) {
            vh.iv_favorites.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));
        } else {
            vh.iv_favorites.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        }

        if (campings.get(position).distance != 0) {
            vh.lv_distance_box.setVisibility(View.VISIBLE);
            int dis = (int)campings.get(position).distance;
            vh.tv_distance.setText(dis+"Km");
        } else {
            vh.lv_distance_box.setVisibility(View.GONE);
        }

        vh.iv_favorites.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (favoritesDbHelper.getFavritesExist(campings.get(position).id)) {
                    favoritesDbHelper.delete(campings.get(position).id);
                    vh.iv_favorites.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                } else {
                    campings.get(position).favorites = 1;
                    favoritesDbHelper.insert(campings.get(position));
                    vh.iv_favorites.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFE400")));
                }

                notifyDataSetChanged();
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

class SearchResultViewHolder {
    TextView tv_title;
    ImageView iv_camping;
    TextView tv_addr;
    TextView tv_intro_line;
    TextView tv_induty;
    ImageView iv_favorites;

    LinearLayout lv_distance_box;
    TextView tv_distance;
}
