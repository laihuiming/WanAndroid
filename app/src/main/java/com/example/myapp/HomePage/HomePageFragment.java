package com.example.myapp.HomePage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Base.BaseFragment;
import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragment extends BaseFragment {

    @BindView(R.id.banner_homepage)
    Banner bannerHomepage;
    @BindView(R.id.fab_homepage)
    FloatingActionButton fabHomepage;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.rv_homepage)
    RecyclerView rvHomepage;
    //适配器的数据
    private List<BannerBean.DataBean> bannerList = new ArrayList<>();
    private List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList = new ArrayList<>();
    private List<ArticleTopBean.ArticleTopDataBean> articleTopList = new ArrayList<>();

    RecyclerView articleRecycleView;

    //适配器
    private HomePageBannerAdapter bannerAdapter;

    private HomePageFragmentAdapter adapter;
    public int page = 0;
    SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData(page);
        smartRefreshLayout = view.findViewById(R.id.refreshlayout);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
                refresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
                loadMore();
            }
        });
        return view;
    }

    protected void loadMore() {//加载更多
        page++;
        initArticleData(page);
    }

    protected void refresh() {//刷新
        initArticleData(page);
    }

    public void initData(int page) {
        initBannerData();
        initArticleTopData();
        initArticleData(page);
    }


    private void initView(View view) {
        initBannerView();
        articleRecycleView = view.findViewById(R.id.rv_homepage);
        LinearLayoutManager articlemanager = new LinearLayoutManager(getActivity());//创建线性布局管理器
        articlemanager.setOrientation(LinearLayoutManager.VERTICAL);//添加垂直布局
        articleRecycleView.setLayoutManager(articlemanager);//将线性布局管理器添加到recyclerview中
        adapter = new HomePageFragmentAdapter(getContext(), articleList, articleTopList);//实例化适配器
        articleRecycleView.setAdapter(adapter);//添加适配器
    }

    /**
     * 置顶文章数据
     */
    private void initArticleTopData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<ArticleTopBean> observable = wanAndroidApiService.loadArticleTop();//获取首页置顶文章列表
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleTopBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArticleTopBean articleTopBean) {
                        articleTopList.addAll(articleTopBean.getData());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 文章数据
     */
    private void initArticleData(int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//支持rxjava
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<ArticleBean> observable = wanAndroidApiService.loadArticle(page);
        observable.subscribeOn(Schedulers.io())                //进io线程
                .observeOn(AndroidSchedulers.mainThread())      //回到主线程
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.e("使用rxjava请求文章列表：", "开始");
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArticleBean articleBean) {
                        ArticleBean bean = articleBean;
                        articleList.addAll(bean.getData().getDatas());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("使用rxjava请求文章列表：", "出问题了");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("使用rxjava请求文章列表：", "请求结束");
                    }
                });
    }

    private void initBannerView() {
        bannerAdapter = new HomePageBannerAdapter(bannerList);
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//支持rxjava
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<BannerBean> observable = wanAndroidApiService.loadBanner();//获取首页轮播图列表
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull BannerBean bannerBean) {
                        BannerBean bean = bannerBean;
                        bannerList.addAll(bean.getData());
                        bannerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.fab_homepage,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_homepage:
                scrollView.smoothScrollTo(0, 0);//ScrollView置顶
                break;
//            case R.id.iv_menu_mine:
//                drawerlayoutMine.openDrawer(Gravity.LEFT);
//                break;
        }
    }
}
