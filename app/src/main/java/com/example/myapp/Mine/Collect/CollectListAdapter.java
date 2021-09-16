package com.example.myapp.Mine.Collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Bean.Collect.CollectListBean;
import com.example.myapp.Constant;
import com.example.myapp.R;
import com.example.myapp.Util.Dialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/9/9
 */

public class CollectListAdapter extends RecyclerView.Adapter<CollectListAdapter.Holder> {

    Context context;
    List<CollectListBean.DataBean.DatasBean> list;

    public CollectListAdapter(Context context, List<CollectListBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_collect_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.tvCollectTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(Constant.WEBVIEW)
                        .withString("title", list.get(position).getTitle())
                        .withString("path", list.get(position).getLink())
                        .navigation();
            }
        });

        if (list.get(position).getTitle()==null){
            holder.tvCollectTitle.setVisibility(View.GONE);
        } else {
            holder.tvCollectTitle.setText("" + list.get(position).getTitle());
        }
        if (list.get(position).getAuthor()==null){
            holder.tvCollectAuthor.setVisibility(View.GONE);
        }else {
            holder.tvCollectAuthor.setText("作者：" + list.get(position).getAuthor());
        }
        if (list.get(position).getChapterId()==0){
            holder.tvCollectChapter.setVisibility(View.GONE);
        }else {
            holder.tvCollectChapter.setText("分类：" + list.get(position).getChapterName());
        }
        holder.tvCollectTime.setText("收藏时间：" + list.get(position).getNiceDate());
        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = Dialog.getInstance();
                dialog.showDialog(context,"确定要取消收藏吗？确定后将从收藏列表中移除！");
                dialog.dialogOnClickListener(new Dialog.DialogOnClickListener() {
                    @Override
                    public void cancelOnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void confirmOnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                        if (list.get(position).getOriginId()==-1){
                            Collect.uncollectOut(context,list.get(position).getId(),list.get(position).getOriginId());
                            refreshOnClickListener.refresh();
                        }else {
                            Collect.uncollect(context, list.get(position).getOriginId());
                            refreshOnClickListener.refresh();
                        }

                    }
                });

            }
        });
    }

    //定义回调接口
    public interface RefreshOnClickListener{
        void refresh();
    }

    private RefreshOnClickListener refreshOnClickListener;

    public void setRefreshOnClickListener(RefreshOnClickListener refresh){
        this.refreshOnClickListener = refresh;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_collect_title)
        TextView tvCollectTitle;
        @BindView(R.id.tv_collect_author)
        TextView tvCollectAuthor;
        @BindView(R.id.tv_collect_chapter)
        TextView tvCollectChapter;
        @BindView(R.id.tv_collect_time)
        TextView tvCollectTime;
        @BindView(R.id.iv_collect)
        ImageView ivCollect;

        public Holder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
