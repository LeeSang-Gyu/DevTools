package com.ssteam.nolcam.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ssteam.nolcam.R;

import java.util.ArrayList;

public class CategoryChoiceAdapter extends RecyclerView.Adapter<CategoryChoiceAdapter.CategoryChoiceAdapterViewHolder> {
    int[] imgs;
    String[] categorys;
    SparseBooleanArray choiceArray;
    int choicePos;

    public CategoryChoiceAdapter(String[] categorys) {
        this.categorys = categorys;
        imgs = new int[]{R.drawable.all, R.drawable.camping, R.drawable.carcamping, R.drawable.glamping, R.drawable.caravan};
        choiceArray = new SparseBooleanArray(0);
    }

    public void setChoicePos(int choicePos) {
        this.choicePos = choicePos;

        choiceArray.put(choicePos, true);
    }


    @NonNull
    @Override
    public CategoryChoiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        return new CategoryChoiceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryChoiceAdapterViewHolder holder, int position) {
        if (choiceArray.get(position, false)) {
            holder.tv_category.setBackground(holder.tv_category.getContext().getResources().getDrawable(R.drawable.border_choice_dialog_choice));
        } else {
            holder.tv_category.setBackground(holder.tv_category.getContext().getResources().getDrawable(R.drawable.border_choice_dialog_non_choice));
        }

        holder.tv_category.setText(categorys[position]);
        if (!categorys[position].equals("")) {
            Glide.with(holder.img_category.getContext()).load(holder.img_category.getContext().getResources().getDrawable(imgs[position])).into(holder.img_category);
        }
    }

    @Override
    public int getItemCount() {
        return categorys.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    class CategoryChoiceAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category;
        ImageView img_category;

        public CategoryChoiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_category = itemView.findViewById(R.id.category_name);
            img_category = itemView.findViewById(R.id.category_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getBindingAdapterPosition();
                    if (!categorys[pos].equals("")) {
                        for (int i = 0; i < categorys.length; i++) {
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