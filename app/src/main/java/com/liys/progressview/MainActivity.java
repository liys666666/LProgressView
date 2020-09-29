package com.liys.progressview;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.liys.progressview.main2.MainActivity2;
import com.liys.view.BaseProView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int[] ids = new int[]{
            R.id.line_view,
            R.id.center_view,
            R.id.bottom_view,
            R.id.arc_view,
            R.id.water_view,
    };
    BaseProView[] proView = new BaseProView[ids.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main2).setOnClickListener(this);
        findViewById(R.id.line_btn).setOnClickListener(this);
        findViewById(R.id.line_centre_btn).setOnClickListener(this);
        findViewById(R.id.line_bottom_btn).setOnClickListener(this);
        findViewById(R.id.arc_btn).setOnClickListener(this);
        findViewById(R.id.water_wave_btn).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);

        for (int i = 0; i < proView.length; i++) {
            proView[i] = findViewById(ids[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main2:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.line_btn:
                startActivity(new Intent(this, LineActivity.class));
                break;
            case R.id.line_centre_btn:
                startActivity(new Intent(this, LineCentreActivity.class));
                break;
            case R.id.line_bottom_btn:
                startActivity(new Intent(this, LineBottomActivity.class));
                break;
            case R.id.arc_btn:
                startActivity(new Intent(this, ArcActivity.class));
                break;
            case R.id.water_wave_btn:
                startActivity(new Intent(this, WaterWaveActivity.class));
                break;
            case R.id.start:
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 60f);
                animator.setDuration(5000);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        for (int i = 0; i < proView.length; i++) {
                            proView[i].setProgress((float) animation.getAnimatedValue());
                        }
                    }
                });
                animator.start();
                break;
        }
    }
}
