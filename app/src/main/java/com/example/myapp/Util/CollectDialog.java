package com.example.myapp.Util;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.myapp.Bean.Collect.CollectObject;
import com.example.myapp.R;


/**
 * Created by laihm on 2021/9/10
 */
public class CollectDialog {

    private static CollectDialogOnClickListener collectDialogOnClickListener;
    public void setCollectDialogOnClickListener(CollectDialogOnClickListener collectDialogOnClickListener){
        this.collectDialogOnClickListener = collectDialogOnClickListener;
    }


    public static CollectDialog getInstance() {
        return new CollectDialog();
    }

    public static void showCollectDialog(Context context,Boolean visibility) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog collectDialog = builder.create();
        collectDialog.show();

        AppCompatEditText etCollectTitle;
        AppCompatEditText etCollectAuthor;
        AppCompatEditText etCollectLink;
        TextView tvCollectLeft;
        TextView tvCollectRight;

        View view = View.inflate(context, R.layout.collect_dialog, null);
        etCollectTitle = view.findViewById(R.id.et_collect_title);
        etCollectAuthor = view.findViewById(R.id.et_collect_author);
        if (visibility){
            etCollectAuthor.setVisibility(View.VISIBLE);
        }else {
            etCollectAuthor.setVisibility(View.GONE);
        }
        etCollectLink = view.findViewById(R.id.et_collect_link);
        tvCollectLeft = view.findViewById(R.id.tv_collect_left);
        tvCollectRight = view.findViewById(R.id.tv_collect_right);

        tvCollectLeft.setText("取 消");
        tvCollectRight.setText("确 定");
        tvCollectLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectDialogOnClickListener.cancelOnClickListener(collectDialog);
            }
        });
        tvCollectRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etCollectTitle.getText().toString();
                String author = etCollectAuthor.getText().toString();
                String link = etCollectLink.getText().toString();
                CollectObject object = new CollectObject();
                object.setTitle(title);
                if (visibility) {
                    object.setAuthor(author);
                }else {
                    object.setAuthor("");
                }
                object.setLink(link);
                collectDialogOnClickListener.confirmOnClickListener(collectDialog,object);
            }
        });
        collectDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        collectDialog.getWindow().setContentView(view);
    }

    public interface CollectDialogOnClickListener{
        void cancelOnClickListener(AlertDialog dialog);//取消
        void confirmOnClickListener(AlertDialog dialog,CollectObject object);//确定
    }
}
