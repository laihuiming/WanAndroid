package com.example.myapp.WXArticle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Bean.WXArticleListBean;
import com.example.myapp.Constant;
import com.example.myapp.Mine.Collect.Collect;
import com.example.myapp.R;
import com.example.myapp.Util.Dialog;

import java.util.List;

/**
 * Created by laihm on 2021/9/30
 */
public class WXArticleListAdapter extends RecyclerView.Adapter<WXArticleListAdapter.ViewHolder> {

    Context context;
    List<WXArticleListBean.DataBean.DatasBean> articleList;

    public WXArticleListAdapter(Context context, List<WXArticleListBean.DataBean.DatasBean> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wx_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = articleList.get(position).getLink();
                String title = articleList.get(position).getTitle();
                ARouter.getInstance().build(Constant.WEBVIEW)
                        .withString("title",title)
                        .withString("path",path)
                        .navigation();
            }
        });
        holder.tvWxTitle.setText(articleList.get(position).getTitle());
        holder.tvWxTime.setText(articleList.get(position).getNiceDate());
        if (articleList.get(position).getCollect()) {
            holder.ivWxCollect.setImageResource(R.mipmap.collect);
        }else {
            holder.ivWxCollect.setImageResource(R.mipmap.uncollect);
        }
        holder.ivWxCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!articleList.get(position).getCollect()){
                    Collect.collect(context,articleList.get(position).getId());
                }else {
                    Dialog dialog = Dialog.getInstance();
                    dialog.dialogOnClickListener(new Dialog.DialogOnClickListener() {
                        @Override
                        public void cancelOnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void confirmOnClickListener(AlertDialog dialog) {
                            Collect.uncollect(context,articleList.get(position).getId());
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvWxTitle;
        TextView tvWxTime;
        ImageView ivWxCollect;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWxTitle = itemView.findViewById(R.id.tv_wx_title);
            tvWxTime = itemView.findViewById(R.id.tv_wx_time);
            ivWxCollect = itemView.findViewById(R.id.iv_wx_collect);
            layout = itemView.findViewById(R.id.ll_wx_article_list);
        }
    }
}
