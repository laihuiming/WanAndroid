package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by laihm on 2021/10/11
 */
public class WelComeActivityAdapter extends RecyclerView.Adapter<WelComeActivityAdapter.ViewHolder> {

    int[] image;

    public WelComeActivityAdapter(int[] image){
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vp2_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageView.setImageResource(image[position]);
        if (position == 3){
            holder.mBtn.setVisibility(View.VISIBLE);
            holder.mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(Constant.LOGIN).navigation();
                }
            });
        }else {
            holder.mBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        Button mBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_vp2);
            mBtn = itemView.findViewById(R.id.bt_welcome);
        }
    }
}
