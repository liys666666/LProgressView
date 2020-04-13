package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/13 17:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/13 17:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class LBaseProgressView extends View{

    protected Context context;

    //宽高
    protected int width;
    protected int height;
    //进度条和背景
    protected int lineSizeIn;
    protected int lineSizeOut;
    protected int lineColorIn;
    protected int lineColorOut;

    //文字
    protected String text = ""; //当前 百分比
    protected int textSize;  //字体大小
    protected int textColor;  //字体大小

    protected Paint paintIn = new Paint();
    protected Paint paintOut = new Paint();
    protected Paint paintText = new Paint();

    protected double maxProgress; //总数
    protected double progress; //当前进度

    public LBaseProgressView(Context context) {
        this(context, null);
    }

    public LBaseProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LBaseProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
        initView();
        post(new Runnable() {
            @Override
            public void run() {
                width = getMeasuredWidth();
                height = getMeasuredHeight();
                if(lineSizeIn==0){
                    lineSizeIn = height;
                }
                if(lineSizeOut==0){
                    lineSizeOut = height;
                }
                init();
            }
        });
    }

    public abstract void init();

    @SuppressLint("CustomViewStyleable")
    private void initAttrs(AttributeSet attrs){
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseProgressView);
        maxProgress = typedArray.getInteger(R.styleable.BaseProgressView_max_progress, 100);
        progress = typedArray.getInteger(R.styleable.BaseProgressView_progress, 0);

        lineSizeIn = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_line_size_in, 0);
        lineSizeOut = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_line_size_out, 0);

        lineColorIn = typedArray.getColor(R.styleable.BaseProgressView_line_color_in, Color.WHITE);
        lineColorOut = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_line_color_out, Color.YELLOW);

        text = typedArray.getString(R.styleable.BaseProgressView_text);
        textSize = typedArray.getDimensionPixelSize(R.styleable.BaseProgressView_text_size, sp2px(10));
        textColor = typedArray.getColor(R.styleable.BaseProgressView_text_color, Color.WHITE);

        typedArray.recycle();
    }

    private void initView(){
        paintIn.setAntiAlias(true);
        paintIn.setColor(lineColorIn);

        paintOut.setAntiAlias(true);
        paintOut.setColor(lineColorOut);

        paintText.setAntiAlias(true);
        paintText.setColor(textColor);
        paintText.setTextSize(textSize);
    }


    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

//    对应set get方法
    public int getLineSizeIn() {
        return lineSizeIn;
    }

    public void setLineSizeIn(int lineSizeIn) {
        this.lineSizeIn = lineSizeIn;
    }

    public int getLineSizeOut() {
        return lineSizeOut;
    }

    public void setLineSizeOut(int lineSizeOut) {
        this.lineSizeOut = lineSizeOut;
    }

    public int getLineColorIn() {
        return lineColorIn;
    }

    public void setLineColorIn(int lineColorIn) {
        this.lineColorIn = lineColorIn;
    }

    public int getLineColorOut() {
        return lineColorOut;
    }

    public void setLineColorOut(int lineColorOut) {
        this.lineColorOut = lineColorOut;
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

    public double getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(double maxProgress) {
        this.maxProgress = maxProgress;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
