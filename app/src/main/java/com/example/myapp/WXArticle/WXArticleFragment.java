package com.example.myapp.WXArticle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.myapp.Base.BaseFragment;
import com.example.myapp.Bean.WXArticleBean;
import com.example.myapp.Bean.WXArticleListBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WXArticleFragment extends BaseFragment {

//    WXArticleAdatper adatper;
    WXArticleListAdapter adatper;
    RecyclerView rv;
    TabLayout mTab;
//    LinearLayout llLoading;
    List<WXArticleBean.DataBean> authorData = new ArrayList<>();
    List<WXArticleListBean.DataBean.DatasBean> articleList = new ArrayList<>();
    int id = -1,page = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wx_article, container, false);
//        llLoading = view.findViewById(R.id.ll_loading);
        initTab(view);
        initView(view);
        initData();
        return view;
    }

    private void initTab(View view) {
        mTab = view.findViewById(R.id.tab_wx_article);
        //初始化
        mTab.getTabAt(0);
        for (int i = 0; i < authorData.size(); i++) {
            TabLayout.Tab tab = mTab.newTab();
            tab.setText(authorData.get(i).getName());
            mTab.addTab(tab);
        }
        //监听
        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                articleList.clear();
                id = authorData.get(position).getId();
                initArticleData(id);
//                adatper.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中状态
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //重新选中
            }
        });
    }

//    private WXArticleAdatper.AuthorOnClickListener authorOnClickListener = new WXArticleAdatper.AuthorOnClickListener() {
//        @Override
//        public void loadArticleList(int id) {
//
//            initArticleData(id);
//        }
//    };

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<WXArticleBean> observable = wanAndroidApiService.loadWXArticle();
        observable.concatMap(new Function<WXArticleBean, ObservableSource<WXArticleListBean>>() {
            @Override
            public ObservableSource<WXArticleListBean> apply(WXArticleBean wxArticleBean) throws Throwable {
                if (id == -1&&page ==-1){
                    id = wxArticleBean.getData().get(0).getId();
                    page = 1;
                }
                authorData.addAll(wxArticleBean.getData());
                return wanAndroidApiService.loadWXArticleList(id,page);
            }
        }).subscribe(new Observer<WXArticleListBean>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//                llLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull WXArticleListBean wxArticleListBean) {
                articleList.addAll(wxArticleListBean.getData().getDatas());
                adatper.notifyDataSetChanged();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                ToastUtils.showShort("公众号网络请求错误，请检查");
                Log.e("WX", "onError: "+e.toString());
            }

            @Override
            public void onComplete() {
//                llLoading.setVisibility(View.GONE);
            }
        });

    }

    private void initView(View v) {
        rv = v.findViewById(R.id.rv_wx_article);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adatper = new WXArticleListAdapter(getContext(),articleList);
        rv.setAdapter(adatper);
    }

    private void initArticleData(int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<WXArticleListBean> observable = wanAndroidApiService.loadWXArticleList(id, page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WXArticleListBean>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//                llLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull WXArticleListBean wxArticleListBean) {
                articleList.addAll(wxArticleListBean.getData().getDatas());
                adatper.notifyDataSetChanged();
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                ToastUtils.showShort("公众号网络请求错误，请检查");
                Log.e("WX", "onError: "+e.toString());
            }

            @Override
            public void onComplete() {
//                llLoading.setVisibility(View.GONE);
            }
        });
    }

}
