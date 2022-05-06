package com.example.actividad2_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class Lienzo extends androidx.appcompat.widget.AppCompatImageView {
    public Lienzo(Context context) {
        super(context);
    }

    public Lienzo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Lienzo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pincel = new Paint();
        pincel.setStrokeWidth(10);
        pincel.setColor(Color.RED);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,0, getWidth(), getHeight(), pincel);
    }
}
