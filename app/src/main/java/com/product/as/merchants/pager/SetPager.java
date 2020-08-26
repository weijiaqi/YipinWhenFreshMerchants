package com.product.as.merchants.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.meikoz.core.AppManager;
import com.product.as.merchants.R;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.LoginActivity;
import com.product.as.merchants.ui.UpdatePwdActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.SharedPreferencesUtils;

public class SetPager extends BasePager {
    private SuperTextView updatepwd;
    private TextView edit;
    public SetPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.set_pager, null);
        updatepwd=view.findViewById(R.id.updatepwd);
        edit=view.findViewById(R.id.exit);
        edit.setOnClickListener(v->{
            SharedPreferencesUtils.saveSp(Constants.UID, "");
            SharedPreferencesUtils.saveSp(Constants.SID, "");
            JumpActivityUtil.launchActivity(context, LoginActivity.class);
            AppManager.getAppManager().finishAllActivity();
        });
        updatepwd.setOnClickListener(v->{
            JumpActivityUtil.launchActivity(context, UpdatePwdActivity.class);
        });
        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }
}
