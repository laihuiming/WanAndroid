package com.example.myapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapp.Base.BaseActivity;
import com.example.myapp.Bean.RegisterBean;
import com.example.myapp.Internet.WanAndroidApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = "/app/RegisterActivity")
public class RegisterActivity extends BaseActivity {


    private static final String TAG = "RegisterActivity";
    @BindView(R.id.et_register_username)
    AppCompatEditText etRegisterUsername;
    @BindView(R.id.et_register_password)
    AppCompatEditText etRegisterPassword;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.tv_return_login)
    TextView tvReturnLogin;
    @BindView(R.id.et_register_repassword)
    AppCompatEditText etRegisterRepassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
    }


    @OnClick({R.id.bt_register, R.id.tv_return_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                if (TextUtils.isEmpty(etRegisterUsername.getText().toString().trim())) {
                    if (TextUtils.isEmpty(etRegisterPassword.getText().toString().trim())) {
                        Toast.makeText(this, "????????????????????????????????????der??????", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } else if (TextUtils.isEmpty(etRegisterPassword.getText().toString().trim())) {
                    Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etRegisterRepassword.getText().toString().trim())) {
                    Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                } else if (!(etRegisterPassword.getText().toString().trim().equals(etRegisterRepassword.getText().toString().trim()))) {
                    Toast.makeText(this, "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    register();
                }
                break;
            case R.id.tv_return_login:
//                String username = etRegisterUsername.getText().toString().trim();
//                String password = etRegisterPassword.getText().toString().trim();
//                Toast.makeText(this, ""+username+"/"+password, Toast.LENGTH_SHORT).show();
//                ARouter.getInstance().build(Constant.LOGIN)
//                        .withString("username", username)
//                        .withString("password", password)
//                        .navigation();
//                finish();
                ARouter.getInstance().build(Constant.LOGIN)
                        .navigation();
                break;
        }
    }

    public void register() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BaseUrl)//??????url
                .addConverterFactory(GsonConverterFactory.create())//Gson????????????
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        WanAndroidApiService wanAndroidApiService = retrofit.create(WanAndroidApiService.class);//????????????
        Observable<RegisterBean> observable = wanAndroidApiService.register(etRegisterUsername.getText().toString().trim(), etRegisterPassword.getText().toString().trim(), etRegisterRepassword.getText().toString().trim());
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterBean registerBean) {
                        if (registerBean.getErrorCode() != 0) {
                            Toast.makeText(RegisterActivity.this, "???????????????" + registerBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, registerBean.getData().toString());
                            ARouter.getInstance().build(Constant.LOGIN)
                                    .withString("username", etRegisterUsername.getText().toString().trim())
                                    .withString("password", etRegisterPassword.getText().toString().trim())
                                    .navigation();
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}