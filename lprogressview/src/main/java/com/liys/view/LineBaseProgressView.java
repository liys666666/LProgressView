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
 * @Description: 线性进度条---基类
 * @Author: liys
 * @CreateDate: 2020/4/13 17:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/13 17:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class LineBaseProgressView extends LBaseProgressView {
    //圆角
    protected float radius;
    protected float leftTopRadius;
    protected float leftBottomRadius;
    protected float rightTopRadius;
    protected float rightBottomRadius;
    protected float progressRadius;

    protected boolean isRadius = true; //true使用radius   false使用leftTopRadius...

    public LineBaseProgressView(Context context) {
        this(context, null);
    }

    public LineBaseProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineBaseProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.LineBaseProgressView));
    }

    /**
     * 获取属性
     * @param typedArray
     */
    private void initAttrs(TypedArray typedArray){
        radius = typedArray.getDimension(R.styleable.LineBaseProgressView_radius, -1);
        leftTopRadius = typedArray.getDimension(R.styleable.LineBaseProgressView_left_top_radius, 0);
        leftBottomRadius = typedArray.getDimension(R.styleable.LineBaseProgressView_left_bottom_radius, 0);
        rightTopRadius = typedArray.getDimension(R.styleable.LineBaseProgressView_right_top_radius, 0);
        rightBottomRadius = typedArray.getDimension(R.styleable.LineBaseProgressView_right_bottom_radius, 0);
        progressRadius = typedArray.getDimension(R.styleable.LineBaseProgressView_progress_radius, 0);

        if(radius==-1){ //没有赋值，则自己处理
            isRadius = false;
        }
        if(leftTopRadius==0 || leftBottomRadius==0 || rightTopRadius==0 || rightBottomRadius==0){
            isRadius = true;
        }
    }

    @Override
    public void beforeInit(){
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
                progressPaint.setShader(gradient);
            }
        });
    }
    public void setOutGradient(@ColorInt int... color){
        setOutGradient(true, color);
    }

    public void setOutGradientArray(boolean isHorizontal, @ArrayRes int arrayRes){
        setOutGradient(isHorizontal, getResources().getIntArray(arrayRes));
    }

    public float getLeftTopRadius() {
        return leftTopRadius;
    }

    public void setLeftTopRadius(float leftTopRadius) {
        this.leftTopRadius = leftTopRadius;
    }

    public float getLeftBottomRadius() {
        return leftBottomRadius;
    }

    public void setLeftBottomRadius(float leftBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
    }

    public float getRightTopRadius() {
        return rightTopRadius;
    }

    public void setRightTopRadius(float rightTopRadius) {
        this.rightTopRadius = rightTopRadius;
    }

    public float getRightBottomRadius() {
        return rightBottomRadius;
    }

    public void setRightBottomRadius(float rightBottomRadius) {
        this.rightBottomRadius = rightBottomRadius;
    }

    public float getProgressRadius() {
        return progressRadius;
    }

    public void setProgressRadius(float progressRadius) {
        this.progressRadius = progressRadius;
    }

    public boolean isRadius() {
        return isRadius;
    }

    public void setRadius(boolean radius) {
        isRadius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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
