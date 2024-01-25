package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

import androidx.annotation.ColorInt;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/13 17:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/13 17:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseProView extends View{

    protected Context context;

    //宽高
    protected int width;
    protected int height;
    //进度条和背景
    protected int progressSize;
    protected int progressColorBackground;
    protected int progressColor;

    //文字
    protected String text = ""; //当前 百分比
    protected int textSize;  //字体大小
    protected int textColor;  //字体大小
    protected boolean textShow; //是否显示文字
    protected int textDecimalNum; //保留小数 位数

    //发光
    protected int lightColor;
    protected boolean lightShow;

    //边框
    protected int strokeWidth;
    protected int strokeColor;
    protected boolean strokeShow;

    //画笔
    protected Paint progressBgPaint = new Paint();
    protected Paint progressPaint = new Paint();
    protected Paint textPaint = new Paint();
    protected Paint lightPaint = new Paint(); //发光 画笔
    protected Paint strokePaint = new Paint(); //边框 边框画笔

    protected double maxProgress; //总数
    protected double progress; //当前进度

    protected int blankSpace; //空白距离/发光和大小

    public BaseProView(Context context) {
        this(context, null);
    }

    public BaseProView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initBaseAttrs(attrs);
        initBaseView();
//        post(new Runnable() {
//            @Override
//            public void run() {
//                width = getMeasuredWidth();
//                height = getMeasuredHeight();
//                if(progressSize == 0){
//                    progressSize = height;
//                }
//                blankSpace = (height- progressSize)/2;
//                refreshLight();
//                beforeInit();
//                init();
//            }
//        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        if(progressSize == 0){
            progressSize = height;
        }
        blankSpace = (height- progressSize)/2;
        refreshLight();
        beforeInit();
        init();
    }

    public void beforeInit(){}
    public abstract void init();

    @SuppressLint("CustomViewStyleable")
    private void initBaseAttrs(AttributeSet attrs){
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseProgressView);
        maxProgress = typedArray.getInteger(R.styleable.BaseProgressView_progress_max, 100);
        progress = typedArray.getInteger(R.styleable.BaseProgressView_progress_value, 0);

        progressSize = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_progress_size, 0);

        progressColorBackground = typedArray.getColor(R.styleable.BaseProgressView_progress_color_background, Color.GRAY);
        progressColor = typedArray.getColor(R.styleable.BaseProgressView_progress_color, Color.YELLOW);

        textSize = typedArray.getDimensionPixelSize(R.styleable.BaseProgressView_text_size, sp2px(10));
        textColor = typedArray.getColor(R.styleable.BaseProgressView_text_color, Color.WHITE);
        textShow = typedArray.getBoolean(R.styleable.BaseProgressView_text_show, false);
        textDecimalNum = typedArray.getInt(R.styleable.BaseProgressView_text_decimal_num, 0);

        lightColor = typedArray.getColor(R.styleable.BaseProgressView_light_color, Color.WHITE);
        lightShow = typedArray.getBoolean(R.styleable.BaseProgressView_light_show, false);

        strokeColor = typedArray.getColor(R.styleable.BaseProgressView_stroke_color, Color.WHITE);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_stroke_width, dp2px(1));
        strokeShow = typedArray.getBoolean(R.styleable.BaseProgressView_stroke_show, false);

        typedArray.recycle();

        refreshText();
    }

    private void initBaseView(){
        progressBgPaint.setAntiAlias(true);
        progressBgPaint.setColor(progressColorBackground);

        progressPaint.setAntiAlias(true);
        progressPaint.setColor(progressColor);

        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        //发光
        lightPaint.setAntiAlias(true);
        lightPaint.setColor(lightColor);

//        if(lightShow){
//            BlurMaskFilter lightMaskFilter = new BlurMaskFilter(blankSpace, BlurMaskFilter.Blur.SOLID);
//            lightPaint.setMaskFilter(lightMaskFilter);
//            progressPaint.setMaskFilter(lightMaskFilter);
//            setLayerType(LAYER_TYPE_SOFTWARE, null); //禁用硬件加速
//        }

        //边框
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(strokeColor);
        strokePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 刷新发光参数
     */
    protected void refreshLight(){
        if(lightShow && blankSpace>0){
            BlurMaskFilter progressMaskFilter = new BlurMaskFilter(blankSpace, BlurMaskFilter.Blur.SOLID);
            BlurMaskFilter lightMaskFilter = new BlurMaskFilter(blankSpace, BlurMaskFilter.Blur.OUTER);
            lightPaint.setMaskFilter(lightMaskFilter);
            progressPaint.setMaskFilter(progressMaskFilter);
            setLayerType(LAYER_TYPE_SOFTWARE, null); //禁用硬件加速
        }
    }

    /**
     * 设置渐变
     * @param isProDirection 是否顺着进度条前进方向 渐变
     * @param colors 颜色数组
     */
    public void setOutGradient(boolean isProDirection, @ColorInt int... colors){}
    public void setOutGradient(@ColorInt int... colors){
        setOutGradient(true, colors);
    }

    /**
     * 获取文字区域
     * @param text
     * @return
     */
    protected Rect getTextRect(String text){
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    protected int getBaseline(Paint paint){
//        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
//        // 获取文字的高
//        int fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
//        // 计算基线到中心点的距离
//        int offY = fontTotalHeight / 2 - fontMetrics.bottom;
//        // 计算基线位置
//        int baseline = (getMeasuredHeight() + fontTotalHeight) / 2 - offY;
        return getBaseline(paint, getMeasuredHeight());
    }

    protected int getBaseline(Paint paint, int totalHeight){
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 获取文字的高
        int fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算基线到中心点的距离
        int offY = fontTotalHeight / 2 - fontMetrics.bottom;
        // 计算基线位置
        int baseline = (totalHeight + fontTotalHeight) / 2 - offY;
        return baseline;
    }

    public void setProgress(double progress) {
        this.progress = progress;
        refreshText();
    }

    public void setMaxProgress(double maxProgress) {
        this.maxProgress = maxProgress;
        refreshText();
    }

    /**
     * 刷新text
     */
    protected void refreshText(){
        text = keepDecimals(progress/maxProgress*100)+"%";
        postInvalidate();
    }

    /**
     * 保留小数
     * @param value
     * @return
     */
    protected String keepDecimals(double value){
        if(textDecimalNum==0){
            return (int)value + "";
        }
        String format = "";
        for (int i = 0; i < textDecimalNum; i++) {
            if(i==0){
                format = "0.0";
            }else{
                format = format+"0";
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(value);
    }

    public int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

//    对应set get方法
    public int getProgressSize() {
        return progressSize;
    }

    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
    }


    public int getProgressColorBackground() {
        return progressColorBackground;
    }

    public void setProgressColorBackground(int progressColorBackground) {
        this.progressColorBackground = progressColorBackground;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isTextShow() {
        return textShow;
    }

    public void setTextShow(boolean textShow) {
        this.textShow = textShow;
    }

    public int getTextDecimalNum() {
        return textDecimalNum;
    }

    public void setTextDecimalNum(int textDecimalNum) {
        this.textDecimalNum = textDecimalNum;
    }

    public int getLightColor() {
        return lightColor;
    }

    public void setLightColor(int lightColor) {
        this.lightColor = lightColor;
    }

    public boolean isLightShow() {
        return lightShow;
    }

    public void setLightShow(boolean lightShow) {
        this.lightShow = lightShow;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public boolean isStrokeShow() {
        return strokeShow;
    }

    public void setStrokeShow(boolean strokeShow) {
        this.strokeShow = strokeShow;
    }

    public double getMaxProgress() {
        return maxProgress;
    }

    public double getProgress() {
        return progress;
    }

    public Paint getProgressBgPaint() {
        return progressBgPaint;
    }

    public void setProgressBgPaint(Paint progressBgPaint) {
        this.progressBgPaint = progressBgPaint;
    }

    public Paint getProgressPaint() {
        return progressPaint;
    }

    public void setProgressPaint(Paint progressPaint) {
        this.progressPaint = progressPaint;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public Paint getLightPaint() {
        return lightPaint;
    }

    public void setLightPaint(Paint lightPaint) {
        this.lightPaint = lightPaint;
    }

    public Paint getStrokePaint() {
        return strokePaint;
    }

    public void setStrokePaint(Paint strokePaint) {
        this.strokePaint = strokePaint;
    }
}
