package com.example.myapp.HomePage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Bean.ArticleBean;
import com.example.myapp.Bean.ArticleTopBean;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Constant;
import com.example.myapp.MainActivity;
import com.example.myapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageFragmentAdapter extends RecyclerView.Adapter {

//    private static final int TYPE_BANNER_VIEW = 0;
    private static final int TYPE_ARTICLE = 0;
    private static final int TYPE_TOP_VIEW = 1;

    Context context;
    List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList;
    List<ArticleTopBean.ArticleTopDataBean> articleTopList;
    List<ArticleTopBean.ArticleTopDataBean.TopTagsBean> topTagsList;
    List<ArticleBean.ArticleDataBean.ArticleDatasBean.TagsBean> articleTagsList;

    String path;

    public HomePageFragmentAdapter(Context context, List<ArticleBean.ArticleDataBean.ArticleDatasBean> articleList,
                                   List<ArticleTopBean.ArticleTopDataBean> articleTopList){
        this.context = context;
        this.articleList = articleList;
        this.articleTopList = articleTopList;
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
        public ArticleTopHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_TOP_VIEW){
            return new ArticleTopHolder(LayoutInflater.from(context).inflate(R.layout.item_homepage_article_top, parent, false));
        }else {
            return new ArticleHolder(LayoutInflater.from(context).inflate(R.layout.item_homepage_article, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                int i = position-articleTopList.size();
                Article(i,holder);
                break;
            case 1:
                ArticleTop(position,holder);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (position<articleTopList.size()){
            return TYPE_TOP_VIEW;
        }else if ((articleTopList.size()<=position)&&(position<articleList.size()+articleTopList.size())){//大于置顶文章数，小于总数
            return TYPE_ARTICLE;
        }else {
            return -1;
        }
//        if (articleTopList.get(position).getType() == 1){
//            return TYPE_TOP_VIEW;
//        }else if (articleList.get(position).getType() == 0){
//            return TYPE_ARTICLE;
//        }else {
//            return super.getItemViewType(position);
//        }
    }

    @Override
    public int getItemCount() {
        return articleList.size()+articleTopList.size();
    }

    /**
     * 首页文章数据展示
     * @param position
     * @param holder
     */
    public void Article(int position,RecyclerView.ViewHolder holder){
        ArticleHolder articleHolder = (ArticleHolder) holder;
        articleTagsList = articleList.get(position).getTags();
        articleHolder.tvArticle.setText(articleList.get(position).getTitle());
        for (int tags = 0;tags<articleTagsList.size();tags++){
            if (articleTagsList.get(tags).name.equals("公众号")){
                ((ArticleHolder) holder).tvOfficial.setVisibility(View.VISIBLE);
            }else {
                ((ArticleHolder) holder).tvOfficial.setVisibility(View.GONE);
            }
            if (articleTagsList.get(tags).name.equals("项目")){
                ((ArticleHolder) holder).tvProject.setVisibility(View.VISIBLE);
            }else {
                ((ArticleHolder) holder).tvProject.setVisibility(View.GONE);
            }
        }
//        if (articleList.get(position).getTags() != null){
//            articleHolder.tvOfficial.setVisibility(View.VISIBLE);
//            articleHolder.tvProject.setVisibility(View.VISIBLE);
//        }
        articleHolder.tvHomepageAuthor.setText("分享人:"+articleList.get(position).getShareUser());
        if (!articleList.get(position).getAuthor().equals("")){
            articleHolder.tvHomepageAuthor.setText("作者："+articleList.get(position).getAuthor());
        }
        if (articleList.get(position).getFresh()){
            articleHolder.tvFresh.setVisibility(View.VISIBLE);
        }else {
            articleHolder.tvFresh.setVisibility(View.GONE);
        }
        articleHolder.tvHomepageClass.setText("分类:"+articleList.get(position).getSuperChapterName()+"/"+articleList.get(position).getChapterName());
        articleHolder.tvHomepageTime.setText("时间:"+articleList.get(position).getNiceShareDate());
        articleHolder.tvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = articleList.get(position).getLink();
                ARouter.getInstance().build(Constant.WEBVIEW)
                        .withString("path",path)
                        .navigation();
            }
        });
    }

    /**
     * 置顶文章数据展示
     * @param position
     * @param holder
     */
    public void ArticleTop(int position,RecyclerView.ViewHolder holder){
        ArticleTopHolder articleTopHolder = (ArticleTopHolder) holder;
        topTagsList = articleTopList.get(position).getTags();
//        if (!articleTopList.get(position).getTags().equals("[]")){
//            articleTopHolder.tvPublish.setVisibility(View.VISIBLE);
//            articleTopHolder.tvQA.setVisibility(View.VISIBLE);
//        }
        for (int tags = 0;tags<articleTopList.get(position).getTags().size();tags++){
            if ((topTagsList.get(tags).name.equals("本站发布"))||(topTagsList.get(tags).name.equals("问答"))){
                articleTopHolder.tvPublish.setVisibility(View.VISIBLE);
                articleTopHolder.tvQA.setVisibility(View.VISIBLE);
            }
        }
        articleTopHolder.tvArticleTop.setText(articleTopList.get(position).getTitle());
        articleTopHolder.tvHomepageTopAuthor.setText("作者："+articleTopList.get(position).getAuthor());
        articleTopHolder.tvHomepageTopClass.setText("分类："+articleTopList.get(position).getSuperChapterName()+"/"+articleTopList.get(position).getChapterName());
        articleTopHolder.tvHomepageTopTime.setText("时间:"+articleTopList.get(position).getNiceShareDate());
        articleTopHolder.tvArticleTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = articleTopList.get(position).getLink();
                ARouter.getInstance().build(Constant.WEBVIEW)
                        .withString("path",path)
                        .navigation();
            }
        });
    }
}
