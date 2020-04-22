package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/22 17:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/22 17:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineCentreProgressView extends LBaseProgressView{

    protected Paint boxPaint = new Paint();
    protected int boxWidth;

    public LineCentreProgressView(Context context) {
        this(context, null);
    }

    public LineCentreProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineCentreProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        boxPaint.setAntiAlias(true);
        boxPaint.setColor(progressPaint.getColor());
        boxWidth = height*3/2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //1. 获取当前进度
        int outWidth = (int)(progress/maxProgress*width); //计算当前进度距离
        if(outWidth >= width - boxWidth){
            outWidth = (width - boxWidth);
        }

        int top = (height-progressSize)/2;
        //进度条当前长度
        canvas.drawRoundRect(new RectF(0, top, width, top+progressSize), progressSize/2, progressSize/2, progressBgPaint);
        canvas.drawRoundRect(new RectF(0, top, outWidth+boxWidth/2, top+progressSize), progressSize/2, progressSize/2, progressPaint);

        drawBox(canvas, outWidth);
        drawText(canvas, outWidth);
    }

    /**
     * @param canvas
     * @param left 左边距离
     */
    public void drawBox(Canvas canvas, int left){
        RectF rectF = new RectF(left, 0, left+boxWidth, height); // 设置个新的长方形
        canvas.drawRoundRect(rectF, height/2, height/2, boxPaint); //第二个参数是x半径，第三个参数是y半径
    }

    public void drawText(Canvas canvas, int left){
        canvas.drawText(text, left+boxWidth/2-getTextRect(text).width()/2, getBaseline(textPaint), textPaint);
    }
}
