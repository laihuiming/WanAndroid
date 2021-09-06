package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.example.myapp.Base.BaseActivity;
import com.example.myapp.Bean.LoginBean;
import com.example.myapp.Internet.WanAndroidApiService;
import com.example.myapp.Util.AddCookiesInterceptor;
import com.example.myapp.Util.SaveCookiesInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = "/app/LoginActivity")
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_username)
    AppCompatEditText etUsername;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Autowired(name = "username")
    String username;
    @Autowired(name = "password")
    String password;
    @BindView(R.id.cb_remember)
    AppCompatCheckBox cbRemember;

    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (cbRemember.isChecked()){
            username = SPUtils.getInstance().getString("username");
            password = SPUtils.getInstance().getString("password");
        }
        ARouter.getInstance().inject(this);
        if (!(TextUtils.isEmpty(username))) {
            etUsername.setText(username);
            etPassword.setText(password);
        }
    }

    @OnClick({R.id.tv_register, R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                //去注册
                ARouter.getInstance().build(Constant.REGISTER).navigation();
//                startActivityForResult(new Intent(this,RegisterActivity.class),1);
                break;
            case R.id.bt_login:
                if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
                    if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                        Toast.makeText(this, "你用户名密码都空着登个der啊？", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "用户名别空着啊！！！", Toast.LENGTH_SHORT).show();
                    }
                } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    Toast.makeText(this, "密码别空着啊！！！", Toast.LENGTH_SHORT).show();
                } else {
                    SaveCookiesInterceptor.clearCookie(context);//清除本地cookie
                    login();
                }
                break;
        }
    }

    //登录请求
    public void login() {
        Context context = getBaseContext();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new SaveCookiesInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//获取url
                .addConverterFactory(GsonConverterFactory.create())//Gson转换工具
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//拿到接口
        Call<LoginBean> call = wanAndroidApiService.login(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean loginBean = response.body();
                if (loginBean.getErrorCode() != 0) {
                    Toast.makeText(LoginActivity.this, "登录失败：" + loginBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, loginBean.getData().toString());
                    if (cbRemember.isChecked()){//是否记住账号密码
                        SPUtils.getInstance().put("username", etUsername.getText().toString().trim());
                        SPUtils.getInstance().put("password", etPassword.getText().toString().trim());
                    }else {
                        SPUtils.getInstance().clear();
                    }
                    ARouter.getInstance().build(Constant.MAINACTIVITY)
                            .navigation();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "数据请求失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

        }
    }
}