package com.example.myapp.Internet;

import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Bean.Collect.CollectListBean;
import com.example.myapp.Bean.Collect.CollectBean;
import com.example.myapp.Bean.Collect.CollectToolsBean;
import com.example.myapp.Bean.Collect.ToolsUpdateBean;
import com.example.myapp.Bean.IntegralDetailBean;
import com.example.myapp.Bean.IntegralRankBean;
import com.example.myapp.Bean.LoginBean;
import com.example.myapp.Bean.LogoutBean;
import com.example.myapp.Bean.NavigationBean;
import com.example.myapp.Bean.ProjectListBean;
import com.example.myapp.Bean.ProjectTreeBean;
import com.example.myapp.Bean.RegisterBean;
import com.example.myapp.Bean.UserInfoBean;
import com.example.myapp.Mine.Collect.Collect;

import retrofit2.Call;
import retrofit2.http.GET;
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
     * 积分排行榜
     * @return
     */
    @GET("/coin/rank/1/json")
    Call<IntegralRankBean> loadIntegralRank();

    /**
     * 获取个人积分获取列表，需要登录后访问
     * @return
     */
    @GET("/lg/coin/list/1/json")
    Call<IntegralDetailBean> loadUserIntegral();

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

    /**
     * 导航数据
     * @return
     */
    @GET("/navi/json")
    Call<NavigationBean> loadNavigation();

    /**
     * 收藏文章列表
     * @return
     */
    @GET("/lg/collect/list/0/json")
    Call<CollectListBean> loadCollectList();


    /**
     * 收藏
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Call<CollectBean> Collect(@Path("id") int id);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Call<CollectBean> unCollect(@Path("id") int id);

    /**
     * 站内及站外收藏
     */
    @POST("/lg/collect/add/json")
    Call<CollectBean> collectOut(@Query("title") String title,@Query("author") String author,@Query("link") String link);

    /**
     * 取消站外收藏
     */
    @POST("/lg/uncollect/{id}/json")
    Call<CollectBean> unCollectOut(@Path("id") int id,@Query("originId") int originId);

    /**
     * 收藏网站列表
     * @return
     */
    @GET("/lg/collect/usertools/json")
    Call<CollectToolsBean> loadcollectTools();

    /**
     * 添加收藏网站
     * @param name
     * @param link
     * @return
     */
    @POST("lg/collect/addtool/json")
    Call<CollectToolsBean> addTool(@Query("name") String name,@Query("link") String link);

    /**
     * 编辑收藏网站
     * @param id
     * @param name
     * @param link
     * @return
     */
    @POST("/lg/collect/updatetool/json")
    Call<ToolsUpdateBean> updateTool(@Query("id") Integer id, @Query("name") String name, @Query("link") String link);

    /**
     * 删除收藏网站
     */
    @POST("/lg/collect/deletetool/json")
    Call<CollectBean> deleteTool(@Query("id") Integer id);

}
