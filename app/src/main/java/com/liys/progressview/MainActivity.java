package com.liys.progressview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.line_btn).setOnClickListener(this);
        findViewById(R.id.line_centre_btn).setOnClickListener(this);
        findViewById(R.id.line_bottom_btn).setOnClickListener(this);
        findViewById(R.id.arc_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        }
    }
}
