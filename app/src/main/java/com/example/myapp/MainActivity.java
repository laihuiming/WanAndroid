package com.example.myapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.Bean.LogoutBean;
import com.example.myapp.Bean.UserInfoBean;
import com.example.myapp.HomePage.HomePageFragment;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.Mine.MineFragment;
import com.example.myapp.OfficialAccount.OfficialAccountFragment;
import com.example.myapp.Project.ProjectFragment;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.NoScrollViewPager;
import com.example.myapp.Util.SaveCookiesInterceptor;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = "/app/MainActivity")
public class MainActivity extends BaseTitleActivity {

    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.tb_main)
    TabLayout tbMain;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_integral_rank)
    TextView tvIntegralRank;
    @BindView(R.id.tv_collect_article)
    TextView tvCollectArticle;
    @BindView(R.id.tv_share_article)
    TextView tvShareArticle;
    @BindView(R.id.tv_collect_web)
    TextView tvCollectWeb;
    @BindView(R.id.tv_share_project)
    TextView tvShareProject;
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.tv_improve_personal_information)
    TextView tvImprovePersonalInformation;
    @BindView(R.id.bt_back_login)
    Button btBackLogin;
    @BindView(R.id.drawerlayout_mine)
    DrawerLayout drawerlayoutMine;
    @BindView(R.id.tv_head)
    ImageView tvHead;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_userem)
    TextView tvUserem;
    @BindView(R.id.tv_message_center)
    TextView tvMessageCenter;
    @BindView(R.id.tv_integral_rule)
    TextView tvIntegralRule;
    @BindView(R.id.ll_basic_operation)
    CardView llBasicOperation;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.ll_mine_intrgral_detail)
    LinearLayout llMineIntrgralDetail;
    private MainAdapter adapter;

    UserInfoBean userInfoData;


    private Fragment[] mfragments = {new HomePageFragment(), new ProjectFragment(), new OfficialAccountFragment(), new MineFragment()};
    private String[] mtitles = {"首页", "项目", "公众号", "我的"};
    //未选中
    private int[] unImage = {R.mipmap.unhomepage, R.mipmap.unproject, R.mipmap.unofficial_account, R.mipmap.unmine};
    //选中
    private int[] Image = {R.mipmap.homepage, R.mipmap.project, R.mipmap.official_account, R.mipmap.mine};

    //默认状态
    private int def = 0;

    TextView tabText;
    ImageView imageView;
    String stitle;
    private Context context = getContext();
//    BaseActionBar actionBar;

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.setTitle("首页");
        actionBar.hideBack(true);
        actionBar.hideMine(false);
        actionBar.setImgR(getDrawable(R.mipmap.find));
        actionBar.setImgROnClickListener(v -> findOnclick());
        actionBar.setMineOnClickListener(v -> mineOnclick());
        initView();
    }

    private void findOnclick() {
        ARouter.getInstance().build(Constant.NAVIGATION).navigation();
    }

    private void mineOnclick() {
        drawerlayoutMine.openDrawer(Gravity.LEFT);
    }

    private void initUserInfo() {//带cookie请求
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
        Call<UserInfoBean> call = wanAndroidApiService.loadUserInfo();
        call.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                userInfoData = response.body();
                if (userInfoData.getErrorCode() != 0) {
                    SaveCookiesInterceptor.clearCookie(context);//清除本地cookie
                    initUserInfo();
                    Log.e("请求用户信息", "失败");
                } else {
                    tvUsername.setText(userInfoData.getData().getUserInfo().getUsername());
                    tvUserid.setText("id:" + userInfoData.getData().getUserInfo().getId());
                    tvUserem.setText("E-mail:" + userInfoData.getData().getUserInfo().getEmail());
                    tvIntegral.setText(" " + userInfoData.getData().getCoinInfo().getCoinCount());
                    tvLevel.setText("Level:" + userInfoData.getData().getCoinInfo().getLevel());
                    tvRank.setText("排名：" + userInfoData.getData().getCoinInfo().getRank());
                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        initViewPager();
        initTabLayout();
        initUserInfo();

    }

    private void initTabLayout() {
        //初始化
        tbMain.getTabAt(def);
        for (int i = 0; i < tbMain.getTabCount(); i++) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tablayout_view, null);
//            LinearLayout linearLayout = view.findViewById(R.id.ll_tab1);

            tabText = view.findViewById(R.id.tv_tab1);
            imageView = view.findViewById(R.id.iv_tab1);

            tabText.setText(mtitles[i]);

            imageView.setImageResource(unImage[i]);

            if (i == def) {
                imageView.setImageResource(Image[i]);
            }

            //设置样式
            tbMain.getTabAt(i).setCustomView(view);
        }
        //监听
        tbMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中时进入，改变样式
                ItemSelect(tab);
                //onTabselected方法里面调用了viewPager的setCurrentItem 所以要想自定义OnTabSelectedListener，
                // 也加上mViewPager.setCurrentItem(tab.getPosition())就可以了
                vpMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中状态
                ItemNoSelect(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //重新选中
            }
        });
    }

    //tabLayout未选中状态
    private void ItemNoSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        tabText = view.findViewById(R.id.tv_tab1);
        imageView = view.findViewById(R.id.iv_tab1);
        stitle = tabText.getText().toString();
        for (int i = 0; i < mtitles.length; i++) {
            if (mtitles[i].equals(stitle)) {
                imageView.setImageResource(unImage[i]);
            }
        }
    }

    //tabLayout选中状态
    private void ItemSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        tabText = view.findViewById(R.id.tv_tab1);
        imageView = view.findViewById(R.id.iv_tab1);
        stitle = tabText.getText().toString();
        for (int i = 0; i < mtitles.length; i++) {
            if (mtitles[i].equals(stitle)) {
                imageView.setImageResource(Image[i]);
                BaseActionBar baseActionBar = findViewById(R.id.base_bar);
                baseActionBar.setTitle(mtitles[i]);//实现切换fragment时切换标题内容
            }
        }

    }

    private void initViewPager() {

        adapter = new MainAdapter(getSupportFragmentManager(), mtitles, mfragments);
        tbMain.setupWithViewPager(vpMain);
        vpMain.setAdapter(adapter);
        vpMain.setOffscreenPageLimit(4);
        vpMain.setNoScroll(false);//禁止滑动
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //使tabLayout与viewpager联动
                tbMain.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.bt_back_login, R.id.tv_integral_rule, R.id.ll_mine_intrgral_detail, R.id.tv_integral_rank})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back_login:
                logout();
                finish();
                ARouter.getInstance().build(Constant.LOGIN).navigation();
                break;
            case R.id.tv_integral_rule:
                ARouter.getInstance().build(Constant.INTEGRALRULE).navigation();
                break;
            case R.id.ll_mine_intrgral_detail:
                ARouter.getInstance().build(Constant.INTEGRALDETAIL).navigation();
                break;
            case R.id.tv_integral_rank:
                ARouter.getInstance().build(Constant.INTEGRALRANK).navigation();

        }
    }

    //退出，并清除本地cookie
    private void logout() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<LogoutBean> call = wanAndroidApiService.logout();//获取首页置顶文章列表
        call.enqueue(new Callback<LogoutBean>() {
            @Override
            public void onResponse(Call<LogoutBean> call, Response<LogoutBean> response) {
                Toast.makeText(MainActivity.this, "退出成功，返回登录界面", Toast.LENGTH_SHORT).show();
                SaveCookiesInterceptor.clearCookie(context);//清除本地cookie
            }

            @Override
            public void onFailure(Call<LogoutBean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveCookiesInterceptor.clearCookie(context);//清除本地cookie
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}