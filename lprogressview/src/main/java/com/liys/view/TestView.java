package com.liys.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/16 12:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/16 12:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestView extends View {

    Paint paint = new Paint();

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);


        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        setLayerType(LAYER_TYPE_SOFTWARE, null); //禁用硬件加速
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), paint);
    }
}
