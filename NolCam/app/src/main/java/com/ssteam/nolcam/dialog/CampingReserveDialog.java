package com.ssteam.nolcam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ssteam.nolcam.R;

public class CampingReserveDialog extends Dialog {
    Context context;
    String tel;
    String homepage;

    public CampingReserveDialog(@NonNull Context context, String tel, String homepage) {
        super(context);
        this.context = context;
        this.tel = tel;
        this.homepage = homepage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_camping_reserve);

        TextView tv_tel = findViewById(R.id.tv_campingReserve_tel);
        TextView tv_homepage = findViewById(R.id.tv_campingReserve_homepage);
        Button btn_tel = findViewById(R.id.btn_campingReserve_tel);
        Button btn_homepage = findViewById(R.id.btn_campingReserve_homepage);
        ImageView iv_close = findViewById(R.id.iv_campingReserve_close);

        if (tel.equals("")) {
            btn_tel.setVisibility(View.GONE);
        } else {
            tv_tel.setText(tel);
            btn_tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel)));
                }
            });
        }

        if (homepage.equals("")) {
            btn_homepage.setVisibility(View.GONE);
        } else {
            tv_homepage.setText(homepage);
            btn_homepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(homepage)));
                }
            });
        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
