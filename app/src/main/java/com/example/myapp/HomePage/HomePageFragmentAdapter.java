package com.example.myapp.HomePage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Constant;
import com.example.myapp.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageFragmentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER_VIEW = 0;
    private static final int TYPE_ARTICLE = 1;
    private static final int TYPE_TOP_VIEW = 2;
    Context context;
    private List<BannerBean.DataBean> bannerDatas;
    List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList;
    List<ArticleTopBean.ArticleTopDataBean> articleTopList;
    List<ArticleTopBean.ArticleTopDataBean.TopTagsBean> topTagsList;
    List<ArticleBean.ArticleDataBean.ArticleDatasBean.TagsBean> articleTagsList;

    private HomePageBannerAdapter bannerAdapter;


    String path;

    public HomePageFragmentAdapter(Context context, List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList,
                                   List<ArticleTopBean.ArticleTopDataBean> articleTopList,
                                   List<BannerBean.DataBean> bannerDatas) {
        this.context = context;
        this.articleList = articleList;
        this.articleTopList = articleTopList;
        this.bannerDatas = bannerDatas;
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        Banner banner;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_homepage);
        }
    }

    public class ArticleTopHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_article_top)
        TextView tvArticleTop;
        @BindView(R.id.tv_top)
        TextView tvTop;
        @BindView(R.id.tv_publish)
        TextView tvPublish;
        @BindView(R.id.tv_QA)
        TextView tvQA;
        @BindView(R.id.tv_homepage_top_author)
        TextView tvHomepageTopAuthor;
        @BindView(R.id.tv_homepage_top_class)
        TextView tvHomepageTopClass;
        @BindView(R.id.tv_homepage_top_time)
        TextView tvHomepageTopTime;
        @BindView(R.id.iv_homepage_top_collect)
        ImageView ivHomepageTopCollect;
        public ArticleTopHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_article)
        TextView tvArticle;
        @BindView(R.id.tv_official)
        TextView tvOfficial;
        @BindView(R.id.tv_project)
        TextView tvProject;
        @BindView(R.id.tv_homepage_author)
        TextView tvHomepageAuthor;
        @BindView(R.id.tv_homepage_class)
        TextView tvHomepageClass;
        @BindView(R.id.tv_homepage_time)
        TextView tvHomepageTime;
        @BindView(R.id.tv_fresh)
        TextView tvFresh;
        @BindView(R.id.iv_homepage_collect)
        ImageView ivHomepageCollect;
        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP_VIEW) {
            return new ArticleTopHolder(LayoutInflater.from(context).inflate(R.layout.item_homepage_article_top, parent, false));
        } else if (viewType == TYPE_ARTICLE) {
            return new ArticleHolder(LayoutInflater.from(context).inflate(R.layout.item_homepage_article, parent, false));
        } else {
            return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_BANNER_VIEW:

                Log.e("Banner", "onBindViewHolder: " + "加载了banner条目");
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                bannerAdapter = new HomePageBannerAdapter(bannerDatas);
                bannerViewHolder.banner.setAdapter(bannerAdapter)
                        .isAutoLoop(true)//自动循环
                        .setIndicator(new CircleIndicator(context))
//                .addBannerLifecycleObserver(this)
                        .start();
//                Banner(holder);
                break;
            case TYPE_ARTICLE:
                int articlePosition = position - articleTopList.size() - 1;
                Article(articlePosition, holder);
                break;
            case TYPE_TOP_VIEW:
                int topPosition = position - 1;
                ArticleTop(topPosition, holder);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position < 1) {
            return TYPE_BANNER_VIEW;
        } else if (1 <= position && position < 1 + articleTopList.size()) {
            return TYPE_TOP_VIEW;
        } else if (1 + articleTopList.size() <= position && position < 1 + articleList.size() + articleTopList.size()) {
            return TYPE_ARTICLE;
        } else {
            return super.getItemViewType(position);
        }
//        return super.getItemViewType(position);
//        if (position<articleTopList.size()){
//            return TYPE_TOP_VIEW;
//        }else if ((articleTopList.size()<=position)&&(position<articleList.size()+articleTopList.size())){//大于置顶文章数，小于总数
//            return TYPE_ARTICLE;
//        }else {
//            return -1;
//        }
    }

    @Override
    public int getItemCount() {
        return 1 + articleList.size() + articleTopList.size();
    }

