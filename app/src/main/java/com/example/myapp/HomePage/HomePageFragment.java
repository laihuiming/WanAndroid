package com.example.myapp.HomePage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
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
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragment extends BaseFragment {

//    @BindView(R.id.banner_homepage)
//    Banner banner;
    @BindView(R.id.fab_homepage)
    FloatingActionButton fabHomepage;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.rv_homepage)
    RecyclerView rvHomepage;
    //??????????????????
    private List<BannerBean.DataBean> bannerList = new ArrayList<>();
    private List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList = new ArrayList<>();
    private List<ArticleTopBean.ArticleTopDataBean> articleTopList = new ArrayList<>();

    RecyclerView articleRecycleView;
    LinearLayout llLoading;

    //?????????
//    private HomePageBannerAdapter bannerAdapter;

    private HomePageFragmentAdapter adapter;
    public int page = 0;
    SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, view);
        llLoading = view.findViewById(R.id.ll_loading);
        initView(view);
        initData(page);
        smartRefreshLayout = view.findViewById(R.id.refreshlayout);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200/*,false*/);//??????false??????????????????
                refresh();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(200/*,false*/);//??????false??????????????????
                loadMore();
            }
        });
        return view;
    }

    protected void loadMore() {//????????????
        page++;
        initArticleData(page);
    }

    protected void refresh() {//??????
        initData(0);
    }

    public void initData(int page) {
        llLoading.setVisibility(View.VISIBLE);
        initBannerData();
//        initArticleTopData();
        initTwoData(page);
        initArticleData(page);
        llLoading.setVisibility(View.GONE);
    }


    private void initView(View view) {
//        initBannerView();
        articleRecycleView = view.findViewById(R.id.rv_homepage);
        LinearLayoutManager articlemanager = new LinearLayoutManager(getActivity());//???????????????????????????
        articlemanager.setOrientation(LinearLayoutManager.VERTICAL);//??????????????????
        articleRecycleView.setLayoutManager(articlemanager);//?????????????????????????????????recyclerview???
        articleRecycleView.setNestedScrollingEnabled(false);//????????????
        adapter = new HomePageFragmentAdapter(getContext(), articleList, articleTopList, bannerList);//??????????????????
        articleRecycleView.setAdapter(adapter);//???????????????
    }

    private void initTwoData(int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//??????url
                .addConverterFactory(GsonConverterFactory.create())//Gson????????????
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//????????????
        Observable<ArticleTopBean> observableTop = wanAndroidApiService.loadArticleTop();
//        Observable<ArticleBean> observable = wanAndroidApiService.loadArticle(page);
        observableTop.concatMap(new Function<ArticleTopBean, ObservableSource<ArticleBean>>() {
            @Override
            public ObservableSource<ArticleBean> apply(ArticleTopBean articleTopBean) throws Throwable {
                articleTopList.addAll(articleTopBean.getData());
                return wanAndroidApiService.loadArticle(page);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.e("TAG", "onSubscribe: " + articleTopList.size());
                        ToastUtils.showShort("??????????????????");
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArticleBean articleBean) {
                        articleList.addAll(articleBean.getData().getDatas());
                        Log.e("TAG", "onNext: " + articleList.size());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("TAG", "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


//    /**
//     * ??????????????????
//     */
//    private void initArticleTopData() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BaseUrl)//??????url
//                .addConverterFactory(GsonConverterFactory.create())//Gson????????????
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//????????????
//        Observable<ArticleTopBean> observable = wanAndroidApiService.loadArticleTop();//??????????????????????????????
//        observable.subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ArticleTopBean>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArticleTopBean articleTopBean) {
//                        articleTopList.addAll(articleTopBean.getData());
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Log.e("articleTop", "onError: "+e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    /**
     * ????????????
     */
    private void initArticleData(int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//??????url
                .addConverterFactory(GsonConverterFactory.create())//Gson????????????
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//??????rxjava
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//????????????
        Observable<ArticleBean> observable = wanAndroidApiService.loadArticle(page);
        observable.subscribeOn(Schedulers.io())                //???io??????
                .observeOn(AndroidSchedulers.mainThread())      //???????????????
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        Log.e("??????rxjava?????????????????????", "??????");
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArticleBean articleBean) {
                        ArticleBean bean = articleBean;
                        articleList.addAll(bean.getData().getDatas());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("??????rxjava?????????????????????", "????????????"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("??????rxjava?????????????????????", "????????????" + articleList.size());
                    }
                });
    }
//
//    private void initBannerView() {
//        bannerAdapter = new HomePageBannerAdapter(bannerList);
//        bannerHomepage.setAdapter(bannerAdapter)
//                .isAutoLoop(true)//????????????
//                .setIndicator(new CircleIndicator(getActivity()))
//                .addBannerLifecycleObserver(this)
//                .start();
//    }

    /**
     * ???????????????
     */
    private void initBannerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//??????url
                .addConverterFactory(GsonConverterFactory.create())//Gson????????????
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//??????rxjava
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//????????????
        Observable<BannerBean> observable = wanAndroidApiService.loadBanner();//???????????????????????????
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        bannerList.clear();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull BannerBean bannerBean) {
                        BannerBean bean = bannerBean;
                        bannerList.addAll(bean.getData());
//                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("Banner", "onError: "+e.toString());
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
                scrollView.smoothScrollTo(0, 0);//ScrollView??????
                break;
//            case R.id.iv_menu_mine:
//                drawerlayoutMine.openDrawer(Gravity.LEFT);
//                break;
        }
    }
}
