package com.product.as.merchants.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.AccountAssetsAdapter;
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.entity.AccountBalanceEntity;
import com.product.as.merchants.entity.AccountHisToryEntity;
import com.product.as.merchants.entity.BindInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 账户资产
 */
public class AccountAssetsActivity extends BaseActivity implements NoticeObserver.Observer {

    @Bind(R.id.sum)
    TextView sum;
    @Bind(R.id.rcydate)
    RecyclerView rcydate;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.withdrawal)
    TextView withdrawal;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    private int page = 0;
    private AccountAssetsAdapter accountAssetsAdapter;
    List<AccountHisToryEntity.DataBean> dataBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_account_assets;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v->{finish();});
        NoticeObserver.getInstance().addObserver(this);
        tvTitles.setText("账户资产");

        accountbalance(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
        rcydate.setLayoutManager(new LinearLayoutManager(this));
        accounthistory(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page);
        withdrawal.setOnClickListener(v -> {
            bindinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1);
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            accounthistory(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page);
            refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            accounthistory(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page);
            if (dataBeanList.size() >= 0) {
                rcydate.smoothScrollToPosition(dataBeanList.size());
            }
            refreshLayout.finishLoadMore();
        });
    }

    public void bindinfo(String uid, int account_type) {
        ApiInterfaceThree.ApiFactory.createApi().bindinfo(uid, account_type).enqueue(new Callback<BindInfoEntity>() {
            @Override
            public void onResponse(Call<BindInfoEntity> call, Response<BindInfoEntity> response) {
                if (response.body().getFlag() == 2) {
                    DialogUtils.getInstance().showSimpleDialog(AccountAssetsActivity.this, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                        utils.setCancelable(false);
                        Button cofim = contentView.findViewById(R.id.submit);
                        Button exit = contentView.findViewById(R.id.cancel);
                        TextView content = contentView.findViewById(R.id.content);
                        content.setText("查询到未绑定账户信息,是否进行绑定?");
                        exit.setOnClickListener(v2 -> {
                            utils.close();
                        });

                        cofim.setOnClickListener(v2 -> {
                            JumpActivityUtil.launchActivity(AccountAssetsActivity.this, BindingAlipayActivity.class);
                            utils.close();
                        });
                    });
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("sum", sum.getText().toString());
                    startActivity(CashWithdrawalActivity.class, bundle);
                }
            }

            @Override
            public void onFailure(Call<BindInfoEntity> call, Throwable t) {

            }
        });
    }


    public void accountbalance(String uid) {
        ApiInterfaceThree.ApiFactory.createApi().accountbalance(uid).enqueue(new Callback<AccountBalanceEntity>() {
            @Override
            public void onResponse(Call<AccountBalanceEntity> call, Response<AccountBalanceEntity> response) {
                if (response.body().getFlag() == 1) {
                    sum.setText(response.body().getBalance().getCny());
                }
            }

            @Override
            public void onFailure(Call<AccountBalanceEntity> call, Throwable t) {

            }
        });
    }

    public void accounthistory(String uid, int token_id, int page) {
        ApiInterfaceThree.ApiFactory.createApi().accounthistory(uid, token_id, page).enqueue(new Callback<AccountHisToryEntity>() {
            @Override
            public void onResponse(Call<AccountHisToryEntity> call, Response<AccountHisToryEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (page == 0) {
                        dataBeanList.clear();
                    }
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        dataBeanList.addAll(response.body().getData());
                        if (accountAssetsAdapter == null) {
                            accountAssetsAdapter = new AccountAssetsAdapter(AccountAssetsActivity.this, R.layout.item_accountassets, dataBeanList);
                            rcydate.setAdapter(accountAssetsAdapter);
                        } else {
                            accountAssetsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (page == 1 || page == 0) {
                            accountAssetsAdapter = new AccountAssetsAdapter(AccountAssetsActivity.this, R.layout.item_accountassets, dataBeanList);
                            rcydate.setAdapter(accountAssetsAdapter);
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<AccountHisToryEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public <T> void update(int what, T t) {
        if (what == Constants.UPDATESUM) {
            accountbalance(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
            page = 0;
            accounthistory(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), 1, page);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
