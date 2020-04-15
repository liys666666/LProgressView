package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
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

    protected Path pathIn = new Path();
    protected Path pathOut = new Path();
    protected float radius;
    protected float leftTopRadius;
    protected float leftBottomRadius;
    protected float rightTopRadius;
    protected float rightBottomRadius;

    protected boolean isRadius = false; //true使用radius   false使用leftTopRadius...

    public LineProgressView(Context context) {
        this(context, null);
    }

    public LineProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.LineProgressView));
        init();
    }

    /**
     * 获取属性
     * @param typedArray
     */
    private void initAttrs(TypedArray typedArray){
        radius = typedArray.getDimension(R.styleable.LineProgressView_radius, 0);
        leftTopRadius = typedArray.getDimension(R.styleable.LineProgressView_left_top_radius, 0);
        leftBottomRadius = typedArray.getDimension(R.styleable.LineProgressView_left_bottom_radius, 0);
        rightTopRadius = typedArray.getDimension(R.styleable.LineProgressView_right_top_radius, 0);
        rightBottomRadius = typedArray.getDimension(R.styleable.LineProgressView_right_bottom_radius, 0);
        if(radius!=0){ //没有赋值， 则使用4个角处理
            isRadius = true;
        }
    }

    @Override
    public void init() {

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathIn.reset();
        pathOut.reset();

        if(isRadius){
            pathIn.addRoundRect(new RectF(0, 0, width, height), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
            pathOut.addRoundRect(new RectF(0, 0, width/2, height), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
        }else{
            pathIn.addRoundRect(new RectF(0, 0, width, height), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
            pathOut.addRoundRect(new RectF(0, 0, width/2, height), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
        }

        canvas.drawPath(pathIn, paintIn);
        canvas.drawPath(pathOut, paintOut);
    }
}
