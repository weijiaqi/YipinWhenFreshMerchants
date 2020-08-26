package com.product.as.merchants.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.AddMemberAdapter;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.UserSearchEntity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.ToastUtil;
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
 * 添加成员
 */
public class AddMemberActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.txsearch)
    TextView txsearch;
    @Bind(R.id.rcyremmber)
    RecyclerView rcyremmber;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.tv_right)
    TextView tvRight;
    private int page = 0;
    private List<UserSearchEntity.DataBean> dataBeanList = new ArrayList<>();
    private AddMemberAdapter addMemberAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        rcyremmber.setLayoutManager(new LinearLayoutManager(this));
        ivBack.setOnClickListener(v -> {
            finish();
        });

        tvTitles.setText("成员添加");
        txsearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(search.getText().toString())) {
                ToastUtil.show("请输入手机号!", 200);
                return;
            }
            usersearch(search.getText().toString(), page);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (!TextUtils.isEmpty(search.getText().toString())) {
                page = 0;
                usersearch(search.getText().toString(), page);
                refreshLayout.finishRefresh();
            }

        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (!TextUtils.isEmpty(search.getText().toString())) {
                page++;
                usersearch(search.getText().toString(), page);
                if (dataBeanList.size() >= 0) {
                    rcyremmber.smoothScrollToPosition(dataBeanList.size());
                }
                refreshLayout.finishLoadMore();
            }

        });

    }

    public void usersearch(String mobile, int page) {
        ApiInterface.ApiFactory.createApi().usersearch(mobile, page).enqueue(new Callback<UserSearchEntity>() {
            @Override
            public void onResponse(Call<UserSearchEntity> call, Response<UserSearchEntity> response) {
                if (page == 0) {
                    dataBeanList.clear();
                }
                if (response.body().getData() != null && response.body().getData().size() > 0) {
                    dataBeanList.addAll(response.body().getData());
                    if (addMemberAdapter == null) {
                        addMemberAdapter = new AddMemberAdapter(context, R.layout.item_addmember, dataBeanList);
                        rcyremmber.setAdapter(addMemberAdapter);
                    } else {
                        addMemberAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (page == 1 || page == 0) {
                        addMemberAdapter = new AddMemberAdapter(context, R.layout.item_addmember, dataBeanList);
                        rcyremmber.setAdapter(addMemberAdapter);
                    }
                }
                addMemberAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", dataBeanList.get(position).getNick_name());
                        bundle.putString("phone", dataBeanList.get(position).getMobile());
                        bundle.putString("uid", dataBeanList.get(position).getUid()+"");
                        JumpActivityUtil.launchActivity(AddMemberActivity.this,AddMemNextberActivity.class,bundle);
                    }

                    @Override
                    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<UserSearchEntity> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
