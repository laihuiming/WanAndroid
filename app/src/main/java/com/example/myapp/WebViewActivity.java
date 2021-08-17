package com.example.myapp;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/app/WebViewActivity")
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @Autowired(name = "path")
    public String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);//获取传过来的path
        webview.getSettings().setJavaScriptEnabled(true);//如果访问的页面中有Javascript,则WebView必须设置支持Javascript
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(path);
    }
}
