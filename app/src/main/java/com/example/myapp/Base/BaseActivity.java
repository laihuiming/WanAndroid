package com.example.myapp.Base;

import android.os.Bundle;

import androidx.annotation.Nullable;
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
        ImmersionBar.destroy(this,null);
    }
}
