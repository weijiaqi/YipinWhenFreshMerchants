package com.product.as.merchants.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.AllOrderAdapter;
import com.product.as.merchants.adapter.SalerListAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.OrderListsEntity;
import com.product.as.merchants.entity.SalerListEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.view.MyDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalerListActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.rcyshaped)
    RecyclerView rcyshaped;
    List<SalerListEntity.DataBean> dataBeanList = new ArrayList<>();
    SalerListAdapter salerListAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_saler_list;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("商户列表");
        rcyshaped.setLayoutManager(new LinearLayoutManager(context));
        rcyshaped.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));

        saler_list(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));
    }

    public void saler_list(String uid) {
        ApiInterfaceTwo.ApiFactory.createApi().saler_list(uid).enqueue(new Callback<SalerListEntity>() {
            @Override
            public void onResponse(Call<SalerListEntity> call, Response<SalerListEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (dataBeanList.size() > 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());
                    if (salerListAdapter == null) {
                        salerListAdapter = new SalerListAdapter(SalerListActivity.this, R.layout.item_salerlist, dataBeanList);
                        rcyshaped.setAdapter(salerListAdapter);
                    }
                    salerListAdapter.notifyDataSetChanged();
                    salerListAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            NoticeObserver.getInstance().notifyObservers(Constants.SALERLIST, dataBeanList.get(position).getSid() + "&" + dataBeanList.get(position).getName());
                            finish();
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SalerListEntity> call, Throwable t) {

            }
        });
    }
}
