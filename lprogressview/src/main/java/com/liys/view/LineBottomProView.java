package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/22 17:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/22 17:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineBottomProView extends LineBaseProView {

    protected Paint boxPaint = new Paint();
    protected int boxWidth;
    protected int boxHeight;
    protected int boxRadius;

    public LineBottomProView(Context context) {
        this(context, null);
    }

    public LineBottomProView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineBottomProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineBottomProView);
        boxWidth = typedArray.getDimensionPixelOffset(R.styleable.LineBottomProView_box_width, -1);
        boxHeight = typedArray.getDimensionPixelOffset(R.styleable.LineBottomProView_box_height, -1);
        boxRadius = typedArray.getDimensionPixelOffset(R.styleable.LineBottomProView_box_radius, -1);
    }

    @Override
    public void init() {
        boxPaint.setAntiAlias(true);
        boxPaint.setColor(progressPaint.getColor());

        if(progressSize==height){
            progressSize = height/5*2;
        }

        if(boxHeight==-1){
            boxHeight = height/5*2;
        }

        if(boxWidth==-1){
            boxWidth = boxHeight*2;
        }
        if(boxRadius==-1){
            boxRadius = dp2px(5);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        pathIn.reset();
        pathOut.reset();

        //1. 获取当前进度
        int outWidth = (int)(progress/maxProgress*(width-boxWidth)); //计算当前进度距离
        int top = height-progressSize;
        //进度条当前长度
//        canvas.drawRoundRect(new RectF(boxWidth/2, top, width-boxWidth/2, top+progressSize), radius, radius, progressBgPaint);
//        canvas.drawRoundRect(new RectF(boxWidth/2, top, outWidth+boxWidth/2, top+progressSize), progressRadius, progressRadius, progressPaint);

        refreshRadius();
        pathIn.addRoundRect(new RectF(boxWidth/2, top, width-boxWidth/2, top+progressSize), floatsIn, Path.Direction.CW);
        pathOut.addRoundRect(new RectF(boxWidth/2, top, outWidth+boxWidth/2, top+progressSize), floatsOut, Path.Direction.CW);
        pathOut.op(pathIn, Path.Op.INTERSECT); //交集
        canvas.drawPath(pathIn, progressBgPaint);
        canvas.drawPath(pathOut, progressPaint);

        drawBox(canvas, outWidth);
        drawText(canvas, outWidth);
    }

    /**
     * @param canvas
     * @param left 左边距离
     */
    public void drawBox(Canvas canvas, int left){
        //2.1圆角矩形
        RectF rectF = new RectF(left, 0, left+boxWidth, boxHeight);
        canvas.drawRoundRect(rectF, boxRadius, boxRadius, boxPaint);

        //2.2 画三角形 (绘制这个三角形,你可以绘制任意多边形)
        Path path = new Path();
        path.moveTo(left + boxWidth/2-sp2px(4), boxHeight);// 此点为多边形的起点
        path.lineTo(left + boxWidth/2+sp2px(4), boxHeight);
        path.lineTo(left + boxWidth/2, height-progressSize-2);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, boxPaint);
    }

    public void drawText(Canvas canvas, int left){
        canvas.drawText(text, left+boxWidth/2-getTextRect(text).width()/2, getBaseline(textPaint, boxHeight), textPaint);
    }

    public Paint getBoxPaint() {
        return boxPaint;
    }

    public void setBoxPaint(Paint boxPaint) {
        this.boxPaint = boxPaint;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
        invalidate();
    }

    public int getBoxRadius() {
        return boxRadius;
    }

    public void setBoxRadius(int boxRadius) {
        this.boxRadius = boxRadius;
        invalidate();
    }
}
