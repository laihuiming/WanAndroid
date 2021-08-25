package com.example.myapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.HomePage.HomePageFragment;
import com.example.myapp.Mine.MineFragment;
import com.example.myapp.OfficialAccount.OfficialAccountFragment;
import com.example.myapp.Project.ProjectFragment;
import com.example.myapp.Util.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = "/app/MainActivity")
public class MainActivity extends BaseTitleActivity {

    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.tb_main)
    TabLayout tbMain;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_integral_level)
    TextView tvIntegralLevel;
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
    private MainAdapter adapter;

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

    }

    private void mineOnclick() {
        drawerlayoutMine.openDrawer(Gravity.LEFT);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        initViewPager();
        initTabLayout();
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

}