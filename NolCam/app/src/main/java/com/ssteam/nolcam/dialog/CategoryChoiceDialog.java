package com.ssteam.nolcam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.Interface.DialogMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.CategoryChoiceAdapter;
import com.ssteam.nolcam.databinding.DialogCategoryChoiceBinding;

import java.util.ArrayList;

public class CategoryChoiceDialog extends Dialog {
    DialogCategoryChoiceBinding binding;
    DialogMsgEvent dialogMsgEvent;

    String[] categorys = new String[]{"전체", "일반야영장", "자동차야영장", "글램핑", "카라반", ""};
    int categoryPos = -1;

    public CategoryChoiceDialog(@NonNull Context context, DialogMsgEvent dialogMsgEvent) {
        super(context);
        this.dialogMsgEvent = dialogMsgEvent;
    }

    public void setCategoryPos(int categoryPos) {
        this.categoryPos = categoryPos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_category_choice, null, false);
        setContentView(binding.getRoot());

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();

            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            params.windowAnimations = R.style.DialogAnimation;
            window.setAttributes(params);

            window.setGravity(Gravity.BOTTOM);
        }

        // Setting
        CategoryChoiceAdapter categoryChoiceAdapter = new CategoryChoiceAdapter(categorys);
        if (categoryPos != -1) {
            categoryChoiceAdapter.setChoicePos(categoryPos);
        }

        categoryChoiceAdapter.setOnItemClickListener(new CategoryChoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                categoryPos = pos;
            }
        });

        binding.choiceList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 3;
                outRect.right = 3;
            }
        });

        binding.choiceList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.choiceList.setAdapter(categoryChoiceAdapter);

        // Event
        binding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.choiceFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryPos == -1) {
                    Toast.makeText(getContext(), "카테고리를 선택 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();

                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(categoryPos + "");
                arrayList.add(categorys[categoryPos]);
                dialogMsgEvent.onResult("Category", arrayList);
            }
        });
    }
}