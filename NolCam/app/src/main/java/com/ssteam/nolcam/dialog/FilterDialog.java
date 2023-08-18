package com.ssteam.nolcam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.ssteam.nolcam.Interface.DialogMsgEvent;
import com.ssteam.nolcam.Interface.FilterMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.GroupSelector;
import com.ssteam.nolcam.databinding.DialogFilterBinding;

public class FilterDialog extends Dialog {
    Context context;
    DialogMsgEvent dialogMsgEvent;
    FilterMsgEvent filterMsgEvent;

    GroupSelector viewSelector;
    GroupSelector categorySelector;
    GroupSelector sortSelector;

    int viewPos = 0;
    int categoryPos = 0;
    int sortPos = 0;

    String[] categoryNames;

    public FilterDialog(@NonNull Context context, DialogMsgEvent dialogMsgEvent, FilterMsgEvent filterMsgEvent) {
        super(context);
        this.context = context;
        this.dialogMsgEvent = dialogMsgEvent;
        this.filterMsgEvent = filterMsgEvent;

        categoryNames = new String[]{"전체", "일반야영장", "자동차야영장", "글램핑", "카라반"};
    }

    public void setCategoryPos(int categoryPos) {
        this.categoryPos = categoryPos;
    }

    public void setViewPos(int viewPos) {
        this.viewPos = viewPos;
    }

    public void setSortPos(int sortPos) {
        this.sortPos = sortPos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_filter, null, false);
        setContentView(binding.getRoot());

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();

            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            window.setAttributes(params);
        }

        //
        viewSelector = new GroupSelector(getContext(), R.drawable.border_filter_choice, R.drawable.border_filter_non_choice);
        viewSelector.addView(binding.itemSmallShowBtn);
        viewSelector.addView(binding.itemBigShowBtn);

        categorySelector = new GroupSelector(getContext(), R.drawable.border_filter_choice, R.drawable.border_filter_non_choice);
        categorySelector.addView(binding.categoryAllBtn);
        categorySelector.addView(binding.categoryCampingBtn);
        categorySelector.addView(binding.categoryCaravanBtn);
        categorySelector.addView(binding.categoryCarCampingBtn);
        categorySelector.addView(binding.categoryGlampingBtn);

        sortSelector = new GroupSelector(getContext(), R.drawable.border_filter_choice, R.drawable.border_filter_non_choice);
        sortSelector.addView(binding.itemSortDefault);
        sortSelector.addView(binding.itemSortNear);

        int viewId = binding.itemSmallShowBtn.getId();
        if (viewPos == 1) {
            viewId = binding.itemBigShowBtn.getId();
        }
        viewSelector.selectView(viewId);

        int categoryId = binding.categoryAllBtn.getId();
        if (categoryPos == 1) {
            categoryId = binding.categoryCampingBtn.getId();
        } else if (categoryPos == 2) {
            categoryId = binding.categoryCarCampingBtn.getId();
        } else if (categoryPos == 3) {
            categoryId = binding.categoryGlampingBtn.getId();
        } else if (categoryPos == 4) {
            categoryId = binding.categoryCaravanBtn.getId();
        }
        categorySelector.selectView(categoryId);

        int sortId = binding.itemSortDefault.getId();
        if (sortPos == 1) {
            sortId = binding.itemSortNear.getId();
        }
        sortSelector.selectView(sortId);

        //
        binding.itemSmallShowBtn.setOnClickListener(viewOnClickListener);
        binding.itemBigShowBtn.setOnClickListener(viewOnClickListener);
        binding.categoryAllBtn.setOnClickListener(categoryOnClickListener);
        binding.categoryCaravanBtn.setOnClickListener(categoryOnClickListener);
        binding.categoryCampingBtn.setOnClickListener(categoryOnClickListener);
        binding.categoryCarCampingBtn.setOnClickListener(categoryOnClickListener);
        binding.categoryGlampingBtn.setOnClickListener(categoryOnClickListener);
        binding.itemSortDefault.setOnClickListener(sortOnclickListener);
        binding.itemSortNear.setOnClickListener(sortOnclickListener);

        binding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.filterFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItem(viewPos, categoryPos, sortPos, categoryNames[categoryPos]);
                }
                                /*
                dismiss();
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(viewPos + "");
                arrayList.add(categoryPos + "");
                dialogMsgEvent.onResult("Filter", arrayList);
            */
            }
        });
    }

    public interface onItemSettingListener {
        void OnItem(int viewPos, int categoryPos, int sortPos, String categoryName);
    }

    public onItemSettingListener mListener;

    public void setOnItemSettingListener(onItemSettingListener listener) {
        this.mListener = listener;
    }

    public View.OnClickListener viewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // small, big
            // 0, 1
            viewSelector.selectView(view.getId());

            final int viewID = view.getId();
            if (viewID == R.id.item_small_show_btn) {
                viewPos = 0;
            } else if (viewID == R.id.item_big_show_btn) {
                viewPos = 1;
            }
        }
    };

    public View.OnClickListener categoryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // all, camping, carcamping, glamping, caravan
            // 0, 1, 2, 3, 4
            categorySelector.selectView(view.getId());

            final int viewID = view.getId();
            if (viewID == R.id.category_all_btn) {
                categoryPos = 0;
            } else if (viewID == R.id.category_camping_btn) {
                categoryPos = 1;
            } else if (viewID == R.id.category_car_camping_btn) {
                categoryPos = 2;
            } else if (viewID == R.id.category_glamping_btn) {
                categoryPos = 3;
            } else if (viewID == R.id.category_caravan_btn) {
                categoryPos = 4;
            }
        }
    };

    public View.OnClickListener sortOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // default, near
            // 0, 1
            sortSelector.selectView(view.getId());

            final int viewID = view.getId();
            if (viewID == R.id.item_sort_default) {
                sortPos = 0;
            } else if (viewID == R.id.item_sort_near) {
                sortPos = 1;
                filterMsgEvent.filterMsg(0);
            }
        }
    };
}