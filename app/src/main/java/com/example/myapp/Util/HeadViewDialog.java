package com.example.myapp.Util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.myapp.R;

import java.io.File;

/**
 * Created by laihm on 2021/9/30
 */
public class HeadViewDialog {
    private static DialogOnClickListener dialogOnClickListener;
    public void DialogOnClickListener(DialogOnClickListener dialogOnClickListener){
        this.dialogOnClickListener = dialogOnClickListener;
    }

    public static HeadViewDialog getInstance(){
        return new HeadViewDialog();
    }

    public static void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView mTvCamera;//相机
        TextView mTvAlbum;//相册
        TextView mTvCancel;

        View view = View.inflate(context, R.layout.dialog_head_view,null);
        mTvCamera = view.findViewById(R.id.tv_camera);
        mTvAlbum = view.findViewById(R.id.tv_album);
        mTvCancel = view.findViewById(R.id.tv_cancel);

        mTvCamera.setText("用相机拍一张");
        mTvAlbum.setText("从相册里选择");
        mTvCancel.setText("取消");

        mTvCamera.setOnClickListener(new View.OnClickListener() {//打开相机
            @Override
            public void onClick(View v) {
                dialogOnClickListener.cameraOnClickListener(dialog);
            }
        });

        mTvAlbum.setOnClickListener(new View.OnClickListener() {//打开相册
            @Override
            public void onClick(View v) {
                dialogOnClickListener.albumOnClickListener(dialog);
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消
                dialogOnClickListener.cancelOnClickListener(dialog);
            }
        });

        dialog.getWindow().setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomInAndOutStyle);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        dialog.getWindow().setBackgroundDrawable(null);//去除空白部分,去除了底色
    }

    public interface DialogOnClickListener{
        void cameraOnClickListener(AlertDialog dialog);//相机
        void albumOnClickListener(AlertDialog dialog);//相册
        void cancelOnClickListener(AlertDialog dialog);//取消
    }
}
