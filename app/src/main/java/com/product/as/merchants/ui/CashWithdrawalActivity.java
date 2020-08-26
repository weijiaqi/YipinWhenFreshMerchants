package com.product.as.merchants.ui;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.entity.BindInfoEntity;
import com.product.as.merchants.entity.WithDeawEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.JumpActivityUtil;
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

//提现列表
public class CashWithdrawalActivity extends BaseActivity implements NoticeObserver.Observer {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind({R.id.name})
    TextView name;
    @Bind(R.id.pay_Account)
    EditText pay_Account;
    @Bind(R.id.Cardnumber)
    TextView Cardnumber;
    @Bind(R.id.Openingbank)
    TextView Openingbank;
    @Bind(R.id.Specificbanks)
    TextView Specificbanks;
    @Bind(R.id.Accountname)
    TextView Accountname;
    @Bind(R.id.edit)
    TextView edit;
    private String bind_id;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_cash_withdrawal;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        NoticeObserver.getInstance().addObserver(this);
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("提现");
        edit.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            bundle1.putString("bind_id", bind_id);
            startActivity(EditingBankInformationActivity.class, bundle1);
        });
        name.setText(getIntent().getExtras().getString("sum"));
        pay_Account.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        bindinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1);
    }

    public void bindinfo(String uid, int account_type) {
        ApiInterfaceThree.ApiFactory.createApi().bindinfo(uid, account_type).enqueue(new Callback<BindInfoEntity>() {
            @Override
            public void onResponse(Call<BindInfoEntity> call, Response<BindInfoEntity> response) {
                if (response.body().getFlag() == 1) {
                    bind_id = response.body().getData().getBind_id();
                    Accountname.setText(response.body().getData().getAccount_name());
                    Cardnumber.setText(response.body().getData().getAccount_no());
                    Openingbank.setText(response.body().getData().getBank_name());
                    Specificbanks.setText(response.body().getData().getBank_detail());
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
                if (TextUtils.isEmpty(pay_Account.getText().toString())) {
                    ToastUtil.show("请输入提现金额", 200);
                    return;
                }
                if (Double.parseDouble(pay_Account.getText().toString()) > Double.parseDouble(name.getText().toString())) {
                    ToastUtil.show("提现金额不能大于总余额", 200);
                    return;
                }
                bindinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), String.valueOf(SharedPreferencesUtils.getSp(Constants.ROLEID, "")), 1, Accountname.getText().toString(), Cardnumber.getText().toString(), Openingbank.getText().toString(), Specificbanks.getText().toString(), 1, pay_Account.getText().toString());
                break;
        }
    }

    public void bindinfo(String uid, String role_id, int account_type, String account_name, String account_no, String bank_name, String bank_detail, int token_id, String balue) {
        ApiInterfaceThree.ApiFactory.createApi().withdrawreq(uid, role_id, account_type, account_name, account_no, bank_name, bank_detail, token_id, balue).enqueue(new Callback<WithDeawEntity>() {
            @Override
            public void onResponse(Call<WithDeawEntity> call, Response<WithDeawEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show(response.body().getMsg().toString(), 200);
                    finish();
                    NoticeObserver.getInstance().notifyObservers(Constants.UPDATESUM);
                }
            }

            @Override
            public void onFailure(Call<WithDeawEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public <T> void update(int what, T t) {
        if (what == Constants.UPDATEBANK) {
            bindinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }
}
