package com.liys.progressview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.liys.view.LineProgressView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    LineProgressView progressView;
    LineProgressView progressView2;
    LineProgressView progressView5;
    LineProgressView progressView6;
    LineProgressView progressView7;
    SeekBar seekBar;
    SeekBar seekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = findViewById(R.id.progressView);
        progressView2 = findViewById(R.id.progressView2);
        progressView5 = findViewById(R.id.progressView5);
        progressView6 = findViewById(R.id.progressView6);
        progressView7 = findViewById(R.id.progressView7);
        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);

        progressView2.setOutGradient(false, Color.RED, Color.YELLOW);
        progressView5.setOutGradient(Color.RED, Color.YELLOW);
        progressView6.setOutGradient(false, Color.parseColor("#571100"),
                Color.RED,
                Color.RED,
                Color.parseColor("#571100"));

        seekBar.setMax(20);
        seekBar.setOnSeekBarChangeListener(this);

        seekBar2.setMax(20);
        seekBar2.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progress = progressView7.sp2px(progress);
        switch (seekBar.getId()){
            case R.id.seekBar:
                progressView7.setRadius(progress);
                progressView7.invalidate();
                break;
            case R.id.seekBar2:
                progressView7.setProgressRadius(progress);
                progressView7.invalidate();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
