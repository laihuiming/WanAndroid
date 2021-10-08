package com.example.myapp.WXArticle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Bean.Collect.CollectObject;
import com.example.myapp.Bean.WXArticleBean;
import com.example.myapp.Bean.WXArticleListBean;
import com.example.myapp.Mine.Collect.Collect;
import com.example.myapp.R;
import com.example.myapp.Util.CollectDialog;
import com.example.myapp.Util.Dialog;

import java.util.List;

/**
 * Created by laihm on 2021/9/29
 */
public class WXArticleAdatper extends RecyclerView.Adapter {

    Context context;
    List<WXArticleBean.DataBean> authorDataBeans;
    List<WXArticleListBean.DataBean.DatasBean> articleList;
    public static final int TYPE_AUTHOR = 0;
    public static final int TYPE_ARTICLE = 1;
    private AuthorOnClickListener authorOnClickListener;
    private void setAuthorOnClickListener(AuthorOnClickListener authorOnClickListener){
        this.authorOnClickListener = authorOnClickListener;
    }

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
            mTvAuthor = itemView.findViewById(R.id.tv_wx_author);
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
                break;
            case TYPE_ARTICLE:
                int articlePosition = position - authorDataBeans.size();
                ArticleList(articlePosition, holder);
                break;
        }
    }

    private void AuthorList(int position, RecyclerView.ViewHolder holder) {
        AuthorHolder authorHolder = (AuthorHolder) holder;
        if (authorDataBeans.get(position).getUserControlSetTop()) {
            authorHolder.mIvAuthorCollect.setImageResource(R.mipmap.wx_collect);
        }else {
            authorHolder.mIvAuthorCollect.setImageResource(R.mipmap.wx_uncollect);
        }
        authorHolder.mIvAuthorCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//收藏
                if (!authorDataBeans.get(position).getUserControlSetTop()){//未收藏，点击收藏
                    Collect.collect(context,authorDataBeans.get(position).getId());
                }else {//已收藏，点击取消收藏
                    Dialog dialog = Dialog.getInstance();
                    dialog.dialogOnClickListener(new Dialog.DialogOnClickListener() {
                        @Override
                        public void cancelOnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void confirmOnClickListener(AlertDialog dialog) {
                            Collect.uncollect(context,authorDataBeans.get(position).getId());
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        authorHolder.mTvAuthor.setText(authorDataBeans.get(position).getName());
        authorHolder.mTvAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//加载此作者微信公众号文章
                authorOnClickListener.loadArticleList(authorDataBeans.get(position).getId());
            }
        });
    }

    private void ArticleList(int position, RecyclerView.ViewHolder holder) {
        ArticleHolder articleHolder = (ArticleHolder) holder;
        articleHolder.tvWxTitle.setText(articleList.get(position).getTitle());
        articleHolder.tvWxTime.setText(articleList.get(position).getNiceDate());
        if (articleList.get(position).getCollect()) {
            articleHolder.ivWxCollect.setImageResource(R.mipmap.collect);
        }else {
            articleHolder.ivWxCollect.setImageResource(R.mipmap.uncollect);
        }
        articleHolder.ivWxCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!articleList.get(position).getCollect()){
                    Collect.collect(context,articleList.get(position).getId());
                }else {
                    Dialog dialog = Dialog.getInstance();
                    dialog.dialogOnClickListener(new Dialog.DialogOnClickListener() {
                        @Override
                        public void cancelOnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void confirmOnClickListener(AlertDialog dialog) {
                            Collect.uncollect(context,articleList.get(position).getId());
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position < authorDataBeans.size() + articleList.size() - articleList.size()) {
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

    public interface AuthorOnClickListener {
        void loadArticleList(int id);
    }


}
