package com.example.myapp.Util;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.myapp.Bean.Collect.CollectObject;
import com.example.myapp.Bean.Collect.ToolObject;
import com.example.myapp.R;

/**
 * Created by laihm on 2021/9/14
 */
public class ToolDialog {
    private static ToolDialogOnClickListener toolDialogOnClickListener;
    public void setToolDialogOnClickListener(ToolDialogOnClickListener toolDialogOnClickListener){
        this.toolDialogOnClickListener = toolDialogOnClickListener;
    }


    public static ToolDialog getInstance() {
        return new ToolDialog();
    }

    public static void showToolDialog(Context context, @Nullable String name,@Nullable String link) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog toolDialog = builder.create();
        toolDialog.show();

        AppCompatEditText etToolName;
        AppCompatEditText etToolLink;
        TextView tvToolLeft;
        TextView tvToolRight;

        View view = View.inflate(context, R.layout.tool_dialog, null);
        etToolName = view.findViewById(R.id.et_tool_name);
        etToolLink = view.findViewById(R.id.et_tool_link);
        tvToolLeft = view.findViewById(R.id.tv_tool_left);
        tvToolRight = view.findViewById(R.id.tv_tool_right);

        etToolName.setText(name);
        etToolLink.setText(link);
        tvToolLeft.setText("取 消");
        tvToolRight.setText("确 定");
        tvToolLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolDialogOnClickListener.cancelOnClickListener(toolDialog);
            }
        });
        tvToolRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etToolName.getText().toString();
                String link = etToolLink.getText().toString();
                ToolObject object = new ToolObject();
                object.setName(title);
                object.setLink(link);
                toolDialogOnClickListener.confirmOnClickListener(toolDialog,object);
            }
        });
        toolDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        toolDialog.getWindow().setContentView(view);
    }


    public interface ToolDialogOnClickListener {
        void cancelOnClickListener(AlertDialog dialog);//取消
        void confirmOnClickListener(AlertDialog dialog,ToolObject object);//确定
    }
}
