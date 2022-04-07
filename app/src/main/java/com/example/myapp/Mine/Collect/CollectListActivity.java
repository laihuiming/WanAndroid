package com.example.myapp.Mine.Collect;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.Bean.Collect.CollectListBean;
import com.example.myapp.Bean.Collect.CollectObject;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.CollectDialog;
import com.example.myapp.Util.SaveCookiesInterceptor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laihm on 2021/9/9
 */
@Route(path = "/app/CollectListActivity")
public class CollectListActivity extends BaseTitleActivity implements View.OnClickListener {
    RecyclerView rvCollect;
    CollectListAdapter adapter;
    List<CollectListBean.DataBean.DatasBean> list = new ArrayList<>();
    SmartRefreshLayout refreshLayout;
    Context context = getContext();
    FloatingActionButton button;

    AppCompatEditText etCollectTitle;
    AppCompatEditText etCollectAuthor;
    AppCompatEditText etCollectLink;

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.hideMine(true);
        actionBar.hideBack(false);
        actionBar.setTitle("收藏文章列表");
        refreshLayout = findViewById(R.id.srl_collect);
        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(200/*,false*/);//传入false表示刷新失败
                refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(200/*,false*/);//传入false表示刷新失败
            }
        });
        initData();
        initView();
    }

    private CollectListAdapter.RefreshOnClickListener refreshOnClickListener = new CollectListAdapter.RefreshOnClickListener() {
        @Override
        public void refresh() {
            list.clear();
            initData();
        }
    };



    public void refresh() {
        list.clear();
        initData();
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
        Call<CollectListBean> call = wanAndroidApiService.loadCollectList();
        call.enqueue(new Callback<CollectListBean>() {
            @Override
            public void onResponse(Call<CollectListBean> call, Response<CollectListBean> response) {
                CollectListBean bean = response.body();
                list.addAll(bean.getData().getDatas());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CollectListBean> call, Throwable t) {
                    Toast.makeText(context, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        etCollectTitle =findViewById(R.id.et_collect_title);
        etCollectAuthor =findViewById(R.id.et_collect_author);
        etCollectLink =findViewById(R.id.et_collect_link);
        button = findViewById(R.id.fab_collect);
        rvCollect = findViewById(R.id.rv_collect);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCollect.setLayoutManager(linearLayoutManager);
        adapter = new CollectListAdapter(this,list);
        rvCollect.setAdapter(adapter);
        adapter.setRefreshOnClickListener(refreshOnClickListener);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_collect_list;
    }


    @OnClick(R.id.fab_collect)
    public void onClick(View v) {
        if (v.getId()==R.id.fab_collect){
            CollectDialog collectDialog = CollectDialog.getInstance();
            collectDialog.showCollectDialog(context,true);

            collectDialog.setCollectDialogOnClickListener(new CollectDialog.CollectDialogOnClickListener() {
                @Override
                public void cancelOnClickListener(AlertDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void confirmOnClickListener(AlertDialog dialog, CollectObject object) {
                    dialog.dismiss();
                    String title = object.getTitle();
                    String author = object.getAuthor();
                    String link = object.getLink();
                    Collect.collectOut(context,title ,author ,link);
                    refresh();
                }
            });
        }
    }
}
