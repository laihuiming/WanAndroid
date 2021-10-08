package com.example.myapp.Base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.myapp.R;

import com.gyf.immersionbar.ImmersionBar;


/**
 * Created by laihm on 2021/8/24
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManage.addActivity(this);
        ImmersionbarInit();
    }

    private void ImmersionbarInit() {
        ImmersionBar.with(this)
                .statusBarView(findViewById(R.id.status_bar_view))
                .titleBar(R.id.action_bar)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.removeActivity(this);
        ImmersionBar.destroy(this, null);
    }

    //获取返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
