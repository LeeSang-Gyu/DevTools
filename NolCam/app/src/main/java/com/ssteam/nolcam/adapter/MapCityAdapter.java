package com.ssteam.nolcam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.Interface.ItemChoiceEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.generic.MapSlideMsg;

import java.util.ArrayList;

public class MapCityAdapter extends RecyclerView.Adapter<MapCityAdapter.CityChoiceAdapterViewHolder> {
    String[] areas;
    public ArrayList<TextView> views;

    ItemChoiceEvent itemChoiceEvent;
    MapSlideMsg mapSlideMsg;


    public MapCityAdapter(String[] areas) {
        this.areas = areas;

        views = new ArrayList<>();
    }

    public void setSlideEvent(ItemChoiceEvent itemChoiceEvent, MapSlideMsg mapSlideMsg) {
        this.itemChoiceEvent = itemChoiceEvent;
        this.mapSlideMsg = mapSlideMsg;
    }

    @NonNull
    @Override
    public CityChoiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

        return new CityChoiceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityChoiceAdapterViewHolder holder, int position) {
        holder.tv_area.setText(areas[position]);
    }

    @Override
    public int getItemCount() {
        return areas.length;
    }

    class CityChoiceAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_area;

        public CityChoiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_area = itemView.findViewById(R.id.area);
            views.add(tv_area);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < views.size(); i++) {
                        if (i == getBindingAdapterPosition()) {
                            views.get(i).setBackground(itemView.getContext().getResources().getDrawable(R.drawable.border_item_choice));
                            mapSlideMsg.setPosition(i);
                        } else {
                            views.get(i).setBackground(itemView.getContext().getResources().getDrawable(R.drawable.border_item));
                        }

                    }


                    mapSlideMsg.setCityResult(String.valueOf(tv_area.getText()));
                    mapSlideMsg.setResultMsg("checkCity");
                    itemChoiceEvent.itemChoice(2, mapSlideMsg);


                }
            });
        }
    }
}
