package com.example.myapp.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Bean.NavigationBean;
import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/9/3
 */
public class NavigationAdapter extends RecyclerView.Adapter {
    Context context;
    List<NavigationBean.DataBean> beanList;
    List<NavigationBean.DataBean.ArticlesBean> articlesList = new ArrayList<>();
    NaviInAdapter adapter;

    public NavigationAdapter(Context context, List<NavigationBean.DataBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavigationHolder(LayoutInflater.from(context).inflate(R.layout.item_navi_out, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NavigationHolder naviholder = (NavigationHolder) holder;
        naviholder.tvNaviChapterName.setText(beanList.get(position).getName());
        articlesList = beanList.get(position).getArticles();
        adapter = new NaviInAdapter(context,articlesList);//实例化适配器
        naviholder.rvNaviIn.setAdapter(adapter);//添加适配器
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class NavigationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_navi_chapterName)
        TextView tvNaviChapterName;
        @BindView(R.id.rv_navi_in)
        RecyclerView rvNaviIn;
        public NavigationHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
            rvNaviIn = v.findViewById(R.id.rv_navi_in);
            GridLayoutManager manager = new GridLayoutManager(context,3);//创建布局管理器
            manager.setOrientation(GridLayoutManager.VERTICAL);//添加布局
            rvNaviIn.setLayoutManager(manager);//将线性布局管理器添加到recyclerview中
        }
    }
}
