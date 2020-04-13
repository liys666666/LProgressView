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

    Path path = new Path();

    public LineProgressView(Context context) {
        this(context, null);
    }

    public LineProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseProgressView);
        maxProgress = typedArray.getInteger(R.styleable.BaseProgressView_max_progress, 100);
    }

    @Override
    public void init() {

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(new RectF(0, 0, width, height), height/2, height/2, paintIn);

        path.reset();
        path.addRoundRect(new RectF(0, 0, width/2, height), new float[]{height/2, height/2, 0, 0, 0, 0, height/2, height/2}, Path.Direction.CW);
        canvas.drawPath(path, paintOut);
//        canvas.drawRoundRect(new RectF(0, 0, width/2, height), height/2, height/4, paintOut);
    }
}
