package com.example.administrator.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    Paint paint=new Paint();
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(32);
        String text="测试";
        Paint.FontMetrics fontMetrics=paint.getFontMetrics();
        int baseLineY= (int) (getHeight()/2-(fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.top);
        canvas.drawText(text,getWidth()/2,baseLineY,paint);
    }
}
