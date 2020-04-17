package com.liys.progressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liys.view.LineProgressView;

public class MainActivity extends AppCompatActivity {

    LineProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = findViewById(R.id.progressView);
        findViewById(R.id.seekBar);
    }
}
