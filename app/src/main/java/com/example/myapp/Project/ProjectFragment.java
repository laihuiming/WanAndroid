package com.example.myapp.Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Base.BaseFragment;
import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ProjectTreeBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.SaveCookiesInterceptor;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectFragment extends BaseFragment {


    @BindView(R.id.rv_project)
    RecyclerView rvProject;
    @BindView(R.id.srl_project_tree)
    SmartRefreshLayout srlProjectTree;

    ProjectFragmentAdapter adapter;
    List<ProjectTreeBean.ProjectTreeDataBean> projectTreeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        initData();
        srlProjectTree.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlProjectTree.finishRefresh(200/*,false*/);//传入false表示刷新失败
                projectTreeList.clear();
                initData();
            }
        });
        return view;
    }

    private void initView(View view) {
        rvProject = view.findViewById(R.id.rv_project);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProject.setLayoutManager(layoutManager);
        adapter = new ProjectFragmentAdapter(getContext(),projectTreeList);
        rvProject.setAdapter(adapter);

    }

    private void initData() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(getContext()))
                .addInterceptor(new SaveCookiesInterceptor(getContext()))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<ProjectTreeBean> call = wanAndroidApiService.loadProjectTree();//获取首页文章列表
        call.enqueue(new Callback<ProjectTreeBean>() {
            @Override
            public void onResponse(Call<ProjectTreeBean> call, Response<ProjectTreeBean> response) {
                ProjectTreeBean projectTreeBean = response.body();
                projectTreeList.addAll(projectTreeBean.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProjectTreeBean> call, Throwable t) {

            }
        });
    }


}
