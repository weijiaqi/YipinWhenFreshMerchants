package com.product.as.merchants.ui;


import android.content.Intent;
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
import com.product.as.merchants.adapter.LecelOneAdapter;
import com.product.as.merchants.adapter.LecelThreeAdapter;
import com.product.as.merchants.adapter.LecelTwoAdapter;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.CateListEntity;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 商品类别
 */
public class CommodityCategoryActivity extends BaseActivity {
    @Bind(R.id.rcylevel1)
    RecyclerView rcylevel1;
    @Bind(R.id.rcylevel2)
    RecyclerView rcylevel2;
    @Bind(R.id.rcylevel3)
    RecyclerView rcylevel3;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    private List<CateListEntity.DataBean> dataBeanList, dataBeanList2, dataBeanList3;
    private LecelOneAdapter lecelOneAdapter;
    private LecelTwoAdapter lecelTwoAdapter;
    private LecelThreeAdapter lecelThreeAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_commodity_category;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("商品类别");
        rcylevel1.setLayoutManager(new LinearLayoutManager(this));
        rcylevel2.setLayoutManager(new LinearLayoutManager(this));
        rcylevel3.setLayoutManager(new LinearLayoutManager(this));
        catalist(0);
    }


    public void catalist(int id) {
        ApiInterfaceTwo.ApiFactory.createApi().catalist(id).enqueue(new Callback<CateListEntity>() {
            @Override
            public void onResponse(Call<CateListEntity> call, Response<CateListEntity> response) {
                if (response.body().getFlag() == 1) {
                    dataBeanList = response.body().getData();
                    lecelOneAdapter = new LecelOneAdapter(CommodityCategoryActivity.this, dataBeanList);
                    rcylevel1.setAdapter(lecelOneAdapter);
                    catalist1(dataBeanList.get(0).getID());
                    lecelOneAdapter.setMyItemClickListener((id1, name, positon) -> {
                        lecelOneAdapter.setSelectPosition(positon);
                        lecelOneAdapter.notifyDataSetChanged();
                        catalist1(dataBeanList.get(positon).getID());
                    });
                }
            }

            @Override
            public void onFailure(Call<CateListEntity> call, Throwable t) {

            }
        });
    }

    public void catalist1(int id) {
        ApiInterfaceTwo.ApiFactory.createApi().catalist(id).enqueue(new Callback<CateListEntity>() {
            @Override
            public void onResponse(Call<CateListEntity> call, Response<CateListEntity> response) {
                if (response.body().getFlag() == 1) {
                    dataBeanList2 = response.body().getData();
                    lecelTwoAdapter = new LecelTwoAdapter(CommodityCategoryActivity.this, dataBeanList2);
                    rcylevel2.setAdapter(lecelTwoAdapter);
                    catalist2(dataBeanList2.get(0).getID());
                    lecelTwoAdapter.setMyItemClickListener((id1, name, positon) -> {
                        lecelTwoAdapter.setSelectPosition(positon);
                        lecelTwoAdapter.notifyDataSetChanged();
                        ApiInterfaceTwo.ApiFactory.createApi().catalist(dataBeanList2.get(positon).getID()).enqueue(new Callback<CateListEntity>() {
                            @Override
                            public void onResponse(Call<CateListEntity> call, Response<CateListEntity> response) {
                                if (response.body().getFlag() == 1) {
                                    dataBeanList3 = response.body().getData();
                                    if (dataBeanList3 == null || dataBeanList3.size() == 0) {
                                        Intent intent = new Intent(CommodityCategoryActivity.this, ReleaseCommoditiesActivity.class);
                                        intent.putExtra("ID", dataBeanList2.get(positon).getID());
                                        intent.putExtra("cata_name", dataBeanList2.get(positon).getCata_name());
                                        intent.putExtra("cata_code", dataBeanList2.get(positon).getCata_code());
                                        startActivity(intent);
                                    } else {
                                        lecelThreeAdapter = new LecelThreeAdapter(CommodityCategoryActivity.this, R.layout.item_lievelone, dataBeanList3);
                                        rcylevel3.setAdapter(lecelThreeAdapter);
                                        lecelThreeAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                                                Intent intent = new Intent(CommodityCategoryActivity.this, ReleaseCommoditiesActivity.class);
                                                intent.putExtra("ID", dataBeanList3.get(position).getID());
                                                intent.putExtra("cata_name", dataBeanList3.get(position).getCata_name());
                                                intent.putExtra("cata_code", dataBeanList3.get(position).getCata_code());
                                                startActivity(intent);
                                            }

                                            @Override
                                            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                                                return false;
                                            }
                                        });
                                    }

                                }else {
                                    Intent intent = new Intent(CommodityCategoryActivity.this, ReleaseCommoditiesActivity.class);
                                    intent.putExtra("ID", dataBeanList2.get(positon).getID());
                                    intent.putExtra("cata_name", dataBeanList2.get(positon).getCata_name());
                                    intent.putExtra("cata_code", dataBeanList2.get(positon).getCata_code());
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<CateListEntity> call, Throwable t) {

                            }
                        });

                    });
                }
            }

            @Override
            public void onFailure(Call<CateListEntity> call, Throwable t) {

            }
        });
    }

    public void catalist2(int id) {
        ApiInterfaceTwo.ApiFactory.createApi().catalist(id).enqueue(new Callback<CateListEntity>() {
            @Override
            public void onResponse(Call<CateListEntity> call, Response<CateListEntity> response) {
                if (response.body().getFlag() == 1) {
                    dataBeanList3 = response.body().getData();

                    lecelThreeAdapter = new LecelThreeAdapter(CommodityCategoryActivity.this, R.layout.item_lievelone, dataBeanList3);
                    rcylevel3.setAdapter(lecelThreeAdapter);
                    lecelThreeAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            Intent intent = new Intent(CommodityCategoryActivity.this, ReleaseCommoditiesActivity.class);
                            intent.putExtra("ID", dataBeanList3.get(position).getID());
                            intent.putExtra("cata_name", dataBeanList3.get(position).getCata_name());
                            intent.putExtra("cata_code", dataBeanList3.get(position).getCata_code());
                            startActivity(intent);
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CateListEntity> call, Throwable t) {

            }
        });
    }


}
