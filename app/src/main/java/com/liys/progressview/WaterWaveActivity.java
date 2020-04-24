package com.liys.progressview;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SeekBar;

import com.liys.view.WaterWaveProView;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/4/23 10:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 10:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WaterWaveActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    WaterWaveProView waterProView;
    SeekBar seekBar;
    SeekBar seekBar2;
    SeekBar seekBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_wave);

        waterProView = findViewById(R.id.water_view);
        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        findViewById(R.id.start_btn).setOnClickListener(this);

        seekBar.setMax(waterProView.dp2px(100));
        seekBar.setOnSeekBarChangeListener(this);

        seekBar2.setMax(waterProView.dp2px(30));
        seekBar2.setOnSeekBarChangeListener(this);

        seekBar3.setMax(10);
        seekBar3.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 50f);
                animator.setDuration(5000);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        waterProView.setProgress((float) animation.getAnimatedValue());
                    }
                });
                animator.start();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(progress==0){
            return;
        }
        switch (seekBar.getId()){
            case R.id.seekBar:
                waterProView.setWaveWidth(progress);
                break;
            case R.id.seekBar2:
                waterProView.setWaveHeight(progress);
                break;
            case R.id.seekBar3:
                waterProView.setWaveSpeed(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waterProView.replace();
    }
}
