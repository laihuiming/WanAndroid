package com.example.myapp.Mine.Collect;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.Bean.Collect.CollectObject;
import com.example.myapp.Bean.Collect.CollectToolsBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.CollectDialog;
import com.example.myapp.Util.SaveCookiesInterceptor;
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
 * Created by laihm on 2021/9/13
 * 收藏网站
 */
@Route(path = "/app/UserToolsActivity")
public class UserToolsActivity extends BaseTitleActivity implements View.OnClickListener {

    TextView mTvEdit;
    TextView mTvFinish;
    RecyclerView rv;
    UserToolsAdapter adapter;
    SmartRefreshLayout refreshLayout;
    int code = 0;//改变按钮状态，初始为0不显示，1时显示
    Context context = getContext();
    List<CollectToolsBean.DataBean> beanList = new ArrayList<>();

    @Override
    protected void findViews(Bundle savedInstanceState) {
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.hideMine(true);
        actionBar.hideBack(false);
        actionBar.setImgR(getDrawable(R.mipmap.add_r));
        actionBar.setImgROnClickListener(this);
        actionBar.setTitle("收藏网站列表");
        ButterKnife.bind(this);
        initData();
        initView();
        refreshLayout = findViewById(R.id.srl_user_tools);
        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(200/*,false*/);//传入false表示加载失败
                refresh.refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(200/*,false*/);//传入false表示加载失败
            }
        });
    }

    private UserToolsAdapter.ToolRefreshOnClickListener refresh = new UserToolsAdapter.ToolRefreshOnClickListener() {
        @Override
        public void refresh() {
            beanList.clear();
            initData();
        }
    };

    private void initData() {
        //收藏网站列表
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(this))
                .addInterceptor(new SaveCookiesInterceptor(this))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectToolsBean> call = wanAndroidApiService.loadcollectTools();
        call.enqueue(new Callback<CollectToolsBean>() {
            @Override
            public void onResponse(Call<CollectToolsBean> call, Response<CollectToolsBean> response) {
                CollectToolsBean bean = response.body();
                beanList.addAll(bean.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CollectToolsBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    private void initView() {
        mTvEdit = findViewById(R.id.tv_tools_edit);
        mTvFinish = findViewById(R.id.tv_tools_finish);
        rv = findViewById(R.id.rv_user_tools);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new UserToolsAdapter(this,beanList,code);
        rv.setAdapter(adapter);
        adapter.setRefreshOnClickListener(refresh);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_user_tool;
    }

    @OnClick({R.id.tv_tools_edit,R.id.tv_tools_finish})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_tools_edit:
                code = 1;
                adapter.code = code;
                adapter.notifyDataSetChanged();
                mTvEdit.setVisibility(View.GONE);
                mTvFinish.setVisibility(View.VISIBLE);
                ToastUtils.showShort("开始编辑");
                break;
            case R.id.tv_tools_finish:
                code = 0;
                adapter.code = code;
                adapter.notifyDataSetChanged();
                mTvEdit.setVisibility(View.VISIBLE);
                mTvFinish.setVisibility(View.GONE);
                ToastUtils.showShort("编辑完成");
                break;
            case R.id.iv_right:
                CollectDialog collectDialog = CollectDialog.getInstance();
                collectDialog.showCollectDialog(getContext(),false);
                collectDialog.setCollectDialogOnClickListener(new CollectDialog.CollectDialogOnClickListener() {
                    @Override
                    public void cancelOnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void confirmOnClickListener(AlertDialog dialog, CollectObject object) {
                        dialog.dismiss();
                        String title = object.getTitle();
                        String link = object.getLink();
                        Collect.addTool(getContext(),title,link);
                        refresh.refresh();
                    }
                });
        }
    }

//    public interface ToolsListOnClickListener{
//        void editOnClickListener();
//        void finishOnClickListener();
//    }
//
//    private ToolsListOnClickListener toolsListOnClickListener;
//    public void setToolsListOnClickListener(ToolsListOnClickListener toolsListOnClickListener){
//        this.toolsListOnClickListener = toolsListOnClickListener;
//    }
}
