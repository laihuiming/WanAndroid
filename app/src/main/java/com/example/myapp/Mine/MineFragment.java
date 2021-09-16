package com.example.myapp.Mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Base.BaseFragment;
import com.example.myapp.Constant;
import com.example.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.bt_gaode_map)
    Button btGaodeMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({R.id.bt_gaode_map})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_gaode_map:
            ARouter.getInstance().build(Constant.GAODEMAP).navigation();
        }
    }
}
