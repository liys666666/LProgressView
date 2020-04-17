package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        progressRadius = typedArray.getDimension(R.styleable.LineProgressView_progress_radius, 0);
    }

    @Override
    public void init() {
//        LinearGradient gradient =new LinearGradient(0, 0, 0, height, new int[]{Color.BLUE, Color.GRAY}, null, Shader.TileMode.CLAMP);  //参数一为渐变起
//        outPaint.setShader(gradient);
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
        double progressLength = progress/maxProgress*(width-lightSize);

        //绘制区域
        int topIn = (height-sizeIn)/2;
        int topOut = (height-sizeOut)/2;
        RectF rectFIn = new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn);
        RectF rectFOut = new RectF(lightSize, topOut, (int)progressLength, topOut+sizeOut);
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
        pathStroke.addRoundRect(rectFIn, floatsIn, Path.Direction.CW);
        pathOut.op(pathIn, Path.Op.INTERSECT); //交集

        canvas.drawPath(pathLight, lightPaint);
        canvas.drawPath(pathIn, inPaint);
        if(lightShow){
            canvas.drawPath(pathOut, outPaint);
        }
        if(strokeShow){
            canvas.drawPath(pathStroke, strokePaint);
        }
    }

}


//        if(isRadius){
//            pathIn.addRoundRect(new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
//            pathOut.addRoundRect(new RectF(lightSize, topOut, width/2-lightSize, topOut+sizeOut), new float[]{radius, radius, progressRadius, progressRadius, progressRadius, progressRadius, radius, radius}, Path.Direction.CW);
//            pathLight.addRoundRect(new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
//            pathStroke.addRoundRect(new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn), new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, Path.Direction.CW);
//        }else{
//            pathIn.addRoundRect(new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//            pathOut.addRoundRect(new RectF(lightSize, topOut, width/2-lightSize, topOut+sizeOut), new float[]{leftTopRadius, leftTopRadius, progressRadius, progressRadius, progressRadius, progressRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//            pathLight.addRoundRect(new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//            pathStroke.addRoundRect(new RectF(lightSize, topIn, width-lightSize, topIn+sizeIn), new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius}, Path.Direction.CW);
//        }
