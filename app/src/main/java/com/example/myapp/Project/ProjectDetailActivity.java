package com.example.myapp.Project;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/9/2
 */
@Route(path = "/app/ProjectDetailActivity")
public class ProjectDetailActivity extends BaseTitleActivity {

    @BindView(R.id.webview)
    WebView webview;
    @Autowired(name = "path")
    public String path;
    @Autowired(name = "title")
    public String title;

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.hideMine(true);
        actionBar.hideBack(false);
        actionBar.setTitle("项目详情");
        ARouter.getInstance().inject(this);//获取传过来的path
        actionBar.setTitle(title);
        webview.getSettings().setJavaScriptEnabled(true);//如果访问的页面中有Javascript,则WebView必须设置支持Javascript
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(path);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_webview;
    }
}
