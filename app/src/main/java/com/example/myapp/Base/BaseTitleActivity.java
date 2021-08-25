package com.example.myapp.Base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.myapp.R;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseTitleActivity extends BaseActivity {

    BaseActionBar baseActionBar;

    ViewGroup llContent;

    ViewGroup llNetWorkError;

    ViewGroup llNoData;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initLayout();
        setBindView();
        findViews(savedInstanceState);
        initListener();
    }

    protected Activity getContext(){
        return this;
    }

    protected  void initLayout(){
        baseActionBar = findViewById(R.id.base_bar);
        llContent = findViewById(R.id.ll_content);
        llNetWorkError = findViewById(R.id.ll_net_work_error);
        llNoData = findViewById(R.id.ll_no_data);

    }


    private void initListener() {
        baseActionBar.setBackOnClickListener(v -> onclick());
    }


    private void setBindView() {
        if(setLayoutId()==0){
            return;
        }
        View view = LayoutInflater.from(this).inflate(setLayoutId(), null);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llContent.addView(view, layoutParams);
    }

    public void NetWorkError() {
        llContent.setVisibility(View.GONE);
        llNetWorkError.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    public void NoData(){
        llContent.setVisibility(View.GONE);
        llNetWorkError.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    //加载完成
    public void Complete(){
        llContent.setVisibility(View.VISIBLE);
        llNetWorkError.setVisibility(View.GONE);
        llNoData.setVisibility(View.GONE);
    }

    protected abstract void findViews(Bundle savedInstanceState);

    @LayoutRes
    public abstract int setLayoutId();

    private void onclick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
