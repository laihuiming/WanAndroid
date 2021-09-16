package com.example.myapp.Mine.Intergral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Bean.IntegralRankBean;
import com.example.myapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laihm on 2021/9/2
 */
public class IntegralRankAdapter extends RecyclerView.Adapter {

    Context context;
    List<IntegralRankBean.DataBean.DatasBean> integralRankList;

    public IntegralRankAdapter(Context context, List<IntegralRankBean.DataBean.DatasBean> integralRankList) {
        this.context = context;
        this.integralRankList = integralRankList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntegralRankHolder(LayoutInflater.from(context).inflate(R.layout.item_integral_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IntegralRankHolder integralRankHolder = (IntegralRankHolder) holder;
        String rank = String.valueOf(integralRankList.get(position).getRank());
        String level = String.valueOf(integralRankList.get(position).getLevel());
        String coinCount = String.valueOf(integralRankList.get(position).getCoinCount());
        ((IntegralRankHolder) holder).tvIntegralRankText.setText(rank+"."+integralRankList.get(position).getUsername()+","+"积分"+coinCount);
        ((IntegralRankHolder) holder).tvIntegralRankLevel.setText("lv "+level);
    }

    @Override
    public int getItemCount() {
        return integralRankList.size();
    }

    public class IntegralRankHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_integral_rank_text)
        TextView tvIntegralRankText;
        @BindView(R.id.tv_integral_rank_level)
        TextView tvIntegralRankLevel;

        public IntegralRankHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
