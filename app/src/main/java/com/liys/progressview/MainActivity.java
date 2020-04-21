package com.liys.progressview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.liys.view.LineProgressView;

public class MainActivity extends AppCompatActivity {

    LineProgressView progressView;
    LineProgressView progressView2;
    LineProgressView progressView5;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = findViewById(R.id.progressView);
        progressView2 = findViewById(R.id.progressView2);
        progressView5 = findViewById(R.id.progressView5);
        seekBar = findViewById(R.id.seekBar);

        progressView2.setOutGradient(false, Color.RED, Color.YELLOW);
        progressView5.setOutGradient(Color.RED, Color.YELLOW);

//        progressView.setProgress(50);
//        seekBar.setMax(100);
//        seekBar.setProgress(50);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                progressView.setProgress(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }
}
