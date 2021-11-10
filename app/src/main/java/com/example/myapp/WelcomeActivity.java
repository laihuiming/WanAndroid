package com.example.myapp;

import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;

import com.example.myapp.Base.BaseActivity;
import com.youth.banner.transformer.ScaleInTransformer;

@Route(path = "/app/WelcomeActivity")
public class WelcomeActivity extends BaseActivity {

//    Fragment[] fm = {new  WelComeFmOne(), new WelComeFmTwo(), new WelComeFmThree(), new WelComeFmFour()};
    int[] image = {R.mipmap.img7,R.mipmap.img8,R.mipmap.img9,R.mipmap.img10};
    WelComeActivityAdapter adpater;
    ViewPager2 vp;
//    Button mButton;
    boolean isFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        isFirst = SPUtils.getInstance().getBoolean("isFirst");
        Log.e("isFirst", "拿到的isFirst的值： "+isFirst );
        if (!SPUtils.getInstance().getBoolean("isFirst")){//判断是否首次启动
//            ARouter.getInstance().build(Constant.LOGIN).navigation();
        }else {
            isFirst = false;
            SPUtils.getInstance().put("isFirst", isFirst);
        }
        initView();
    }

    private void initView() {
        vp = findViewById(R.id.vp_welcome);
        adpater = new WelComeActivityAdapter(image);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new ScaleInTransformer());
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        vp.setOffscreenPageLimit(4);
        vp.setPageTransformer(compositePageTransformer);
        vp.setAdapter(adpater);
    }


}