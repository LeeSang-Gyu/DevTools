package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssteam.nolcam.R;

public class FacilityAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    String[] facilitys;

    public FacilityAdapter(Context context, String[] facilitys) {
        this.context = context;
        this.facilitys = facilitys;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return facilitys.length;
    }

    @Override
    public Object getItem(int position) {
        return facilitys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FacilityViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_facility, null);

            vh = new FacilityViewHolder();

            // findByView 작성
            vh.title = convertView.findViewById(R.id.item_facilityIcon_title);
            vh.icon = convertView.findViewById(R.id.item_facilityIcon_icon);

            convertView.setTag(vh);
        } else {
            vh = (FacilityViewHolder) convertView.getTag();
        }

        vh.title.setText(facilitys[position]);

        Drawable dr = null;
        if (facilitys[position].equals("침대")) {
            dr = context.getResources().getDrawable(R.drawable.bed);
        }
        if (facilitys[position].equals("TV")) {
            dr = context.getResources().getDrawable(R.drawable.television);
        }
        if (facilitys[position].equals("에어컨")) {
            dr = context.getResources().getDrawable(R.drawable.air_con);
        }
        if (facilitys[position].equals("냉장고")) {
            dr = context.getResources().getDrawable(R.drawable.freezer);
        }
        if (facilitys[position].equals("유무선인터넷")) {
            dr = context.getResources().getDrawable(R.drawable.internet);
            vh.title.setText("인터넷");
        }
        if (facilitys[position].equals("무선인터넷")) {
            dr = context.getResources().getDrawable(R.drawable.wifi);
            vh.title.setText("와이파이");
        }
        if (facilitys[position].equals("난방기구")) {
            dr = context.getResources().getDrawable(R.drawable.hotwind);
        }
        if (facilitys[position].equals("취사도구")) {
            dr = context.getResources().getDrawable(R.drawable.cook);
        }
        if (facilitys[position].equals("내부화장실")) {
            dr = context.getResources().getDrawable(R.drawable.toilet);
        }
        if (facilitys[position].equals("내부샤워실")) {
            dr = context.getResources().getDrawable(R.drawable.washroom);
        }
        if (facilitys[position].equals("전기")) {
            dr = context.getResources().getDrawable(R.drawable.electric);
        }
        if (facilitys[position].equals("장작판매")) {
            dr = context.getResources().getDrawable(R.drawable.wood);
        }
        if (facilitys[position].equals( "온수")) {
            dr = context.getResources().getDrawable(R.drawable.hot_water);
        }
        if (facilitys[position].equals("놀이터")) {
            dr = context.getResources().getDrawable(R.drawable.playground);
        }
        if (facilitys[position].equals("운동시설")) {
            dr = context.getResources().getDrawable(R.drawable.health);
        }
        if (facilitys[position].equals("트렘폴린")) {
            dr = context.getResources().getDrawable(R.drawable.trampoline);
        }
        if (facilitys[position].equals("물놀이장")) {
            dr = context.getResources().getDrawable(R.drawable.swimming);
        }
        if (facilitys[position].equals("마트.편의점")) {
            dr = context.getResources().getDrawable(R.drawable.mart);
            vh.title.setText("마트\n편의점");
        }
        if (facilitys[position].equals("산책로")) {
            dr = context.getResources().getDrawable(R.drawable.road);
        }
        if (facilitys[position].equals("애완동물")) {
            dr = context.getResources().getDrawable(R.drawable.animal);
        }

        vh.icon.setImageDrawable(dr);

        return convertView;
    }
}


class FacilityViewHolder {
    TextView title;
    ImageView icon;
}
