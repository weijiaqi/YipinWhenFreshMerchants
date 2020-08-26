package com.product.as.merchants.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.tools.Constant;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.entity.BindCreateEntity;
import com.product.as.merchants.entity.BindInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditingBankInformationActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.detail)
    EditText detail;
    @Bind(R.id.accountbank)
    EditText accountbank;
    @Bind(R.id.Specific)
    EditText Specific;
    @Bind(R.id.householder)
    EditText householder;
    @Bind(R.id.Bind_immediately)
    TextView BindImmediately;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_editing_bank_information;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("编辑银行卡信息");
        bindinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1);
    }

    public void bindinfo(String uid, int account_type) {
        ApiInterfaceThree.ApiFactory.createApi().bindinfo(uid, account_type).enqueue(new Callback<BindInfoEntity>() {
            @Override
            public void onResponse(Call<BindInfoEntity> call, Response<BindInfoEntity> response) {
                if (response.body().getFlag() == 1) {
                    householder.setText(response.body().getData().getAccount_name());
                    detail.setText(response.body().getData().getAccount_no());
                    accountbank.setText(response.body().getData().getBank_name());
                    Specific.setText(response.body().getData().getBank_detail());
                }
            }

            @Override
            public void onFailure(Call<BindInfoEntity> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.Bind_immediately})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.Bind_immediately:
                if (TextUtils.isEmpty(detail.getText().toString())) {
                    ToastUtil.show("请输入银行卡号", 200);
                    return;
                }
                if (TextUtils.isEmpty(accountbank.getText().toString())) {
                    ToastUtil.show("请输入开户行", 200);
                    return;
                }
                if (TextUtils.isEmpty(Specific.getText().toString())) {
                    ToastUtil.show("输入银行具体名称", 200);
                    return;
                }
                if (TextUtils.isEmpty(householder.getText().toString())) {
                    ToastUtil.show("输入户主", 200);
                    return;
                }
                bindedit(getIntent().getExtras().getString("bind_id"), String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, householder.getText().toString(), detail.getText().toString(), accountbank.getText().toString(), Specific.getText().toString());
                break;
        }
    }

    public void bindedit(String bind_id, String uid, int account_type, String account_name, String account_no, String bank_name, String bank_detail) {
        ApiInterfaceThree.ApiFactory.createApi().bindedit(bind_id, uid, account_type, account_name, account_no, bank_name, bank_detail).enqueue(new Callback<BindCreateEntity>() {
            @Override
            public void onResponse(Call<BindCreateEntity> call, Response<BindCreateEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show("更改成功", 200);
                    NoticeObserver.getInstance().notifyObservers(Constants.UPDATEBANK);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BindCreateEntity> call, Throwable t) {

            }
        });
    }
}
