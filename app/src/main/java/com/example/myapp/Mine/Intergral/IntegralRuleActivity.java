package com.example.myapp.Mine.Intergral;

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
@Route(path = "/app/IntegralRuleActivity")
public class IntegralRuleActivity extends BaseTitleActivity {

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
        actionBar.setTitle("积分规则");
        ARouter.getInstance().inject(this);//获取传过来的path
        webview.getSettings().setJavaScriptEnabled(true);//如果访问的页面中有Javascript,则WebView必须设置支持Javascript
        webview.setWebViewClient(new WebViewClient());
        path = "https://www.wanandroid.com/blog/show/2653";
        webview.loadUrl(path);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_webview;
    }
}
