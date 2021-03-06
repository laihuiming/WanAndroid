package com.example.myapp.Project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapp.Bean.ProjectListBean;
import com.example.myapp.Constant;
import com.example.myapp.Mine.Collect.Collect;
import com.example.myapp.R;
import com.example.myapp.Util.Dialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/8/31
 */
public class ProjectFragmentAdapter extends RecyclerView.Adapter {

    String path;
    String title;
    Context context;
    //    List<ProjectTreeBean.ProjectTreeDataBean> projectTreeDataList;
    List<ProjectListBean.DataBean.DatasBean> projectList;


    public ProjectFragmentAdapter(Context context, List<ProjectListBean.DataBean.DatasBean> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectTreeHolder(LayoutInflater.from(context).inflate(R.layout.item_project_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectTreeHolder projectTreeHolder = (ProjectTreeHolder) holder;
        Glide.with(holder.itemView)
                .load(projectList.get(position).getEnvelopePic())
                .thumbnail(Glide.with(holder.itemView).load(R.mipmap.loading))
                .into(((ProjectTreeHolder) holder).imProject);
        projectTreeHolder.tvProjectTitle.setText(projectList.get(position).getTitle());
        projectTreeHolder.tvProjectTitle.setOnClickListener(new View.OnClickListener() {//???????????????????????????
            @Override
            public void onClick(View v) {
                title = projectList.get(position).getTitle();
                path = projectList.get(position).getLink();
                ARouter.getInstance().build(Constant.PROJECT)
                        .withString("title",title)
                        .withString("path",path)
                        .navigation();
            }
        });
        projectTreeHolder.tvProjectDesc.setText(projectList.get(position).getDesc());
        projectTreeHolder.tvProjectTime.setText(projectList.get(position).getNiceShareDate());
        projectTreeHolder.tvProjectAuthor.setText(projectList.get(position).getAuthor());
        projectTreeHolder.ivProjectCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collect collect = new Collect();
                if (!projectList.get(position).getCollect()){
                    collect.collect(context,projectList.get(position).getId());
                    ProjectFragment fragment = new ProjectFragment();
                    fragment.refresh();
                }else {
                    Dialog dialog = Dialog.getInstance();
                    dialog.showDialog(context,"???????????????????????????");
                    dialog.dialogOnClickListener(new Dialog.DialogOnClickListener() {
                        @Override
                        public void cancelOnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                        @Override
                        public void confirmOnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                            collect.uncollect(context,projectList.get(position).getId());
                            ProjectFragment fragment = new ProjectFragment();
                            fragment.refresh();
                        }
                    });
                }
            }
        });
        if (projectList.get(position).getCollect()){
            projectTreeHolder.ivProjectCollect.setImageResource(R.mipmap.collect);
        }else {//?????????
            projectTreeHolder.ivProjectCollect.setImageResource(R.mipmap.uncollect);
        }
    }

    @Override
    public int getItemCount() {
        Log.e("??????????????????", "getItemCount: "+projectList.size() );
        return projectList.size();
    }

    public class ProjectTreeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.im_project)
        ImageView imProject;
        @BindView(R.id.tv_project_title)
        TextView tvProjectTitle;
        @BindView(R.id.tv_project_desc)
        TextView tvProjectDesc;
        @BindView(R.id.tv_project_time)
        TextView tvProjectTime;
        @BindView(R.id.tv_project_author)
        TextView tvProjectAuthor;
        @BindView(R.id.iv_project_collect)
        ImageView ivProjectCollect;

        public ProjectTreeHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
