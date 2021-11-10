package com.example.myapp.WelComeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapp.Base.BaseFragment;
import com.example.myapp.R;

/**
 * Created by laihm on 2021/10/12
 */
public class WelComeFmThree extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_three, container, false);
        return view;
    }
}
