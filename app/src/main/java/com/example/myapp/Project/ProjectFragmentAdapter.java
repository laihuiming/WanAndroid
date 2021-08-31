package com.example.myapp.Project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Bean.ProjectTreeBean;
import com.example.myapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/8/31
 */
public class ProjectFragmentAdapter extends RecyclerView.Adapter {

    Context context;
    List<ProjectTreeBean.ProjectTreeDataBean> projectTreeDataList;


    public ProjectFragmentAdapter(Context context, List<ProjectTreeBean.ProjectTreeDataBean> projectTreeDataList) {
        this.context = context;
        this.projectTreeDataList = projectTreeDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectTreeHolder(LayoutInflater.from(context).inflate(R.layout.item_project_tree, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectTreeHolder holder1 = (ProjectTreeHolder) holder;
        ((ProjectTreeHolder) holder).tvProjectType.setText(projectTreeDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return projectTreeDataList.size();
    }

    public class ProjectTreeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_project_type)
        TextView tvProjectType;

        public ProjectTreeHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
