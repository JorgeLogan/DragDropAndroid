package com.example.actividad2_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MiPaint extends View {
    Canvas miCanvas;

    // Constructores pedidos
    public MiPaint(Context context) {
        super(context);
    }

    public MiPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MiPaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MiPaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint myPaint = new Paint();
        myPaint.setColor(color.toArgb());
        myPaint.setStrokeWidth(10);
        myPaint.setStyle(Paint.Style.FILL);
        miCanvas.drawRect(posX1, posY1, posX2, posY2, myPaint);
        Log.d("Pruebas", "Pintado rectangulo");
    }

    private float posX1, posX2, posY1, posY2, radio;
    private Color color;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pintarRectangulo(float posX1, float posX2, float posY1, float posY2, Color color){
        this.posX1 = posX1;
        this.posX2 = posX2;
        this.posY1 = posY1;
        this.posY2 = posY2;
        this.color = color;
        this.refreshDrawableState();
    }
}