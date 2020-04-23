package com.liys.view;

import android.content.Context;
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
 * @CreateDate: 2020/4/16 12:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/16 12:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestView extends BaseProView {
    protected Path pathOut = new Path();

    Paint paint = new Paint();


    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        setLayerType(LAYER_TYPE_SOFTWARE, null); //禁用硬件加速
    }

    boolean isRadius = true;
    int radius = 20;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathOut.reset();

        //进度条当前长度
        double progressLength = progress/maxProgress*(width-blankSpace);

        //绘制区域
        int top = (height- progressSize)/2;
        RectF rectFIn = new RectF(blankSpace, top, width-blankSpace, top+ progressSize);
//        RectF rectFOut = new RectF(blankSpace, top, (int)progressLength, top+ progressSize);
        RectF rectFOut = new RectF(0, 0, width/2, height);
        RectF rectFStroke = new RectF(blankSpace+strokeWidth/2, top+strokeWidth/2, width-blankSpace-strokeWidth/2, top+progressSize-strokeWidth/2);
        //圆角
        float[] floatsOut = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        pathOut.addRoundRect(rectFOut, floatsOut, Path.Direction.CW);
        canvas.drawPath(pathOut, progressPaint);

    }
}
