package com.example.myapp.Other;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.Bean.NavigationBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.SaveCookiesInterceptor;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = "/app/NavigationActivity")
public class NavigationActivity extends BaseTitleActivity {

    Context context = getContext();
    List<String> chapterName = new ArrayList<>();
    List<NavigationBean.DataBean> beanList = new ArrayList<>();
    RecyclerView rv;
    NavigationAdapter adapter;
    @BindView(R.id.tab_navigation)
    TabLayout tabNavigation;
    LinearLayoutManager manager;

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BaseActionBar baseActionBar = findViewById(R.id.base_bar);
        baseActionBar.setTitle("导航页面");
        baseActionBar.hideBack(false);
        baseActionBar.hideMine(true);
        initData();
        initView();
    }

    private void initView() {
        initTabLayout();
        rv = findViewById(R.id.rv_navi_out);
        manager = new LinearLayoutManager(this);//创建线性布局管理器
        manager.setOrientation(LinearLayoutManager.VERTICAL);//添加垂直布局
        rv.setLayoutManager(manager);//将线性布局管理器添加到recyclerview中
        adapter = new NavigationAdapter(getContext(), beanList);//实例化适配器
        rv.setAdapter(adapter);//添加适配器

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://滚动停止
                        int position = manager.findFirstCompletelyVisibleItemPosition();
                        tabNavigation.getTabAt(position);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://手指 拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滚动
                        break;
                }
            }
        });
    }

    private void initTabLayout() {
        //初始化
        tabNavigation.getTabAt(0);
        for (int i = 0; i < chapterName.size(); i++) {
            TabLayout.Tab tab = tabNavigation.newTab();
            tab.setText(chapterName.get(i));
            tabNavigation.addTab(tab);
        }
        //监听
        tabNavigation.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                moveToPosition(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中状态
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //重新选中
                int position = tab.getPosition();
                moveToPosition(position);
            }
        });
    }

    private void moveToPosition(int position){
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            rv.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = rv.getChildAt(position - firstItem).getTop();
            rv.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            rv.smoothScrollToPosition(position);

        }
    }

    private void initData() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<NavigationBean> call = wanAndroidApiService.loadNavigation();
        call.enqueue(new Callback<NavigationBean>() {
            @Override
            public void onResponse(Call<NavigationBean> call, Response<NavigationBean> response) {
                NavigationBean bean = response.body();
                for (int i = 0; i < bean.getData().size(); i++) {
                    chapterName.add(bean.getData().get(i).getName());
                }
                beanList.addAll(bean.getData());
                initTabLayout();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NavigationBean> call, Throwable t) {
                Toast.makeText(NavigationActivity.this, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_navigation;
    }

}