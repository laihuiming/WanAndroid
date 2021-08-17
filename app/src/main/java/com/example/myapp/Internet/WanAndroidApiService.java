package com.example.myapp.Internet;

import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WanAndroidApiService {
    /**
     * 首页文章
     * @param
     * @return
     */
    @GET("/article/list/0/json")
    Call<ArticleBean> loadArticle();

    /**
     * 首页banner
     * @return
     */
    @GET("/banner/json")
    Call<BannerBean> loadBanner();

    /**
     * 首页置顶文章
     * @return
     */
    @GET("/article/top/json")
    Call<ArticleTopBean> loadArticleTop();
}
