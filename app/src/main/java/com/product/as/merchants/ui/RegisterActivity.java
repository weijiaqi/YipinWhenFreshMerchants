package com.product.as.merchants.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.util.CommonUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.phone)
    EditText phone;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        tvTitles.setText("商家注册");
    }

    @OnClick({R.id.next,R.id.login})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (CommonUtils.CheckPhone(this, phone.getText().toString())) {
                    Bundle  bundle=new Bundle();
                    bundle.putString("phone",phone.getText().toString());
                    startActivity(RegisterNextActivity.class,bundle);
                }
                break;
            case R.id.login:
                startActivity(LoginActivity.class);
                break;
        }
    }



}
