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
import com.example.myapp.Bean.Collect.CollectToolsBean;
import com.example.myapp.Bean.Collect.ToolObject;
import com.example.myapp.Constant;
import com.example.myapp.R;
import com.example.myapp.Util.ColorUtil;
import com.example.myapp.Util.Dialog;
import com.example.myapp.Util.ToolDialog;

import java.util.List;

/**
 * Created by laihm on 2021/9/13
 */
public class UserToolsAdapter extends RecyclerView.Adapter<UserToolsAdapter.Holder> {

    Context context;
    List<CollectToolsBean.DataBean> beanList;
    int code;
    public UserToolsAdapter(Context context, List<CollectToolsBean.DataBean> beanList,int code){
        this.context = context;
        this.beanList = beanList;
        this.code = code;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_user_tools,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (code == 1) {
            holder.mIvUpdate.setVisibility(View.VISIBLE);
            holder.mIvDelete.setVisibility(View.VISIBLE);
        }else {
            holder.mIvUpdate.setVisibility(View.GONE);
            holder.mIvDelete.setVisibility(View.GONE);
        }
        Integer id = beanList.get(position).getId();
        String name = beanList.get(position).getName();
        String link = beanList.get(position).getLink();
        holder.mTvTitle.setText(name);
        holder.mTvTitle.setTextColor(ColorUtil.randomColor());
        holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(Constant.WEBVIEW)
                        .withString("path",link)
                        .withString("title",name)
                        .navigation();
            }
        });
        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = Dialog.getInstance();
                dialog.showDialog(context,"确定要删除这个网址吗？");
                dialog.dialogOnClickListener(new Dialog.DialogOnClickListener() {
                    @Override
                    public void cancelOnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void confirmOnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                        Collect.deleteTool(context,id);
                        refreshOnClickListener.refresh();
                    }
                });

            }
        });

        holder.mIvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToolDialog dialog = ToolDialog.getInstance();
                dialog.showToolDialog(context,name,link);
                dialog.setToolDialogOnClickListener(new ToolDialog.ToolDialogOnClickListener() {
                    @Override
                    public void cancelOnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void confirmOnClickListener(AlertDialog dialog, ToolObject object) {
                        dialog.dismiss();
                        String ObName = object.getName();
                        String ObLink = object.getLink();
                        Collect.updateTool(context,id,ObName,ObLink);
                        refreshOnClickListener.refresh();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        ImageView mIvDelete;
        ImageView mIvUpdate;
        public Holder(@NonNull View v) {
            super(v);
            mTvTitle = v.findViewById(R.id.tv_user_tools);
            mIvDelete = v.findViewById(R.id.iv_tool_delete);
            mIvUpdate = v.findViewById(R.id.iv_tool_update);
        }
    }


    //定义回调接口
    public interface ToolRefreshOnClickListener {
        void refresh();
    }

    private ToolRefreshOnClickListener refreshOnClickListener;

    public void setRefreshOnClickListener(ToolRefreshOnClickListener refresh){
        this.refreshOnClickListener = refresh;
    }
}
