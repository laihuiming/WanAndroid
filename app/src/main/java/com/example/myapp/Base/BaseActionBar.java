package com.example.myapp.Base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapp.R;


public class BaseActionBar extends LinearLayout {

    ImageView mMine;
    ImageView mBack;
    TextView mTitle;
    ImageView mImgR;
    TextView mRightTitle;

    public BaseActionBar(Context context) {
        super(context);
    }

    public BaseActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.actionbar, null);
        mMine = view.findViewById(R.id.iv_mine);
        mBack = view.findViewById(R.id.iv_back);
        mTitle = view.findViewById(R.id.tv_title);
        mImgR = view.findViewById(R.id.iv_right);
        mRightTitle = view.findViewById(R.id.tv_right_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionBar);
        Boolean mine = typedArray.getBoolean(R.styleable.BaseActionBar_mine, false);
        Boolean back = typedArray.getBoolean(R.styleable.BaseActionBar_back, true);
        String title = typedArray.getString(R.styleable.BaseActionBar_title);
        Drawable imgR = typedArray.getDrawable(R.styleable.BaseActionBar_imgR);
        String rightT = typedArray.getString(R.styleable.BaseActionBar_rightTitle);

        //个人主页
        if (mine) {
            mMine.setVisibility(VISIBLE);
        } else {
            mMine.setVisibility(GONE);
        }

        //返回键
        if (!back) {
            mBack.setVisibility(GONE);
        } else {
            mBack.setVisibility(VISIBLE);
        }

        //标题
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }

        //右侧图片
        if (imgR == null) {
            mImgR.setVisibility(GONE);
        } else {
            mImgR.setImageDrawable(imgR);
        }

        //右侧标题
        if (!TextUtils.isEmpty(rightT)) {
            mRightTitle.setText(rightT);
            mRightTitle.setVisibility(VISIBLE);
            mImgR.setVisibility(GONE);
        }
        addView(view);
    }

    //个人主页
    public BaseActionBar hideMine(boolean hide) {
        mMine.setVisibility(hide?GONE:VISIBLE);
        return this;
    }

    //返回键
    public BaseActionBar hideBack(boolean hide) {
        mBack.setVisibility(hide?GONE:VISIBLE);
        return this;
    }

    //标题
    public BaseActionBar setTitle(String title) {
        mTitle.setText(title);
        return  this;
    }

    //右侧图片
    public BaseActionBar setImgR(Drawable drawable) {
        mImgR.setImageDrawable(drawable);
        if (drawable != null){
            mImgR.setVisibility(VISIBLE);
        }
        return  this;
    }

    //右侧标题
    public BaseActionBar setRightTitle(String Rtitle) {
        mRightTitle.setText(Rtitle);
        if (Rtitle != null){
            mRightTitle.setVisibility(VISIBLE);
            mImgR.setVisibility(GONE);
        }
        return this;
    }

    public void setMineOnClickListener(OnClickListener onClickListener){
        mMine.setOnClickListener(onClickListener);
    }

    public void setBackOnClickListener(OnClickListener onClickListenet){
        mBack.setOnClickListener(onClickListenet);
    }

    public void setRightTitleOnClickListener(OnClickListener onClickListener) {
        mRightTitle.setOnClickListener(onClickListener);
    }

    public void setImgROnClickListener(OnClickListener onClickListener){
        mImgR.setOnClickListener(onClickListener);
    }
}
