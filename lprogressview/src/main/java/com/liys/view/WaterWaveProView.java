package com.liys.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @Description: 水波--进度条
 * @Author: liys
 * @CreateDate: 2020/4/23 13:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 13:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WaterWaveProView extends SquareProView {

    protected Path path = new Path();

    protected int waveWidth; //水波长
    protected int waveHeight; //水波高度
    protected int waveSpeed;//水波速度

    protected float startX = 0; //

    public WaterWaveProView(Context context) {
        this(context, null);
    }

    public WaterWaveProView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterWaveProView);
        waveWidth = typedArray.getInteger(R.styleable.WaterWaveProView_water_wave_width, -1);
        waveHeight = typedArray.getInteger(R.styleable.WaterWaveProView_water_wave_height, -1);
        waveSpeed = typedArray.getInteger(R.styleable.WaterWaveProView_water_wave_speed, -1);

//        mInColor = Color.parseColor("#69B655");
//        mWaterColor = Color.parseColor("#0AA328");
    }

    @Override
    public void init() {
        //设置模式
        progressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int defaultWaterHeight = 5; //默认水波高度 单位sp
        //5. 确定波长和波高
        waveWidth = width/4;
        waveHeight = sp2px(defaultWaterHeight);
        setMeasuredDimension(width, height);
        startAnimator();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //1. 绘制贝塞尔曲线
        drawBessel(width, startX, (int)(height*(1-progress/maxProgress)), waveWidth, waveHeight, path, progressPaint);
        canvas.drawPath(path, progressPaint);
        //2. 绘制圆形bitmap
        canvas.drawBitmap(createCircleBitmap(width/2), null, new Rect(0,0,width,height), progressPaint);
        //3. 绘制文字
        drawText(canvas);
    }

    /**
     * 绘制贝塞尔曲线
     * @param width 总共需要绘制的长度
     * @param startX 开始X点坐标(-2*startX 到 0 之间) 左右预留一个波长
     * @param startY 开始Y坐标
     * @param waveWidth 波长(半个周期)
     * @param waveHeight 波高
     * @param path
     * @param paint 画笔
     */
    private void drawBessel(float width, float startX, float startY, float waveWidth, float waveHeight, Path path, Paint paint){
        //Android贝塞尔曲线
        // 二阶写法：rQuadTo(float dx1, float dy1, float dx2, float dy2) 相对上一个起点的坐标
        path.reset();
        int currentWidth = 0; //当前已经绘制的宽度
        path.moveTo(startX,startY); //画笔位置
        while (currentWidth <= width + 4*waveWidth && waveWidth>0){
            path.rQuadTo(waveWidth, -waveHeight, 2*waveWidth, 0);
            path.rQuadTo(waveWidth, waveHeight, 2*waveWidth, 0);
            currentWidth += 2*waveWidth;
        }
        //封闭的区域
        path.lineTo(getWidth()+4*waveWidth,getHeight());
        path.lineTo(0,getHeight());
        path.close();
    }

    private Bitmap createCircleBitmap(int radius){
        Bitmap canvasBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBmp);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(radius, radius, radius, progressBgPaint); //确定位置
        return canvasBmp;
    }

    private void startAnimator(){
        ValueAnimator animator = ValueAnimator.ofFloat(0-4*waveWidth, 0);
        animator.setInterpolator(new LinearInterpolator());//匀速插值器 解决卡顿问题
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setStartX((float) animation.getAnimatedValue());
                invalidate();
            }
        });
        animator.start();
    }


    public int getWaveWidth() {
        return waveWidth;
    }

    public void setWaveWidth(int waveWidth) {
        this.waveWidth = waveWidth;
    }

    public int getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        this.waveHeight = waveHeight;
    }

    public int getWaveSpeed() {
        return waveSpeed;
    }

    public void setWaveSpeed(int waveSpeed) {
        this.waveSpeed = waveSpeed;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }
}
