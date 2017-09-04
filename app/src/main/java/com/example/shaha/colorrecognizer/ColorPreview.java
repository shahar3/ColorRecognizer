package com.example.shaha.colorrecognizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shaha on 04/09/2017.
 */

public class ColorPreview extends View {
    private Paint paint;

    public ColorPreview(Context context, AttributeSet attr) {
        super(context, attr);

        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    public void setColor(int r,int g,int b){
        paint.setColor(Color.rgb(r,g,b));
        //draw the box
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
    }
}
