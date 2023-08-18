package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssteam.nolcam.Interface.DMLMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.generic.Keyword;

import java.util.ArrayList;

public class RecentlyKeywordAdapter extends BaseAdapter {
    Context context;
    DMLMsgEvent DMLMsgEvent;
    ArrayList<Keyword> keywords;

    public RecentlyKeywordAdapter(Context context, DMLMsgEvent DMLMsgEvent, ArrayList<Keyword> keywords) {
        this.keywords = keywords;
        this.context = context;
        this.DMLMsgEvent = DMLMsgEvent;
    }

    @Override
    public int getCount() {
        return keywords.size();
    }

    @Override
    public Object getItem(int i) {
        return keywords.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        KeywordViewHoler viewHolder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_keyword, viewGroup, false);

            viewHolder = new KeywordViewHoler();
            viewHolder.tv_keyword = view.findViewById(R.id.tv_item_keyword_text);
            viewHolder.iv_delete = view.findViewById(R.id.iv_item_keyword_delete);

            view.setTag(viewHolder);
        } else {
            viewHolder = (KeywordViewHoler) view.getTag();
        }

        viewHolder.tv_keyword.setText(keywords.get(i).getKeyword());
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DMLMsgEvent.onDelete(keywords.get(i).getId());
            }
        });

        return view;
    }

    public class KeywordViewHoler {
        TextView tv_keyword;
        ImageView iv_delete;
    }
}
