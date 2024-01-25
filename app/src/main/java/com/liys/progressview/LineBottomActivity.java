package com.liys.progressview;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.liys.view.LineBottomProView;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/23 10:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineBottomActivity extends AppCompatActivity implements View.OnClickListener {

    LineBottomProView bottomProView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_bottom);

        bottomProView = findViewById(R.id.bottom_pro_view);
        seekBar = findViewById(R.id.seekBar);
        findViewById(R.id.start_btn).setOnClickListener(this);

        seekBar.setMax(bottomProView.dp2px(10));
        seekBar.setProgress(seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bottomProView.setBoxRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
                        bottomProView.setProgress((float) animation.getAnimatedValue());
                    }
                });
                animator.start();
                break;
        }
    }
}
