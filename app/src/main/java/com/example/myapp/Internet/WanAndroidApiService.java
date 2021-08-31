package com.example.myapp.Internet;

import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Bean.LoginBean;
import com.example.myapp.Bean.LogoutBean;
import com.example.myapp.Bean.ProjectListBean;
import com.example.myapp.Bean.ProjectTreeBean;
import com.example.myapp.Bean.RegisterBean;
import com.example.myapp.Bean.UserInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WanAndroidApiService {
    /**
     * 首页文章
     * @param
     * @return
     */
    @GET("/article/list/{page}/json")
    Call<ArticleBean> loadArticle(@Path("page") int page);

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

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("/user/login")
    Call<LoginBean> login(@Query("username") String username,@Query("password") String password);

    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("/user/register")
    Call<RegisterBean> register(@Query("username") String username,@Query("password") String password,@Query("repassword") String repassword);

    /**
     * 登出
     * @return
     */
    @GET("/user/logout/json")
    Call<LogoutBean> logout();

    /**
     * 获取个人信息
     * @return
     */
    @GET("/user/lg/userinfo/json")
    Call<UserInfoBean> loadUserInfo();

    /**
     * 项目分类
     * @return
     */
    @GET("/project/tree/json")
    Call<ProjectTreeBean> loadProjectTree();

    /**
     * 项目列表
     * @param page
     * @param cid
     * @return
     */
    @GET("/project/list/{page}/json")
    Call<ProjectListBean> loadProjectList(@Path("page") int page,@Query("cid") int cid);

}
