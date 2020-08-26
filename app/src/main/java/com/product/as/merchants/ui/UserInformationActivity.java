package com.product.as.merchants.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderUserEntity;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.view.CircleImageView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInformationActivity extends BaseActivity {

    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.headpic)
    CircleImageView headpic;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user_information;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("用户信息");

        order_user(getIntent().getExtras().getString("groupno"));
    }

    public void order_user(String groupno) {
        ApiInterfaceTwo.ApiFactory.createApi().order_user(groupno).enqueue(new Callback<OrderUserEntity>() {
            @Override
            public void onResponse(Call<OrderUserEntity> call, Response<OrderUserEntity> response) {
                if (response.body().getFlag() == 1) {
                    GlideUtils.loadImgWithGlide(response.body().getData().getHead_icon(), headpic);
                    name.setText(response.body().getData().getNick_name());
                    phone.setText(response.body().getData().getMobile());
                    phone.setOnClickListener(v->{
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + response.body().getData().getMobile());
                        intent.setData(data);
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<OrderUserEntity> call, Throwable t) {

            }
        });
    }
}
