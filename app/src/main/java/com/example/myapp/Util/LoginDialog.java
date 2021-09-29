package com.example.myapp.Util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.myapp.R;

/**
 * Created by laihm on 2021/9/26
 */
public class LoginDialog {

    public static LoginDialog getInstance(){
        return new LoginDialog();
    }

    public static void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();

        View view = View.inflate(context, R.layout.dialog_login,null);

        dialog.getWindow().setContentView(view);
        dialog.getWindow().setBackgroundDrawable(null);//去除空白部分,去除了底色
    }

    public static void hide(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.dismiss();
    }


}
