package com.example.myapp.Mine.Collect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.myapp.Bean.Collect.CollectBean;
import com.example.myapp.Bean.Collect.CollectToolsBean;
import com.example.myapp.Bean.Collect.ToolsUpdateBean;
import com.example.myapp.Bean.IntegralRankBean;
import com.example.myapp.Constant;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.SaveCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laihm on 2021/9/9
 * 收藏功能相关接口
 */
public class Collect{
    //收藏
    public static void collect(Context context,int id) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectBean> call = wanAndroidApiService.Collect(id);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                ToastUtils.showShort("收藏成功");
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                Toast.makeText(context, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void collectOut(Context context,String title,String author,String link){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectBean> call = wanAndroidApiService.collectOut(title, author, link);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                ToastUtils.showShort("收藏成功");
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    //取消收藏
    public static void uncollect(Context context,int id) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectBean> call = wanAndroidApiService.unCollect(id);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                ToastUtils.showShort("取消收藏成功");
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    //取消站外收藏
    public static void uncollectOut(Context context,int id,int originId) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectBean> call = wanAndroidApiService.unCollectOut(id,originId);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                ToastUtils.showShort("取消收藏成功");
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    //--------------------------------收藏网站--------------------------------------------------------------------

    //收藏网站列表
    public static void usertools(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectToolsBean> call = wanAndroidApiService.loadcollectTools();
        call.enqueue(new Callback<CollectToolsBean>() {
            @Override
            public void onResponse(Call<CollectToolsBean> call, Response<CollectToolsBean> response) {
                CollectToolsBean bean = response.body();
            }

            @Override
            public void onFailure(Call<CollectToolsBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    //添加收藏网站
    public static void addTool(Context context,String name,String link) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectToolsBean> call = wanAndroidApiService.addTool(name, link);
        call.enqueue(new Callback<CollectToolsBean>() {
            @Override
            public void onResponse(Call<CollectToolsBean> call, Response<CollectToolsBean> response) {
                ToastUtils.showShort("收藏成功");
            }

            @Override
            public void onFailure(Call<CollectToolsBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    //
    public static void updateTool(Context context,Integer id,String name,String link) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<ToolsUpdateBean> call = wanAndroidApiService.updateTool(id,name,link);
        call.enqueue(new Callback<ToolsUpdateBean>() {
            @Override
            public void onResponse(Call<ToolsUpdateBean> call, Response<ToolsUpdateBean> response) {
                ToastUtils.showShort("修改成功");
            }

            @Override
            public void onFailure(Call<ToolsUpdateBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

    //删除收藏网站
    public static void deleteTool(Context context,Integer id) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<CollectBean> call = wanAndroidApiService.deleteTool(id);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                ToastUtils.showShort("取消收藏成功");
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ToastUtils.showShort("数据请求失败，请检查网络");
            }
        });
    }

}
