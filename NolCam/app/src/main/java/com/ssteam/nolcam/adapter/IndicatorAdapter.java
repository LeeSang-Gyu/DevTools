package com.ssteam.nolcam.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.ssteam.nolcam.R;

public class IndicatorAdapter {
    private Context context;
    private ImageView[] img_indicator;

    public int length;
    public int width;
    public int height;
    public int marginLeft;

    private String choiceColor;
    private String notChoiceColor;

    public IndicatorAdapter(Context context, int length) {
        this.context = context;
        this.length = length;

        width = 30;
        height = 30;
        marginLeft = 25;

        choiceColor = "#000000";
        notChoiceColor = "#A3A3A3";
    }

    public void setIndicator(ViewGroup view) {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(params);

        img_indicator = new ImageView[length];
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(width, height);
            if (i != 0) {
                imgParams.setMargins(marginLeft, 0, 0, 0);
            }
            imageView.setLayoutParams(imgParams);
            imageView.setImageResource(R.drawable.indicator);

            String baseColor = notChoiceColor;
            if (i == 0) {
                baseColor = choiceColor;
            }

            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(baseColor));
            imageView.setImageTintList(colorStateList);

            img_indicator[i] = imageView;

            linearLayout.addView(imageView);
        }

        view.addView(linearLayout);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < length; i++) {
                    String baseColor = notChoiceColor;
                    if (i == position) {
                        baseColor = choiceColor;
                    }

                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(baseColor));
                    img_indicator[i].setImageTintList(colorStateList);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setChoiceColor(String changeColor) {
        this.choiceColor = validateColor(choiceColor, changeColor);
    }

    public void setNotChoiceColor(String changeColor) {
        this.notChoiceColor = validateColor(notChoiceColor, changeColor);
    }

    public String validateColor(String defaultColor, String changeColor) {
        String baseColor = changeColor;

        try {
            // #RRGGBB규칙에 어긋나지 않은지 확인
            Color.parseColor(baseColor);
        } catch (NumberFormatException e) {
            baseColor = defaultColor;
        }

        return baseColor;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public String getChoiceColor() {
        return choiceColor;
    }

    public String getNotChoiceColor() {
        return notChoiceColor;
    }
}