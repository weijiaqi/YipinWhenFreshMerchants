package com.product.as.merchants.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.entity.BindCreateEntity;
import com.product.as.merchants.entity.BindInfoEntity;
import com.product.as.merchants.entity.SendSmsEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BindingAlipayActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;

    @Bind(R.id.rlbank)
    RelativeLayout rlbank;

    @Bind(R.id.detail)
    TextView detail;
    @Bind(R.id.llbank)
    LinearLayout llbank;
    @Bind(R.id.Bind_immediately)
    TextView BindImmediately;

    @Bind(R.id.accountbank)
    EditText accountbank;
    @Bind(R.id.Specific)
    EditText Specific;
    @Bind(R.id.householder)
    EditText householder;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_binding_alipay;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("绑定");
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
                bindcreate(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, householder.getText().toString(), detail.getText().toString(), accountbank.getText().toString(), Specific.getText().toString());
                break;
        }
    }


    public void bindcreate(String uid, int account_type, String account_name, String account_no, String bank_name, String bank_detail) {
        ApiInterfaceThree.ApiFactory.createApi().bindcreate(uid, account_type, account_name, account_no, bank_name, bank_detail).enqueue(new Callback<BindCreateEntity>() {
            @Override
            public void onResponse(Call<BindCreateEntity> call, Response<BindCreateEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show("绑定银行卡成功", 200);

            }
        }

        @Override
        public void onFailure (Call < BindCreateEntity > call, Throwable t){

        }
    });
}
}
