package com.example.myapp.Project;

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
import com.example.myapp.Bean.ProjectListBean;
import com.example.myapp.Bean.ProjectTreeBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.SaveCookiesInterceptor;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectFragment extends BaseFragment {


    @BindView(R.id.rv_project)
    RecyclerView rvProject;

    SmartRefreshLayout srlProjectTree;
    LinearLayout llLoading;
    @BindView(R.id.tab_project)
    TabLayout tabProject;
    //    List<ProjectTreeBean.ProjectTreeDataBean> tabNameList = new ArrayList<>();//tablayout 标题
    List<String> tabNameList = new ArrayList<>();
    ProjectFragmentAdapter adapter;
    private int curpage = 1;
    List<Integer> cidList = new ArrayList<>();
    List<ProjectTreeBean.ProjectTreeDataBean> projectTreeList = new ArrayList<>();
    List<ProjectListBean.DataBean.DatasBean> dataBeanList = new ArrayList<>();

    int def = 0;//默认状态
    //    int page = 1;
    int cid = 294;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, view);
        llLoading = view.findViewById(R.id.ll_loading);
        initData();
        initView(view);
        srlProjectTree = view.findViewById(R.id.srl_project_tree);
        srlProjectTree.setRefreshHeader(new ClassicsHeader(getContext()));
        srlProjectTree.setRefreshFooter(new ClassicsFooter(getContext()));
        srlProjectTree.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {//刷新
                refreshLayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
                refresh();
            }
        });
        srlProjectTree.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {//加载更多
                refreshLayout.finishLoadMore(200/*,false*/);//传入false表示刷新失败
                curpage++;
                initProjectListData(curpage, cid);
            }
        });
        return view;
    }

    public void refresh() {
        dataBeanList.clear();
        initProjectListData(curpage, cid);
    }


    private void initData() {
        initTabData();
        initProjectListData(curpage, cid);
    }

    private void initView(View view) {
        initTabLayout();
        initRecyclerView(view);

    }

    private void initRecyclerView(View view) {
        rvProject = view.findViewById(R.id.rv_project);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProject.setLayoutManager(linearLayoutManager);
        adapter = new ProjectFragmentAdapter(getContext(), dataBeanList);
        rvProject.setAdapter(adapter);
    }

    private void initTabLayout() {
        //初始化
        tabProject.getTabAt(def);
        for (int i = 0; i < tabNameList.size(); i++) {
            TabLayout.Tab tab = tabProject.newTab();
            tab.setText(tabNameList.get(i));
            tabProject.addTab(tab);
        }
        //监听
        tabProject.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                dataBeanList.clear();
                cid = cidList.get(position);
                initProjectListData(curpage, cid);
                adapter.notifyDataSetChanged();
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

    private void initTabData() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(getContext()))
                .addInterceptor(new SaveCookiesInterceptor(getContext()))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<ProjectTreeBean> observable = wanAndroidApiService.loadProjectTree();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectTreeBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ProjectTreeBean projectTreeBean) {
                        projectTreeList.addAll(projectTreeBean.getData());
                        for (int i = 0; i < projectTreeList.size(); i++) {
                            String title = projectTreeList.get(i).getName();
                            Integer id = projectTreeList.get(i).getId();
                            tabNameList.add(title);
                            cidList.add(id);
                        }
                        Log.e("项目分类列表：", "获取成功"+projectTreeList.size());

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        ToastUtils.showShort("你网络炸了？"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        initTabLayout();
                    }
                });
    }

    private void initProjectListData(int page, int cid) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(getContext()))
                .addInterceptor(new SaveCookiesInterceptor(getContext()))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Observable<ProjectListBean> observable = wanAndroidApiService.loadProjectList(page, cid);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectListBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        llLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ProjectListBean projectListBean) {
                        ToastUtils.showShort("加载列表");
                        dataBeanList.addAll(projectListBean.getData().getDatas());
                        curpage = projectListBean.getData().getCurPage();
                        adapter.notifyDataSetChanged();
                        Log.e("项目列表", "获取成功,有: "+projectListBean.getData().getSize()+"个" );
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        ToastUtils.showShort("网络有问题，请检查你的网络");
                        Log.e("projectList", "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("加载完成");
                        llLoading.setVisibility(View.GONE);
                    }
                });
    }


}
