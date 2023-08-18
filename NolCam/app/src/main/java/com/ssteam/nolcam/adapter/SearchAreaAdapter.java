package com.ssteam.nolcam.adapter;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.R;

public class SearchAreaAdapter extends RecyclerView.Adapter<SearchAreaAdapter.AreaChoiceAdapterViewHolder> {
    String[] areas;
    SparseBooleanArray choiceArray;
    int choicePos;

    public SearchAreaAdapter(String[] areas) {
        this.areas = areas;
        choiceArray = new SparseBooleanArray(0);
    }

    public void setChoicePos(int choicePos) {
        this.choicePos = choicePos;

        choiceArray.put(choicePos, true);
    }

    @NonNull
    @Override
    public AreaChoiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

        return new AreaChoiceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaChoiceAdapterViewHolder holder, int position) {
        if (choiceArray.get(position, false)) {
            holder.tv_area.setBackgroundColor(Color.parseColor("#DEE6EF"));
//            holder.tv_area.setBackground(holder.tv_area.getContext().getResources().getDrawable(R.drawable.border_choice_dialog_choice));
        } else {
            holder.tv_area.setBackgroundColor(Color.WHITE);
//            holder.tv_area.setBackground(holder.tv_area.getContext().getResources().getDrawable(R.drawable.border_choice_dialog_non_choice));
        }

        holder.tv_area.setText(areas[position] + "");
    }

    @Override
    public int getItemCount() {
        return areas.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    class AreaChoiceAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_area;

        public AreaChoiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_area = itemView.findViewById(R.id.area);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getBindingAdapterPosition();
                    if (!areas[pos].equals("")) {
                        for (int i = 0; i < areas.length; i++) {
                            choiceArray.put(pos, true);

                            if (choiceArray.get(i, false)) {
                                if (i != pos) {
                                    choiceArray.put(i, false);
                                }
                            }
                        }

                        if (mListener != null) {
                            mListener.onItemClick(view, pos);
                        }

                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}