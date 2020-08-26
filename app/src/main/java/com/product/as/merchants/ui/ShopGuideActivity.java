package com.product.as.merchants.ui;


import android.os.Bundle;
import android.view.View;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;

import java.io.IOException;

import butterknife.OnClick;

//开店指导
public class ShopGuideActivity extends BaseActivity {


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shop_guide;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {

    }

    @OnClick({R.id.next})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.next:
                startfinishActivity(StoreInformationActivity.class,null);
                break;
        }
    }
}
