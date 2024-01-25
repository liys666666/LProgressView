package com.liys.progressview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;

import com.liys.view.ArcProView;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/23 10:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ArcActivity extends Activity implements View.OnClickListener {

    ArcProView arcProView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc);

        arcProView = findViewById(R.id.arc_view);
        seekBar = findViewById(R.id.seekBar);
        findViewById(R.id.start_btn).setOnClickListener(this);

        seekBar.setMax(360);
        seekBar.setProgress(seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                arcProView.setDrawAngle(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        arcProView.setOutGradient(Color.RED, Color.YELLOW, Color.GRAY, Color.RED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 100f);
                animator.setDuration(5000);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        arcProView.setProgress((float) animation.getAnimatedValue());
                    }
                });
                animator.start();
                break;
        }
    }
}
