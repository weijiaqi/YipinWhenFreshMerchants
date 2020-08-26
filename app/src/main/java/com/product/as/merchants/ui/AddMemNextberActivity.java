package com.product.as.merchants.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.SendSmsEntity;
import com.product.as.merchants.entity.UserLinkEntity;
import com.product.as.merchants.entity.WholesalerInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.popuwindow.SelectMemberPopuWindow;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.UiUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemNextberActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.position)
    TextView position;
    @Bind(R.id.rlposition)
    RelativeLayout rlposition;

    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.tv_right)
    TextView tvRight;
    private SelectMemberPopuWindow selectMemberPopuWindow;
    private int role_id;
    View rootView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_mem_nextber;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("成员管理");

        phone.setText(getIntent().getExtras().getString("phone"));
        tvRight.setText("完成");
        tvRight.setTextColor(getResources().getColor(R.color.color_2b));
        rlposition.setOnClickListener(v -> {
            selectMemberPopuWindow = new SelectMemberPopuWindow(this, v2 -> {
                UiUtils.backgroundAlpha(AddMemNextberActivity.this, 1.0f);
                position.setText("管理员");
                role_id = 1;
                selectMemberPopuWindow.dismiss();
            }, v2 -> {
                UiUtils.backgroundAlpha(AddMemNextberActivity.this, 1.0f);
                position.setText("店员");
                role_id = 0;
                selectMemberPopuWindow.dismiss();
            });
            rootView = LayoutInflater.from(this)
                    .inflate(R.layout.activity_main, null);
            selectMemberPopuWindow.showAtLocation(rootView,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        });
        tvRight.setOnClickListener(v -> {
            if (position.getText().toString().equals("职位")) {
                ToastUtil.show("请选择职位", 200);
                return;
            }
            userlink(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), getIntent().getExtras().getString("uid"), role_id);
        });
    }

    public void userlink(String sid, String uid, int role_id) {
        ApiInterface.ApiFactory.createApi().userlink(sid, uid, role_id).enqueue(new Callback<UserLinkEntity>() {
            @Override
            public void onResponse(Call<UserLinkEntity> call, Response<UserLinkEntity> response) {
                if (response.body().getFlag() == 1) {
                    ToastUtil.show(response.body().getMsg(), 20);
                    NoticeObserver.getInstance().notifyObservers(Constants.AddMemNextber);
                    finish();
                } else {
                    ToastUtil.show(response.body().getMsg(), 20);
                }
            }

            @Override
            public void onFailure(Call<UserLinkEntity> call, Throwable t) {

            }
        });
    }

}
