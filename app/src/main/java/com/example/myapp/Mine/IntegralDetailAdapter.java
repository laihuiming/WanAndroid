package com.example.myapp.Mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Bean.IntegralDetailBean;
import com.example.myapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/9/2
 */
public class IntegralDetailAdapter extends RecyclerView.Adapter {
    List<IntegralDetailBean.DataBean.DatasBean> integralDetailList;
    Context context;


    public IntegralDetailAdapter(Context context, List<IntegralDetailBean.DataBean.DatasBean> integralDetailList) {
        this.context = context;
        this.integralDetailList = integralDetailList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntegralDetailHolder(LayoutInflater.from(context).inflate(R.layout.item_integral_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IntegralDetailHolder integralDetailHolder = (IntegralDetailHolder) holder;
        ((IntegralDetailHolder) holder).rvIntegralDetail.setText(integralDetailList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return integralDetailList.size();
    }

    public class IntegralDetailHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_integral_detail)
        TextView rvIntegralDetail;
        public IntegralDetailHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
