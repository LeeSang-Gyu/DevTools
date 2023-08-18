package com.ssteam.nolcam.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.Interface.ItemChoiceEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.generic.MapSlideMsg;

import java.util.ArrayList;

public class MapAreaAdapter extends RecyclerView.Adapter<MapAreaAdapter.AreaChoiceAdapterViewHolder> {
    String[] areas;
    String[] cityList;

    public ArrayList<TextView> views;

    ItemChoiceEvent itemChoiceEvent;
    MapSlideMsg mapSlideMsg = null;


    public MapAreaAdapter(String[] areas, String[] cityList) {
        this.areas = areas;
        this.cityList = cityList;

        views = new ArrayList<>();
    }

    public void setSlideEvent(ItemChoiceEvent itemChoiceEvent, MapSlideMsg mapSlideMsg){
        this.itemChoiceEvent = itemChoiceEvent;
        this.mapSlideMsg = mapSlideMsg;
    }

    @NonNull
    @Override
    public AreaChoiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

        return new AreaChoiceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaChoiceAdapterViewHolder holder, int position) {
        holder.tv_area.setText(areas[position]);
    }

    @Override
    public int getItemCount() {
        return areas.length;
    }


    class AreaChoiceAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_area;


        public AreaChoiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_area = itemView.findViewById(R.id.area);
            views.add(tv_area);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0; i < views.size(); i++) {
                        if(i == getBindingAdapterPosition()) {
                            views.get(i).setBackgroundColor(Color.parseColor("#DEE6EF"));
                        } else {
                            views.get(i).setBackgroundColor(Color.WHITE);
//                            views.get(i).setBackground(itemView.getContext().getResources().getDrawable(R.drawable.border_choice_dialog_non_choice));
                        }

                        Toast.makeText(views.get(i).getContext(), i+"d", Toast.LENGTH_SHORT).show();
                    }

                    int position = getAbsoluteAdapterPosition();
                    mapSlideMsg.setResult(String.valueOf(tv_area.getText()));
                    mapSlideMsg.setPosition(position);
                    mapSlideMsg.setResultMsg("check");

                    itemChoiceEvent.itemChoice(1, mapSlideMsg);




                }
            });


        }
    }
}