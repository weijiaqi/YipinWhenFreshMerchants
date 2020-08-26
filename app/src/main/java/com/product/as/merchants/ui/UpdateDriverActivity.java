package com.product.as.merchants.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.DistributionAdapter;
import com.product.as.merchants.adapter.UpdateDriverAdapter;
import com.product.as.merchants.api.ApiInterFaceFour;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.ExpressEntity;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.entity.OrderUpdateEntity;
import com.product.as.merchants.entity.SaleStaticEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.DialogUtils;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.view.MyDecoration;
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
 * 更改司机
 */
public class UpdateDriverActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;

    @Bind(R.id.rcydistribution)
    RecyclerView rcydistribution;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 0;
    private UpdateDriverAdapter updateDriverAdapter;
    private List<ExpressEntity.DataBean> dataBeanList=new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update_driver;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("更改司机");
        rcydistribution.setLayoutManager(new LinearLayoutManager(context));
        rcydistribution.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL_LIST));

        ApiInterFaceFour(page, getIntent().getExtras().getString("rec_area"));
    }


    public void ApiInterFaceFour(int page, String area) {
        ApiInterFaceFour.ApiFactory.createApi().expresslist(page, area).enqueue(new Callback<ExpressEntity>() {
            @Override
            public void onResponse(Call<ExpressEntity> call, Response<ExpressEntity> response) {
                if (response.body().getFlag() == 1) {
                    if (dataBeanList.size() > 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(response.body().getData());
                    if (updateDriverAdapter == null) {
                        updateDriverAdapter = new UpdateDriverAdapter(context, R.layout.item_updatedriver, dataBeanList);
                        rcydistribution.setAdapter(updateDriverAdapter);
                    }
                    updateDriverAdapter.notifyDataSetChanged();
                    updateDriverAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            DialogUtils.getInstance().showSimpleDialog(UpdateDriverActivity.this, R.layout.dialog_backup, R.style.dialog, (contentView, utils) -> {
                                utils.setCancelable(false);
                                Button cofim = contentView.findViewById(R.id.submit);
                                Button exit = contentView.findViewById(R.id.cancel);
                                TextView content = contentView.findViewById(R.id.content);
                                content.setText("确定进行修改?");
                                exit.setOnClickListener(v2 -> {
                                    utils.close();
                                });
                                cofim.setOnClickListener(v2 -> {
                                    update_deli(getIntent().getExtras().getString("group_no"), getIntent().getExtras().getString("saler_id"), dataBeanList.get(position).getUid()+"", dataBeanList.get(position).getNick_name(), dataBeanList.get(position).getMobile(), dataBeanList.get(position).getDelivery_code() + "-" + getIntent().getExtras().getString("code"));
                                    utils.close();
                                });
                            });

                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ExpressEntity> call, Throwable t) {

            }
        });
    }

    public void update_deli(String groupno, String saler_id, String uid, String deliname, String deliphone, String code) {
        ApiInterfaceTwo.ApiFactory.createApi().update_deli(groupno,saler_id,uid,deliname,deliphone,code).enqueue(new Callback<OrderUpdateEntity>() {
            @Override
            public void onResponse(Call<OrderUpdateEntity> call, Response<OrderUpdateEntity> response) {
                if (response.body().getFlag()==1){
                    ToastUtil.show(response.body().getMsg().toString(),200);
                    NoticeObserver.getInstance().notifyObservers(Constants.RENSURE);
                    finish();
                }else {
                    ToastUtil.show(response.body().getMsg().toString(),200);
                }
            }

            @Override
            public void onFailure(Call<OrderUpdateEntity> call, Throwable t) {

            }
        });
    }
}
