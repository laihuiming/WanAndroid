package com.example.myapp.HomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Base.BaseFragment;
import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragment extends BaseFragment {

    @BindView(R.id.banner_homepage)
    Banner bannerHomepage;
//    @BindView(R.id.rv_homepage_article)
//    RecyclerView rvHomepageArticle;
//    @BindView(R.id.rv_homepage_article_top)
//    RecyclerView rvHomepageArticleTop;
    //适配器的数据
    private List<BannerBean.DataBean> bannerList = new ArrayList<>();
    private List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList = new ArrayList<>();
    private List<ArticleTopBean.ArticleTopDataBean> articleTopList = new ArrayList<>();

    private List<Object> list;

    //适配器
    private HomePageBannerAdapter bannerAdapter;
    private HomePageArticleAdapter articleAdapter;
    private HomePageArticleTopAdapter articleTopAdapter;

    private HomePageFragmentAdapter adapter;

//    private RecyclerView articleRecycleView;
//    private RecyclerView articleTopRecycleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        initBannerData();
        initArticleTopData();
        initArticleData();
    }

    private void initView(View view) {
        initBannerView();
        RecyclerView articleRecycleView = view.findViewById(R.id.rv_homepage);
        LinearLayoutManager articlemanager = new LinearLayoutManager(getActivity());//创建线性布局管理器
        articlemanager.setOrientation(LinearLayoutManager.VERTICAL);//添加垂直布局
        articleRecycleView.setLayoutManager(articlemanager);//将线性布局管理器添加到recyclerview中
        adapter = new HomePageFragmentAdapter(getContext(),articleList,articleTopList);//实例化适配器
        articleRecycleView.setAdapter(adapter);//添加适配器
    }

    /**
     * 置顶文章数据
     */
    private void initArticleTopData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<ArticleTopBean> call = wanAndroidApiService.loadArticleTop();//获取首页置顶文章列表
        call.enqueue(new Callback<ArticleTopBean>() {
            @Override
            public void onResponse(Call<ArticleTopBean> call, Response<ArticleTopBean> response) {
                ArticleTopBean articleTopBean = response.body();//
                articleTopList.addAll(articleTopBean.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArticleTopBean> call, Throwable t) {

            }
        });
    }

    /**
     * 文章数据
     */
    private void initArticleData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<ArticleBean> call = wanAndroidApiService.loadArticle();//获取首页文章列表
        call.enqueue(new Callback<ArticleBean>() {
            @Override
            public void onResponse(Call<ArticleBean> call, Response<ArticleBean> response) {
                ArticleBean articleBean = response.body();//
                articleList.addAll(articleBean.getData().getDatas());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArticleBean> call, Throwable t) {

            }
        });
    }

    private void initBannerView() {
        bannerAdapter = new com.example.myapp.HomePage.HomePageBannerAdapter(bannerList);
        bannerHomepage.setAdapter(bannerAdapter)
                .isAutoLoop(true)//自动循环
                .setIndicator(new CircleIndicator(getActivity()))
                .addBannerLifecycleObserver(this)
                .start();
    }
    /**
     * 轮播图数据
     */
    private void initBannerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具

                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<BannerBean> call = wanAndroidApiService.loadBanner();//获取首页轮播图列表
        call.enqueue(new Callback<BannerBean>() {
            @Override
            public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                BannerBean bannerBean = response.body();//
                bannerList.addAll(bannerBean.getData());
                bannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BannerBean> call, Throwable t) {
                Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
