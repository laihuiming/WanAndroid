package com.example.myapp.WXArticle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Bean.WXArticleBean;
import com.example.myapp.Bean.WXArticleListBean;
import com.example.myapp.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by laihm on 2021/9/29
 */
public class WXArticleAdatper extends RecyclerView.Adapter {

    Context context;
    List<WXArticleBean.DataBean> authorDataBeans;
    List<WXArticleListBean.DataBean.DatasBean> articleList;
    public static final int TYPE_AUTHOR = 0;
    public static final int TYPE_ARTICLE = 1;


    public WXArticleAdatper(Context context, List<WXArticleBean.DataBean> dataBeans,
                            List<WXArticleListBean.DataBean.DatasBean> articleList) {
        this.context = context;
        this.authorDataBeans = dataBeans;
        this.articleList = articleList;
    }

    public class AuthorHolder extends RecyclerView.ViewHolder {

        ImageView mIvAuthorCollect;
        TextView mTvAuthor;

        public AuthorHolder(@NonNull View itemView) {
            super(itemView);
            mIvAuthorCollect = itemView.findViewById(R.id.iv_wx_author);
            mTvAuthor = itemView.findViewById(R.id.rv_wx_article);
        }
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {
        TextView tvWxTitle;
        TextView tvWxTime;
        ImageView ivWxCollect;
        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            tvWxTitle = itemView.findViewById(R.id.tv_wx_title);
            tvWxTime = itemView.findViewById(R.id.tv_wx_time);
            ivWxCollect = itemView.findViewById(R.id.iv_wx_collect);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AUTHOR) {
            return new AuthorHolder(LayoutInflater.from(context).inflate(R.layout.item_wx_author, parent, false));
        } else {
            return new ArticleHolder(LayoutInflater.from(context).inflate(R.layout.item_wx_article, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_AUTHOR:
                AuthorList(position, holder);
            case TYPE_ARTICLE:
                int articlePosition = position - authorDataBeans.size();
                ArticleList(articlePosition, holder);
        }
    }

    private void AuthorList(int position, RecyclerView.ViewHolder holder) {
        AuthorHolder authorHolder = (AuthorHolder) holder;
        if (authorDataBeans.get(position).getUserControlSetTop()) {
            ((AuthorHolder) holder).mIvAuthorCollect.setImageResource(R.mipmap.wx_collect);
        }else {
            ((AuthorHolder) holder).mIvAuthorCollect.setImageResource(R.mipmap.wx_uncollect);
        }

        ((AuthorHolder) holder).mTvAuthor.setText(authorDataBeans.get(position).getName());
    }

    private void ArticleList(int position, RecyclerView.ViewHolder holder) {
        ArticleHolder articleHolder = (ArticleHolder) holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < position - articleList.size()) {
            return TYPE_AUTHOR;
        } else if (position - articleList.size() <= position && position < authorDataBeans.size() + articleList.size()) {
            return TYPE_ARTICLE;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return authorDataBeans.size() + articleList.size();
    }


}
