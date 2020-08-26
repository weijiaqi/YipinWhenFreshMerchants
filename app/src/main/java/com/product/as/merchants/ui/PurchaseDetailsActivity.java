package com.product.as.merchants.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.ProductOrderEntity;
import com.product.as.merchants.adapter.PurchaseDetailsAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.GoodsInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.view.MyDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseDetailsActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.rcyPurchase)
    RecyclerView rcyPurchase;
    PurchaseDetailsAdapter purchaseDetailsAdapter;
    List<ProductOrderEntity.DataBean> dataBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_purchase_details;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("采购详情");
        rcyPurchase.setLayoutManager(new LinearLayoutManager(context));
        rcyPurchase.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));
        get_product_order(getIntent().getExtras().getString("date"), getIntent().getExtras().getString("products_id"),getIntent().getExtras().getString("sid") );
    }


    public void get_product_order(String date, String parent_id, String sid) {
        ApiInterfaceTwo.ApiFactory.createApi().get_product_order(date, parent_id, sid).enqueue(new Callback<ProductOrderEntity>() {
            @Override
            public void onResponse(Call<ProductOrderEntity> call, Response<ProductOrderEntity> response) {
                if (response.body().getFlag() == 1) {
                    dataBeanList.addAll(response.body().getData());
                    if (purchaseDetailsAdapter == null) {
                        purchaseDetailsAdapter = new PurchaseDetailsAdapter(context, R.layout.item_purchasedetails, dataBeanList);
                        rcyPurchase.setAdapter(purchaseDetailsAdapter);
                    } else {
                        purchaseDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductOrderEntity> call, Throwable t) {

            }
        });
    }
}
