package com.liys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
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
    protected int sizeIn;
    protected int sizeOut;
    protected int colorIn;
    protected int colorOut;

    //文字
    protected String text = ""; //当前 百分比
    protected int textSize;  //字体大小
    protected int textColor;  //字体大小

    //发光
    protected int lightColor;
    protected int lightSize;
    protected boolean lightShow;

    //边框
    protected int strokeWidth;
    protected int strokeColor;
    protected boolean strokeShow;

    //画笔
    protected Paint inPaint = new Paint();
    protected Paint outPaint = new Paint();
    protected Paint textPaint = new Paint();
    protected Paint lightPaint = new Paint(); //发光 画笔
    protected Paint strokePaint = new Paint(); //边框 边框画笔

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
        initBaseAttrs(attrs);
        initBaseView();
        post(new Runnable() {
            @Override
            public void run() {
                width = getMeasuredWidth();
                height = getMeasuredHeight();
                if(sizeIn ==0){
                    sizeIn = height;
                }
                if(sizeOut ==0){
                    sizeOut = height;
                }
                init();
            }
        });
    }

    public abstract void init();

    @SuppressLint("CustomViewStyleable")
    private void initBaseAttrs(AttributeSet attrs){
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseProgressView);
        maxProgress = typedArray.getInteger(R.styleable.BaseProgressView_max_progress, 100);
        progress = typedArray.getInteger(R.styleable.BaseProgressView_progress, 0);

        sizeIn = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_size_in, 0);
        sizeOut = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_size_out, 0);

        colorIn = typedArray.getColor(R.styleable.BaseProgressView_color_in, Color.WHITE);
        colorOut = typedArray.getColor(R.styleable.BaseProgressView_color_out, Color.YELLOW);

        text = typedArray.getString(R.styleable.BaseProgressView_text);
        textSize = typedArray.getDimensionPixelSize(R.styleable.BaseProgressView_text_size, sp2px(10));
        textColor = typedArray.getColor(R.styleable.BaseProgressView_text_color, Color.WHITE);

        lightColor = typedArray.getColor(R.styleable.BaseProgressView_light_color, Color.WHITE);
        lightSize = typedArray.getDimensionPixelSize(R.styleable.BaseProgressView_light_size, dp2px(8));
        lightShow = typedArray.getBoolean(R.styleable.BaseProgressView_light_show, false);
        if(!lightShow){
            lightSize=0;
        }

        strokeColor = typedArray.getColor(R.styleable.BaseProgressView_stroke_color, Color.WHITE);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.BaseProgressView_stroke_size, dp2px(0.5f));
        strokeShow = typedArray.getBoolean(R.styleable.BaseProgressView_light_show, false);

        typedArray.recycle();
    }

    private void initBaseView(){
        inPaint.setAntiAlias(true);
        inPaint.setColor(colorIn);

        outPaint.setAntiAlias(true);
        outPaint.setColor(colorOut);

        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        //发光
        lightPaint.setAntiAlias(true);
        lightPaint.setColor(lightColor);

        BlurMaskFilter lightMaskFilter = new BlurMaskFilter(lightSize, BlurMaskFilter.Blur.SOLID);
        lightPaint.setMaskFilter(lightMaskFilter);
        outPaint.setMaskFilter(lightMaskFilter);
        setLayerType(LAYER_TYPE_SOFTWARE, null); //禁用硬件加速

        //边框
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(strokeColor);
        strokePaint.setStyle(Paint.Style.STROKE);
    }


    protected int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
    protected int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

//    对应set get方法
    public int getSizeIn() {
        return sizeIn;
    }

    public void setSizeIn(int sizeIn) {
        this.sizeIn = sizeIn;
    }

    public int getSizeOut() {
        return sizeOut;
    }

    public void setSizeOut(int sizeOut) {
        this.sizeOut = sizeOut;
    }

    public int getColorIn() {
        return colorIn;
    }

    public void setColorIn(int colorIn) {
        this.colorIn = colorIn;
    }

    public int getColorOut() {
        return colorOut;
    }

    public void setColorOut(int colorOut) {
        this.colorOut = colorOut;
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
