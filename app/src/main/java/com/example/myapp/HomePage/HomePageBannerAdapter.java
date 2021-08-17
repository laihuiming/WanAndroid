package com.example.myapp.HomePage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapp.Bean.BannerBean;
import com.example.myapp.Constant;
import com.example.myapp.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;
public class HomePageBannerAdapter extends BannerAdapter<BannerBean.DataBean, HomePageBannerAdapter.BannerViewHolder> {

    private List<BannerBean.DataBean> datas;
    private String path;

    public HomePageBannerAdapter(List<BannerBean.DataBean> datas) {
        super(datas);
        this.datas = datas;
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerBean.DataBean data, int position, int size) {
        Glide.with(holder.itemView)
                .load(datas.get(position).getImagePath())
                .thumbnail(Glide.with(holder.itemView).load(R.mipmap.loading))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = datas.get(position).getUrl();
                ARouter.getInstance()
                        .build(Constant.WEBVIEW)
                        .withString("path", path)
                        .navigation();
            }
        });
    }


    public class BannerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            imageView = itemView.findViewById(R.id.iv_homepage_banner);
            this.imageView = view;
        }
    }
}
