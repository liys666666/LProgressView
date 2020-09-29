package com.liys.progressview.main2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liys.progressview.R;

import java.util.ArrayList;
import java.util.List;



public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    Main2Adapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);
        initData();
        adapter = new Main2Adapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            list.add(""+i);
        }
    }


    public void test(){
        LogUtils.d("test");
    }
}