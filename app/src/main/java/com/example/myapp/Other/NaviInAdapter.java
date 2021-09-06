package com.example.myapp.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Bean.NavigationBean;
import com.example.myapp.Constant;
import com.example.myapp.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by laihm on 2021/9/3
 */
public class NaviInAdapter extends RecyclerView.Adapter {

    Context context;
    List<NavigationBean.DataBean.ArticlesBean> articlesList;


    public NaviInAdapter(Context context, List<NavigationBean.DataBean.ArticlesBean> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NaviInHolder(LayoutInflater.from(context).inflate(R.layout.item_navi_in, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NaviInHolder naviInHolder = (NaviInHolder) holder;
        naviInHolder.tvNaviTitle.setText(articlesList.get(position).getTitle());
        naviInHolder.tvNaviTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = articlesList.get(position).getLink();
                ARouter.getInstance()
                        .build(Constant.WEBVIEW)
                        .withString("path",path)
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class NaviInHolder extends RecyclerView.ViewHolder {
        TextView tvNaviTitle;
        public NaviInHolder(@NonNull View v) {
            super(v);
            tvNaviTitle = v.findViewById(R.id.tv_navi_title);
        }
    }
}
