package com.liys.progressview.main2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liys.progressview.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/9/29 15:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/29 15:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class Main2Adapter extends RecyclerView.Adapter<Main2Adapter.MainViewHolder> {

    List<String> list = new ArrayList<>();

    public Main2Adapter(List<String> list){
        if(list!=null){
            this.list.addAll(list);
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main2, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
