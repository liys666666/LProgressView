package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

/**
 * @Description: 圆弧进度条
 * @Author: liys
 * @CreateDate: 2020/4/23 13:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 13:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ArcProView extends SquareProView {

    protected int startAngle = 0; //开始角度
    protected int drawAngle = 360; //需要绘制的角度
    protected int currentAngle = 0; //当前角度


    public ArcProView(Context context) {
        this(context, null);
    }

    public ArcProView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcProView);
        startAngle = typedArray.getInteger(R.styleable.ArcProView_arc_start_angle, 0);
        drawAngle = typedArray.getInteger(R.styleable.ArcProView_arc_draw_angle, 360);

    }

    @Override
    public void init() {
        blankSpace = dp2px(4);
        if(progressSize==height){
            progressSize = dp2px(10);
        }
        blankSpace = dp2px(2);
        refreshLight();

        progressPaint.setStrokeWidth(progressSize); //大小
        progressPaint.setStrokeCap(Paint.Cap.ROUND); // 结束位置圆角
        progressPaint.setStyle(Paint.Style.STROKE); //空心样式

        progressBgPaint.setStrokeWidth(progressSize); //大小
        progressBgPaint.setStrokeCap(Paint.Cap.ROUND); // 结束位置圆角
        progressBgPaint.setStyle(Paint.Style.STROKE); //空心样式
    }

    @Override
    public void setOutGradient(final boolean isProDirection, @ColorInt final int... colors){
        post(new Runnable() {
            @Override
            public void run() {
                int[] colorResArr = new int[colors.length];
                for (int i = 0; i < colors.length; i++) {
                    colorResArr[i] = colors[i];
                }
                SweepGradient gradient = new SweepGradient(width/2, height/2, colorResArr, null);
                progressPaint.setShader(gradient);
            }
        });
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        //计算角度 progress/maxProgress = currentAngle/drawAngle;
        currentAngle = (int)(progress * drawAngle / maxProgress);

        int r = progressSize / 2; //圆弧的一半
        RectF rectF = new RectF(r+ blankSpace, r+ blankSpace, width - r- blankSpace, height - r- blankSpace);
//        drawLightCircle(canvas, rectF);
        drawInCircle(canvas, rectF);
        drawOutCircle(canvas, rectF);
        drawText(canvas);
    }

    //发光区域
    private void drawLightCircle(Canvas canvas, RectF rectF) {
        canvas.drawArc(rectF, startAngle, drawAngle, false, lightPaint);
    }

    //内圆弧
    protected void drawInCircle(Canvas canvas, RectF rectF) {
        canvas.drawArc(rectF, startAngle, drawAngle, false, progressBgPaint);
    }

    //外圆弧
    protected void drawOutCircle(Canvas canvas, RectF rectF) {
        if (currentAngle > drawAngle) {
            currentAngle = drawAngle;
        }
        canvas.drawArc(rectF, startAngle, currentAngle, false, progressPaint);
    }

//>>>>>>>>>>>>>>>>>>>>>>对应 set get方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getDrawAngle() {
        return drawAngle;
    }

    public void setDrawAngle(int drawAngle) {
        this.drawAngle = drawAngle;
        invalidate();
    }

    public int getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(int currentAngle) {
        this.currentAngle = currentAngle;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }
}
