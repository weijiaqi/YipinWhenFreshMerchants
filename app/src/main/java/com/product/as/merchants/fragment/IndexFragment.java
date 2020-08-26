package com.product.as.merchants.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meikoz.core.base.BaseFragment;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.MainActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.RecomGoodsAdapter;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.api.App;
import com.product.as.merchants.entity.HomeLedgerEntity;
import com.product.as.merchants.entity.RecomGoodsEntity;
import com.product.as.merchants.entity.RecomPlaceEntity;
import com.product.as.merchants.entity.SaleStaticEntity;
import com.product.as.merchants.entity.WholesalerInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.ui.ReleaseCommoditiesActivity;
import com.product.as.merchants.util.GlideUtils;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.view.CircleImageView;
import com.product.as.merchants.view.MultipleStatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexFragment extends BaseFragment {
    @Bind(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_titles)
    TextView tv_titles;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.storename)
    TextView storename;
    @Bind(R.id.day_tatol)
    TextView dayTatol;
    @Bind(R.id.day_count)
    TextView dayCount;
    @Bind(R.id.day_delive)
    TextView dayDelive;
    @Bind(R.id.tatol)
    TextView tatol;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.delive)
    TextView delive;
    @Bind(R.id.rcymenu)
    RecyclerView rcymenu;
    @Bind(R.id.tatol2)
    TextView tatol2;
    @Bind(R.id.on_credit)
    TextView onCredit;
    @Bind(R.id.on_credit_repay)
    TextView onCreditRepay;
    @Bind(R.id.surplus_on_credit)
    TextView surplusOnCredit;
    @Bind(R.id.banner)
    ConvenientBanner banner;
    @Bind(R.id.date)
    TextView dates;
    @Bind(R.id.Commodityrelease)
    TextView Commodityrelease;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.headpic)
    CircleImageView headpic;
    @Bind(R.id.llshipped)
    LinearLayout llshipped;
    private RecomGoodsAdapter recomGoodsAdapter;
    private List<String> images;
    private TimePickerView pvTime;

    @Override
    protected int getLayoutResource() {
        return R.layout.index_fragment;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) throws IOException {
        initTimePicker();
        iv_back.setVisibility(View.GONE);
        tv_titles.setText("一码当鲜");
//        ivRight.setBackgroundResource(R.mipmap.sweepcode);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcymenu.setLayoutManager(layoutManager);

        //销售统计
        sale_static(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
        //台账
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()) + ">");
        ledger(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), TimeUtils.millisDateString(System.currentTimeMillis()));

        //好货推荐
        recom_goods(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));

        //产地推荐
        recom_place(String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")));

        dates.setOnClickListener(v -> {
            pvTime.show();
        });

        Commodityrelease.setOnClickListener(v -> {
            JumpActivityUtil.launchActivity(mContext, ReleaseCommoditiesActivity.class);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            //销售统计
            sale_static(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
            dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()) + ">");
            ledger(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), TimeUtils.millisDateString(System.currentTimeMillis()));
            refreshLayout.finishRefresh();
        });

        wholesalerinfo(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));

        llshipped.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getNavigationBar().selectTab(2,false);
        });
    }

    public void wholesalerinfo(String sid) {
        ApiInterface.ApiFactory.createApi().wholesalerinfo(sid).enqueue(new Callback<WholesalerInfoEntity>() {
            @Override
            public void onResponse(Call<WholesalerInfoEntity> call, Response<WholesalerInfoEntity> response) {
                if (response.body().getFlag() == 1) {
                    storename.setText(response.body().getData().getName());
                    Log.e("getLogo_url()", response.body().getData().getLogo_url());
                    SharedPreferencesUtils.saveSp(Constants.USERNAME, response.body().getData().getName());
                    SharedPreferencesUtils.saveSp(Constants.TEL, response.body().getData().getTel());
                    GlideUtils.loadImgWithGlide(response.body().getData().getLogo_url(), headpic);
                }
            }

            @Override
            public void onFailure(Call<WholesalerInfoEntity> call, Throwable t) {

            }
        });
    }

    public void sale_static(String sid) {
        ApiInterfaceTwo.ApiFactory.createApi().sale_static(sid).enqueue(new Callback<SaleStaticEntity>() {
            @Override
            public void onResponse(Call<SaleStaticEntity> call, Response<SaleStaticEntity> response) {
                if (response.body().getFlag() == 1) {
                    dayTatol.setText(response.body().getData().getDay_tatol() + "");
                    dayCount.setText(response.body().getData().getDay_count() + "");
                    dayDelive.setText(response.body().getData().getDay_delive() + "");
                    tatol.setText(response.body().getData().getTatol() + "");
                    count.setText(response.body().getData().getCount() + "");
                    delive.setText(response.body().getData().getDelive() + "");
                }
            }

            @Override
            public void onFailure(Call<SaleStaticEntity> call, Throwable t) {

            }
        });
    }


    public void ledger(String sid, String date) {
        ApiInterfaceTwo.ApiFactory.createApi().ledger(sid, date).enqueue(new Callback<HomeLedgerEntity>() {
            @Override
            public void onResponse(Call<HomeLedgerEntity> call, Response<HomeLedgerEntity> response) {
                if (response.body().getFlag() == 1) {
                    tatol2.setText(response.body().getData().getTatol() + "");
                    onCredit.setText(response.body().getData().getOn_credit() + "");
                    onCreditRepay.setText(response.body().getData().getOn_credit_repay() + "");
                    surplusOnCredit.setText(response.body().getData().getSurplus_on_credit() + "");
                }
            }

            @Override
            public void onFailure(Call<HomeLedgerEntity> call, Throwable t) {

            }
        });
    }


    public void recom_goods(String uid) {
        ApiInterfaceTwo.ApiFactory.createApi().recom_goods(uid).enqueue(new Callback<RecomGoodsEntity>() {
            @Override
            public void onResponse(Call<RecomGoodsEntity> call, Response<RecomGoodsEntity> response) {
                if (response.body().getFlag() == 1) {
                    List<RecomGoodsEntity.DataBean> dataBeanList = response.body().getData();
                    recomGoodsAdapter = new RecomGoodsAdapter(mContext, R.layout.item_recomgood, dataBeanList);
                    rcymenu.setAdapter(recomGoodsAdapter);
                }
            }

            @Override
            public void onFailure(Call<RecomGoodsEntity> call, Throwable t) {

            }
        });
    }

    public void recom_place(String uid) {
        ApiInterfaceTwo.ApiFactory.createApi().recom_place(uid).enqueue(new Callback<RecomPlaceEntity>() {
            @Override
            public void onResponse(Call<RecomPlaceEntity> call, Response<RecomPlaceEntity> response) {
                if (response.body().getFlag() == 1) {
                    List<RecomPlaceEntity.DataBean> dataBeanList = response.body().getData();
                    images = new ArrayList<>();
                    images.clear();

                    for (RecomPlaceEntity.DataBean bannersBeanLists : dataBeanList) {
                        images.add(bannersBeanLists.getSaler_img());
                    }

                    banner.setPages(() -> new LocalImageHolderView(), images)
                            //设置指示器是否可见
                            .setPointViewVisible(true)
                            //设置自动切换（同时设置了切换时间间隔）
                            .startTurning(3000)
                            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                            .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                            //设置指示器的方向（左、中、右）
                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

                }
            }

            @Override
            public void onFailure(Call<RecomPlaceEntity> call, Throwable t) {

            }
        });
    }


    //为了方便改写，来实现复杂布局的切换
    private class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.mipmap.ic_default_adimage);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_default_adimage);
            Glide.with(App.getContext()).load(data).apply(requestOptions).into(imageView);
        }


    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(mContext, (date, v) -> {
            dates.setText(getTime(date) + ">");
            ledger(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), getTime(date));
        })
                .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(view -> Log.i("pvTime", "onCancelClickListener"))
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
}
