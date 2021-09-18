package com.example.myapp.Mine.Intergral;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.Bean.IntegralDetailBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.R;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.SaveCookiesInterceptor;

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

/**
 * Created by laihm on 2021/9/2
 */
@Route(path = "/app/IntegralDetailActivity")
public class IntegralDetailActivity extends BaseTitleActivity {
    @BindView(R.id.rv_integral_detail)
    RecyclerView rvIntegralDetail;
    IntegralDetailAdapter adapter;
    List<IntegralDetailBean.DataBean.DatasBean> integralDetailList = new ArrayList<>();
    Context context = getContext();
    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.hideMine(true);
        actionBar.hideBack(false);
        actionBar.setTitle("个人积分详情");
        initData();
        initView();
    }

    private void initView() {
        rvIntegralDetail = findViewById(R.id.rv_integral_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvIntegralDetail.setLayoutManager(linearLayoutManager);
        adapter = new IntegralDetailAdapter(getContext(),integralDetailList);
        rvIntegralDetail.setAdapter(adapter);
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
        Call<IntegralDetailBean> call = wanAndroidApiService.loadUserIntegral();
        call.enqueue(new Callback<IntegralDetailBean>() {
            @Override
            public void onResponse(Call<IntegralDetailBean> call, Response<IntegralDetailBean> response) {
                IntegralDetailBean bean = response.body();
                integralDetailList.addAll(bean.getData().getDatas());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<IntegralDetailBean> call, Throwable t) {
                Toast.makeText(IntegralDetailActivity.this, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_integral_detail;
    }

}
