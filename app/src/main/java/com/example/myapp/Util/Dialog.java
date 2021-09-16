package com.example.myapp.Util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.myapp.R;

/**
 * Created by laihm on 2021/9/9
 */
public class Dialog {

    private static DialogOnClickListener dialogOnClickListener;
    public void dialogOnClickListener(DialogOnClickListener dialogOnClickListener){
        this.dialogOnClickListener = dialogOnClickListener;
    }

    public static Dialog getInstance(){
        return new Dialog();
    }

    public static void showDialog(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView mMessage;
        TextView mCancel;
        TextView mConfirm;
        View view = View.inflate(context, R.layout.dialog,null);
        mMessage = view.findViewById(R.id.tv_dialog_message);
        mCancel = view.findViewById(R.id.tv_dialog_left);
        mConfirm = view.findViewById(R.id.tv_dialog_right);

        mMessage.setText(message);
        mCancel.setText("取 消");
        mConfirm.setText("确 定");
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOnClickListener.cancelOnClickListener(dialog);
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOnClickListener.confirmOnClickListener(dialog);
            }
        });
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setBackgroundDrawable(null);//去除空白部分,去除了底色
    }

    public interface DialogOnClickListener {
        void cancelOnClickListener(AlertDialog dialog);//取消
        void confirmOnClickListener(AlertDialog dialog);//确定
    }


}
