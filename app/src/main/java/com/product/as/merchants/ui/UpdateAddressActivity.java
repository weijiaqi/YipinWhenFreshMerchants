package com.product.as.merchants.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.entity.UpdateAddressEntity;
import com.product.as.merchants.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddressActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.writename)
    EditText writename;
    @Bind(R.id.writephone)
    EditText writephone;
    @Bind(R.id.writeaddress)
    EditText writeaddress;
    @Bind(R.id.saveupdate)
    TextView saveupdate;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update_address;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("地址");
        writename.setText(getIntent().getExtras().getString("rec_name"));
        writephone.setText(getIntent().getExtras().getString("rec_tel"));
        writeaddress.setText(getIntent().getExtras().getString("rec_add"));
        saveupdate.setOnClickListener(v -> {
            if (TextUtils.isEmpty(writename.getText().toString())) {
                ToastUtil.show("姓名不能为空!", 200);
                return;
            }
            if (TextUtils.isEmpty(writephone.getText().toString())) {
                ToastUtil.show("电话不能为空!", 200);
                return;
            }
            if (TextUtils.isEmpty(writeaddress.getText().toString())) {
                ToastUtil.show("地址不能为空!", 200);
                return;
            }

            updateaddress(getIntent().getExtras().getString("group_no"), getIntent().getExtras().getString("rec_name"), getIntent().getExtras().getString("rec_tel"), getIntent().getExtras().getString("rec_add"));
        });
    }

    public void updateaddress(String group_no, String rec_name, String rec_tel, String rec_add) {
        ApiInterfaceTwo.ApiFactory.createApi().updateaddress(group_no, rec_name, rec_tel, rec_add).enqueue(new Callback<UpdateAddressEntity>() {
            @Override
            public void onResponse(Call<UpdateAddressEntity> call, Response<UpdateAddressEntity> response) {
                if (response.body().getFlag() == 1) {
                    Intent intent=new Intent(UpdateAddressActivity.this,ToBeShippedActivity.class);
                    intent.putExtra("name",writename.getText().toString());
                    intent.putExtra("tel",writephone.getText().toString());
                    intent.putExtra("address",writeaddress.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressEntity> call, Throwable t) {

            }
        });
    }

}