//    public void Banner(RecyclerView.ViewHolder holder){
//        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
//        ((BannerViewHolder) holder).bannerAdapter = new HomePageBannerAdapter(bannerDatas);
//        bannerViewHolder.banner.setAdapter(((BannerViewHolder) holder).bannerAdapter)
//                .isAutoLoop(true)//自动循环
//                .setIndicator(new CircleIndicator(context))
////                .addBannerLifecycleObserver(this)
//                .start();
//    }

    /**
     * 首页文章数据展示
     *
     * @param position
     * @param holder
     */
    public void Article(int position, RecyclerView.ViewHolder holder) {
        ArticleHolder articleHolder = (ArticleHolder) holder;
        articleTagsList = articleList.get(position).getTags();
        articleHolder.tvArticle.setText(articleList.get(position).getTitle());
        for (int tags = 0; tags < articleTagsList.size(); tags++) {
            if (articleTagsList.get(tags).name.equals("公众号")) {
                ((ArticleHolder) holder).tvOfficial.setVisibility(View.VISIBLE);
            } else {
                ((ArticleHolder) holder).tvOfficial.setVisibility(View.GONE);
            }
            if (articleTagsList.get(tags).name.equals("项目")) {
                ((ArticleHolder) holder).tvProject.setVisibility(View.VISIBLE);
            } else {
                ((ArticleHolder) holder).tvProject.setVisibility(View.GONE);
            }
        }
        articleHolder.tvHomepageAuthor.setText("分享人:" + articleList.get(position).getShareUser());
        if (!articleList.get(position).getAuthor().equals("")) {
            articleHolder.tvHomepageAuthor.setText("作者：" + articleList.get(position).getAuthor());
        }
        if (articleList.get(position).getFresh()) {
            articleHolder.tvFresh.setVisibility(View.VISIBLE);
        } else {
            articleHolder.tvFresh.setVisibility(View.GONE);
        }
        articleHolder.tvHomepageClass.setText("分类:" + articleList.get(position).getSuperChapterName() + "/" + articleList.get(position).getChapterName());
        articleHolder.tvHomepageTime.setText("时间:" + articleList.get(position).getNiceShareDate());
        if (articleList.get(position).getCollect()){
            articleHolder.ivHomepageCollect.setImageResource(R.mipmap.collect);
        }else {
            articleHolder.ivHomepageCollect.setImageResource(R.mipmap.uncollect);
        }
        articleHolder.tvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = articleList.get(position).getLink();
                ARouter.getInstance().build(Constant.WEBVIEW)
                        .withString("title", articleList.get(position).getTitle())
                        .withString("path", path)
                        .navigation();
            }
        });
    }

    /**
     * 置顶文章数据展示
     *
     * @param position
     * @param holder
     */
    public void ArticleTop(int position, RecyclerView.ViewHolder holder) {
        ArticleTopHolder articleTopHolder = (ArticleTopHolder) holder;
        topTagsList = articleTopList.get(position).getTags();
//        if (!articleTopList.get(position).getTags().equals("[]")){
//            articleTopHolder.tvPublish.setVisibility(View.VISIBLE);
//            articleTopHolder.tvQA.setVisibility(View.VISIBLE);
//        }
        for (int tags = 0; tags < articleTopList.get(position).getTags().size(); tags++) {
            if ((topTagsList.get(tags).name.equals("本站发布")) || (topTagsList.get(tags).name.equals("问答"))) {
                articleTopHolder.tvPublish.setVisibility(View.VISIBLE);
                articleTopHolder.tvQA.setVisibility(View.VISIBLE);
            }
        }
        articleTopHolder.tvArticleTop.setText(articleTopList.get(position).getTitle());
        articleTopHolder.tvHomepageTopAuthor.setText("作者：" + articleTopList.get(position).getAuthor());
        articleTopHolder.tvHomepageTopClass.setText("分类：" + articleTopList.get(position).getSuperChapterName() + "/" + articleTopList.get(position).getChapterName());
        articleTopHolder.tvHomepageTopTime.setText("时间:" + articleTopList.get(position).getNiceShareDate());
        if (articleTopList.get(position).getCollect()) {
            articleTopHolder.ivHomepageTopCollect.setImageResource(R.mipmap.collect);
        }else {
            articleTopHolder.ivHomepageTopCollect.setImageResource(R.mipmap.uncollect);
        }
        articleTopHolder.tvArticleTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = articleTopList.get(position).getLink();
                ARouter.getInstance().build(Constant.WEBVIEW)
                        .withString("title", articleTopList.get(position).getTitle())
                        .withString("path", path)
                        .navigation();
            }
        });
    }
}
