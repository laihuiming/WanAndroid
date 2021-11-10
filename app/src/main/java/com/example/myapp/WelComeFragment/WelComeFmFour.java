package com.example.myapp.WelComeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Base.BaseFragment;
import com.example.myapp.Constant;
import com.example.myapp.R;

/**
 * Created by laihm on 2021/10/12
 */
public class WelComeFmFour extends BaseFragment {

//    Button btnWelCome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_four, container, false);
//        btnWelCome = view.findViewById(R.id.bt_welcome);
//        btnWelCome.setVisibility(View.VISIBLE);
//        btnWelCome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build(Constant.LOGIN).navigation();
//            }
//        });
        return view;
    }


}
