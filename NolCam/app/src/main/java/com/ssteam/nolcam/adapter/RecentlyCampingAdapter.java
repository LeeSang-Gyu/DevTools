package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.Interface.DMLMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.generic.Camping;

import java.util.ArrayList;

public class RecentlyCampingAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Camping> campings;
    DMLMsgEvent dmlMsgEvent;

    public RecentlyCampingAdapter(Context context, DMLMsgEvent dmlMsgEvent, ArrayList<Camping> campings) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.campings = campings;
        this.dmlMsgEvent = dmlMsgEvent;
    }

    public RecentlyCampingAdapter(Context context, int i, ArrayList<Camping> campings) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.campings = campings;
        this.dmlMsgEvent = dmlMsgEvent;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecentlyCampingViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_recently_camping, parent, false);

            vh = new RecentlyCampingViewHolder();

            // findByView 작성
            vh.iv_camping = convertView.findViewById(R.id.iv_recentlyCamping);
            vh.tv_title = convertView.findViewById(R.id.tv_recentlyCamping_title);
            vh.tv_addr = convertView.findViewById(R.id.tv_recentlyCamping_addr);
            vh.tv_date = convertView.findViewById(R.id.tv_recentlyCamping_date);
            vh.iv_delete = convertView.findViewById(R.id.tv_recentlyCamping_delete);

            convertView.setTag(vh);
        } else {
            vh = (RecentlyCampingViewHolder) convertView.getTag();
        }

        vh.tv_title.setText(campings.get(position).facltNm);
        vh.tv_addr.setText(campings.get(position).addr1);
        String imageUrl = campings.get(position).firstImageURL;
        if (imageUrl.equals("")) {
            Glide.with(context).load(R.drawable.noimg).thumbnail(0.1f).into(vh.iv_camping);
        } else {
            Glide.with(context).load(imageUrl).thumbnail(0.1f).placeholder(R.drawable.loading).into(vh.iv_camping);
        }

        vh.tv_date.setText(campings.get(position).date);

        vh.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dmlMsgEvent.onDelete(campings.get(position).db_id);
            }
        });

        return convertView;
    }
}


class RecentlyCampingViewHolder {
    TextView tv_title;
    ImageView iv_camping;
    TextView tv_addr;
    TextView tv_date;
    ImageView iv_delete;
}