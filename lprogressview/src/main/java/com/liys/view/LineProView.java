package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/23 16:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 16:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineProView extends LineBaseProView {
    //画笔
    protected Path pathLight = new Path();
    protected Path pathStroke = new Path();


    //文字偏移量(进度条--左边距离)
    protected int offTextX;

    public LineProView(Context context) {
        this(context, null);
    }

    public LineProView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void init() {
        offTextX = dp2px(10);
        if(isRadius && radius==-1){
            radius = progressSize /2;
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathIn.reset();
        pathOut.reset();
        pathLight.reset();
        pathStroke.reset();

        //进度条当前长度
        double progressLength = progress/maxProgress*(width-blankSpace);

        //绘制区域
        int top = (height- progressSize)/2;

        RectF rectFIn = new RectF(blankSpace, top, width-blankSpace, top+ progressSize);
        RectF rectFOut = new RectF(blankSpace, top, (int)progressLength, top+ progressSize);
        RectF rectFStroke = new RectF(blankSpace+strokeWidth/2, top+strokeWidth/2, width-blankSpace-strokeWidth/2, top+progressSize-strokeWidth/2);
        refreshRadius();
        pathIn.addRoundRect(rectFIn, floatsIn, Path.Direction.CW);
        pathOut.addRoundRect(rectFOut, floatsOut, Path.Direction.CW);

        pathLight.addRoundRect(rectFIn, floatsIn, Path.Direction.CW);
        pathStroke.addRoundRect(rectFStroke, floatsIn, Path.Direction.CW);
        pathOut.op(pathIn, Path.Op.INTERSECT); //交集

        if(lightShow){
            canvas.drawPath(pathLight, lightPaint);
        }
        canvas.drawPath(pathIn, progressBgPaint);
        canvas.drawPath(pathOut, progressPaint);

        if(strokeShow){
            canvas.drawPath(pathStroke, strokePaint);
        }

        drawText(canvas, progressLength);
    }

    /**
     * 绘制文字
     * @param canvas
     * @return
     */
    protected void drawText(Canvas canvas, double progressLength){
        if(textShow){
            int startX = dp2px(10)+blankSpace;
            int endX = startX + getTextRect(text).width()+offTextX; //文字结束位置
            if(endX < progressLength){
                startX = (int) progressLength-getTextRect(text).width()-offTextX;
            }
            canvas.drawText(text, startX, getBaseline(textPaint), textPaint);
        }
    }

    public int getOffTextX() {
        return offTextX;
    }

    public void setOffTextX(int offTextX) {
        this.offTextX = offTextX;
    }

}


//        if(isRadius){
//            pathIn.addRoundRect(new RectF(blankSpace, topIn, width-blankSpace, topIn+progressSize), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
//            pathOut.addRoundRect(new RectF(blankSpace, topOut, width/2-blankSpace, topOut+progressSize), new float[]{radius, radius, progressRadius, progressRadius, progressRadius, progressRadius, radius, radius}, Path.Direction.CW);
//            pathLight.addRoundRect(new RectF(blankSpace, topIn, width-blankSpace, topIn+progressSize), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
//            pathStroke.addRoundRect(new RectF(blankSpace, topIn, width-blankSpace, topIn+progressSize), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
//        }else{
//            pathIn.addRoundRect(new RectF(blankSpace, topIn, width-blankSpace, topIn+progressSize), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//            pathOut.addRoundRect(new RectF(blankSpace, topOut, width/2-blankSpace, topOut+progressSize), new float[]{leftTopRadius, leftTopRadius, progressRadius, progressRadius, progressRadius, progressRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//            pathLight.addRoundRect(new RectF(blankSpace, topIn, width-blankSpace, topIn+progressSize), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//            pathStroke.addRoundRect(new RectF(blankSpace, topIn, width-blankSpace, topIn+progressSize), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//        }
