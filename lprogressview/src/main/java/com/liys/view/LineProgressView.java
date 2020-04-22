package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/13 17:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/13 17:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineProgressView extends LBaseProgressView {
    //画笔
    protected Path pathIn = new Path();
    protected Path pathOut = new Path();
    protected Path pathLight = new Path();
    protected Path pathStroke = new Path();
    //圆角
    protected float radius;
    protected float leftTopRadius;
    protected float leftBottomRadius;
    protected float rightTopRadius;
    protected float rightBottomRadius;
    protected float progressRadius;

    protected boolean isRadius = true; //true使用radius   false使用leftTopRadius...

    //文字偏移量(进度条--左边距离)
    protected int offTextX;

    public LineProgressView(Context context) {
        this(context, null);
    }

    public LineProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.LineProgressView));
    }

    /**
     * 获取属性
     * @param typedArray
     */
    private void initAttrs(TypedArray typedArray){
        radius = typedArray.getDimension(R.styleable.LineProgressView_radius, -1);
        leftTopRadius = typedArray.getDimension(R.styleable.LineProgressView_left_top_radius, 0);
        leftBottomRadius = typedArray.getDimension(R.styleable.LineProgressView_left_bottom_radius, 0);
        rightTopRadius = typedArray.getDimension(R.styleable.LineProgressView_right_top_radius, 0);
        rightBottomRadius = typedArray.getDimension(R.styleable.LineProgressView_right_bottom_radius, 0);
        progressRadius = typedArray.getDimension(R.styleable.LineProgressView_progress_radius, 0);

        if(radius==-1){ //没有赋值，则自己处理
            isRadius = false;
        }
        if(leftTopRadius==0 || leftBottomRadius==0 || rightTopRadius==0 || rightBottomRadius==0){
            isRadius = true;
        }
    }

    @Override
    public void init() {
        offTextX = dp2px(10);
        if(isRadius && radius==-1){
            radius = progressSize /2;
        }
    }


    /**
     * 设置渐变
     * @param isHorizontal 是否水平
     * @param isHorizontal 是否水平渐变  (水平：左到右  垂直：上到下)
     * @param color 颜色数组
     */
    public void setOutGradient(final boolean isHorizontal, final @ColorInt int... color){
        post(new Runnable() {
            @Override
            public void run() {
                int[] colorResArr = new int[color.length];
                for (int i = 0; i < color.length; i++) {
                    colorResArr[i] = color[i];
                }
                LinearGradient gradient;
                int topOut = (height- progressSize)/2;
                if(isHorizontal){ //水平
                    gradient =new LinearGradient(0, 0, width, 0, colorResArr, null, Shader.TileMode.CLAMP);  //参数一为渐变起
                }else{
                    gradient =new LinearGradient(0, topOut, 0, topOut+ progressSize, colorResArr, null, Shader.TileMode.CLAMP);  //参数一为渐变起
                }
                outPaint.setShader(gradient);
            }
        });
    }
    public void setOutGradient(@ColorInt int... color){
        setOutGradient(true, color);
    }

    public void setOutGradientArray(boolean isHorizontal, @ArrayRes int arrayRes){
        setOutGradient(isHorizontal, getResources().getIntArray(arrayRes));
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
        //圆角
        float[] floatsIn;
        float[] floatsOut;
        if(isRadius){
            floatsIn = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
            floatsOut = new float[]{radius, radius, progressRadius, progressRadius, progressRadius, progressRadius, radius, radius};
        }else{
            floatsIn = new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
            floatsOut = new float[]{leftTopRadius, leftTopRadius, progressRadius, progressRadius, progressRadius, progressRadius, leftBottomRadius, leftBottomRadius};
        }
        pathIn.addRoundRect(rectFIn, floatsIn, Path.Direction.CW);
        pathOut.addRoundRect(rectFOut, floatsOut, Path.Direction.CW);
        pathLight.addRoundRect(rectFIn, floatsIn, Path.Direction.CW);
        pathStroke.addRoundRect(rectFStroke, floatsIn, Path.Direction.CW);
        pathOut.op(pathIn, Path.Op.INTERSECT); //交集

        if(lightShow){
            canvas.drawPath(pathLight, lightPaint);
        }
        canvas.drawPath(pathIn, inPaint);
        canvas.drawPath(pathOut, outPaint);

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
