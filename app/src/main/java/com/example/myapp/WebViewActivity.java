package com.example.myapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/app/WebViewActivity")
public class WebViewActivity extends BaseTitleActivity {

    @BindView(R.id.webview)
    WebView webview;
    @Autowired(name = "path")
    public String path;


    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.hideMine(true);
        actionBar.hideBack(false);
        actionBar.setTitle("外部页面WebView");
        ARouter.getInstance().inject(this);//获取传过来的path
        webview.getSettings().setJavaScriptEnabled(true);//如果访问的页面中有Javascript,则WebView必须设置支持Javascript
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(path);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_webview;
    }
}
