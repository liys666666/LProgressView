package com.liys.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Path;
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
public abstract class LineBaseProView extends BaseProView {
    //圆角
    protected float radius;
    protected float leftTopRadius;
    protected float leftBottomRadius;
    protected float rightTopRadius;
    protected float rightBottomRadius;
    protected float progressRadius;

    protected boolean isRadius = true; //true使用radius   false使用leftTopRadius...

    //圆角
    protected float[] floatsIn;
    protected float[] floatsOut;

    protected Path pathIn = new Path();
    protected Path pathOut = new Path();

    public LineBaseProView(Context context) {
        this(context, null);
    }

    public LineBaseProView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineBaseProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.LineBaseProView));
    }

    /**
     * 获取属性
     * @param typedArray
     */
    private void initAttrs(TypedArray typedArray){
        radius = typedArray.getDimension(R.styleable.LineBaseProView_radius, -1);
        leftTopRadius = typedArray.getDimension(R.styleable.LineBaseProView_left_top_radius, 0);
        leftBottomRadius = typedArray.getDimension(R.styleable.LineBaseProView_left_bottom_radius, 0);
        rightTopRadius = typedArray.getDimension(R.styleable.LineBaseProView_right_top_radius, 0);
        rightBottomRadius = typedArray.getDimension(R.styleable.LineBaseProView_right_bottom_radius, 0);
        progressRadius = typedArray.getDimension(R.styleable.LineBaseProView_progress_radius, 0);

        if(radius==-1){ //没有赋值，则自己处理
            isRadius = true;
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

    protected void refreshRadius(){
        if(isRadius){
            floatsIn = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
            floatsOut = new float[]{radius, radius, progressRadius, progressRadius, progressRadius, progressRadius, radius, radius};
        }else{
            floatsIn = new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
            floatsOut = new float[]{leftTopRadius, leftTopRadius, progressRadius, progressRadius, progressRadius, progressRadius, leftBottomRadius, leftBottomRadius};
        }
    }

    /**
     * 设置渐变
     * @param isProDirection 是否水平渐变  (水平：左到右  垂直：上到下)
     * @param colors 颜色数组
     */
    @Override
    public void setOutGradient(final boolean isProDirection, final @ColorInt int... colors){
        post(new Runnable() {
            @Override
            public void run() {
                int[] colorResArr = new int[colors.length];
                for (int i = 0; i < colors.length; i++) {
                    colorResArr[i] = colors[i];
                }
                LinearGradient gradient;
                int topOut = (height- progressSize)/2;
                if(isProDirection){ //水平
                    gradient =new LinearGradient(0, 0, width, 0, colorResArr, null, Shader.TileMode.CLAMP);  //参数一为渐变起
                }else{
                    gradient =new LinearGradient(0, topOut, 0, topOut+ progressSize, colorResArr, null, Shader.TileMode.CLAMP);  //参数一为渐变起
                }
                progressPaint.setShader(gradient);
            }
        });
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
