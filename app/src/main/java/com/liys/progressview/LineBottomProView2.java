package com.liys.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.liys.view.LineBottomProView;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2024/1/25 10:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2024/1/25 10:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineBottomProView2 extends LineBottomProView {

    public LineBottomProView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        pathIn.reset();
        pathOut.reset();

        //1. 获取当前进度
        int outWidth = (int)(progress/maxProgress*(width-boxWidth)); //计算当前进度距离
//        int top = height-progressSize;
        int top = 0;
        //进度条当前长度
//        canvas.drawRoundRect(new RectF(boxWidth/2, top, width-boxWidth/2, top+progressSize), radius, radius, progressBgPaint);
//        canvas.drawRoundRect(new RectF(boxWidth/2, top, outWidth+boxWidth/2, top+progressSize), progressRadius, progressRadius, progressPaint);

        refreshRadius();
        pathIn.addRoundRect(new RectF(boxWidth/2, top, width-boxWidth/2, top+progressSize), floatsIn, Path.Direction.CW);
        pathOut.addRoundRect(new RectF(boxWidth/2, top, outWidth+boxWidth/2, top+progressSize), floatsOut, Path.Direction.CW);
//        pathIn.addRoundRect(new RectF(boxWidth/2, top, width-boxWidth/2, top+progressSize), floatsIn, Path.Direction.CW);
//        pathOut.addRoundRect(new RectF(boxWidth/2, top, outWidth+boxWidth/2, top+progressSize), floatsOut, Path.Direction.CW);
        pathOut.op(pathIn, Path.Op.INTERSECT); //交集
        canvas.drawPath(pathIn, progressBgPaint);
        canvas.drawPath(pathOut, progressPaint);

        drawBox(canvas, outWidth);
        drawText(canvas, outWidth);
    }

    @Override
    public void drawBox(Canvas canvas, int left) {
        //2.1圆角矩形
//        RectF rectF = new RectF(left, 0, left+boxWidth, boxHeight);
//        canvas.drawRoundRect(rectF, boxRadius, boxRadius, boxPaint);
        RectF rectF = new RectF(left, height-boxHeight, left+boxWidth, height);
        canvas.drawRoundRect(rectF, boxRadius, boxRadius, boxPaint);

        //2.2 画三角形 (绘制这个三角形,你可以绘制任意多边形)
        Path path = new Path();
//        path.moveTo(left + boxWidth/2-sp2px(4), boxHeight);// 此点为多边形的起点
//        path.lineTo(left + boxWidth/2+sp2px(4), boxHeight);
//        path.lineTo(left + boxWidth/2, height-progressSize-2);
        path.moveTo(left + boxWidth/2-sp2px(4), height-boxHeight);// 此点为多边形的起点
        path.lineTo(left + boxWidth/2+sp2px(4), height-boxHeight);
        path.lineTo(left + boxWidth/2, progressSize);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, boxPaint);
    }

    @Override
    public void drawText(Canvas canvas, int left) {
        canvas.drawText(text, left+boxWidth/2-getTextRect(text).width()/2, height-boxHeight+getBaseline(textPaint, boxHeight), textPaint);
    }
}
