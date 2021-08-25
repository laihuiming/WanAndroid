package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;

public class NavigationActivity extends BaseTitleActivity {

    @Override
    protected void findViews(Bundle savedInstanceState) {
        BaseActionBar baseActionBar = findViewById(R.id.base_bar);
        baseActionBar.setTitle("导航页面");
        baseActionBar.hideBack(false);
        baseActionBar.hideMine(true);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_navigation;
    }
}