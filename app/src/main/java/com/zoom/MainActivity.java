package com.zoom;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TestInterface {


    ZoomLayout zoomLayout;
    TextView tvView;
    int leftMargin = 0;
    int topMargin = 0;
    int tvHeight = 0;
    int tvWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zoomLayout = (ZoomLayout) findViewById(R.id.zoom_layout);
        tvView = (TextView) findViewById(R.id.tv);
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "黑色点击事件", Toast.LENGTH_SHORT).show();
            }
        });
        zoomLayout.setTestInterface(this);
        ViewTreeObserver vto = zoomLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                zoomLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = zoomLayout.getMeasuredWidth();
                int height = zoomLayout.getMeasuredHeight();
                zoomLayout.setContentSize(width, height);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tvView.getLayoutParams();
                leftMargin = lp.leftMargin;
                topMargin = lp.topMargin;
                tvHeight = tvView.getMeasuredHeight();
                tvWidth = tvView.getMeasuredWidth();
            }
        });
    }


    @Override
    public void transView(float tranx, float trany) {
        tvView.setTranslationX(tranx);
        tvView.setTranslationY(trany);
    }


    @Override
    public void scaleView(float dx, float dy) {
        double x = leftMargin * dx + (tvWidth * dx) * 0.5 - tvHeight * 0.5;
        double y = topMargin * dy + (tvHeight * dy) * 0.5 - tvWidth * 0.5;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(tvWidth, tvHeight);
        lp.leftMargin = (int) x;
        lp.topMargin = (int) y;
        tvView.setLayoutParams(lp);
        tvView.invalidate();
    }


}
