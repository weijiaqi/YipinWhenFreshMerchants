package com.product.as.merchants.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.meikoz.core.AppManager;
import com.meikoz.core.base.BaseActivity;
import com.meikoz.core.util.MD5Util;
import com.product.as.merchants.MainActivity;
import com.product.as.merchants.PlatformlevelMainActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.LoginEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.CommonUtils;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.login)
    TextView login;
    @Bind(R.id.check_create)
    CheckBox checkCreate;
    @Bind(R.id.privacy)
    TextView privacy;
    private final int LOCATION_CODE = 10002;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && null != action && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        String token = String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, ""));

        if (token != null && !TextUtils.isEmpty(token)) {
            if (SharedPreferencesUtils.getInt(Constants.ISSERVICE, 0) == 100) {
                startfinishActivity(PlatformlevelMainActivity.class, null);
                AppManager.getAppManager().finishAllActivity();
            } else {
                if (SharedPreferencesUtils.getSp(Constants.ROLEID, "").equals("2") || SharedPreferencesUtils.getSp(Constants.ROLEID, "").equals("200")) {
                    startfinishActivity(CourierMainActivity.class, null);
                    AppManager.getAppManager().finishAllActivity();
                } else if (!String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")).equals("-1") && String.valueOf(SharedPreferencesUtils.getSp(Constants.ISAUTH, "")).equals("1")) {
                    startfinishActivity(MainActivity.class, null);
                    AppManager.getAppManager().finishAllActivity();
                } else {
                    tvTitles.setText("登录");
                }
            }
        } else {
            tvTitles.setText("登录");
        }
    }

    private void requestPermissions() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_CODE);
        }
    }
    @OnClick({R.id.register, R.id.login, R.id.privacy})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.login:
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    ToastUtil.show("请输入手机号!", 25);
                    return;
                }
                if (phone.getText().toString().length() != 11) {
                    ToastUtil.show("请输入正确的手机号!", 25);
                    return;
                }
                if (!CommonUtils.checkMobile(phone.getText().toString())) {
                    ToastUtil.show(String.valueOf(R.string.please_correct_phone), 25);
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText().toString())) {
                    ToastUtil.show("请输入密码!", 25);
                    return;
                }
                if (!checkCreate.isChecked()) {
                    ToastUtil.showShort("是否同意用户协议");
                    return;
                }
                signin(phone.getText().toString(), MD5Util.md5(pwd.getText().toString()));
                break;
            case R.id.privacy:
                Intent intent = new Intent(this, AgentWebActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void signin(String phone, String pwd) {
        ApiInterface.ApiFactory.createApi().signin(phone, pwd).enqueue(new Callback<LoginEntity>() {
            @Override
            public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                if (response.body().getFlag() == 1) {
                    SharedPreferencesUtils.putInt(Constants.ISSERVICE, response.body().getData().getIs_service());
                    SharedPreferencesUtils.saveSp(Constants.UID, response.body().getData().getUid() + "");
                    SharedPreferencesUtils.saveSp(Constants.JWT_OKEN, response.body().getData().getJwt_token());
                    SharedPreferencesUtils.saveSp(Constants.SID, response.body().getData().getSid() + "");
                    SharedPreferencesUtils.saveSp(Constants.ISAUTH, response.body().getData().getIs_auth() + "");
                    SharedPreferencesUtils.saveSp(Constants.ROLEID, response.body().getData().getRole_id() + "");
                    if (response.body().getData().getIs_service() == 100) {
                        startfinishActivity(PlatformlevelMainActivity.class, null);
                        AppManager.getAppManager().finishAllActivity();
                    } else {
                        if (response.body().getData().getRole_id() == 2 || response.body().getData().getRole_id() == 200) {   //物流
                            startfinishActivity(CourierMainActivity.class, null);
                            AppManager.getAppManager().finishAllActivity();
                        } else if (response.body().getData().getSid() != -1 && response.body().getData().getIs_auth() == 1) {
                            startfinishActivity(MainActivity.class, null);
                            AppManager.getAppManager().finishAllActivity();
                        } else if (response.body().getData().getSid() > 0 && response.body().getData().getIs_auth() == 0) {
                            //正在审核
                            startActivity(UnderReviewActivity.class);
                        } else {
                            startfinishActivity(StoreInformationActivity.class, null);
                        }
                    }
                } else {
                    ToastUtil.showShort(response.body().getMsg());
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoginEntity> call, Throwable t) {

            }
        });
    }
}
