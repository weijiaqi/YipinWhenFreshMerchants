package com.product.as.merchants.platform;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.meikoz.core.base.BaseFragment;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.CommodityManagementAdapter;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.pager.BasePager;
import com.product.as.merchants.pager.OffShelfPager;
import com.product.as.merchants.pager.OnShelvesPager;

import com.product.as.merchants.pager.PlatformCommodityPayAllPager;
import com.product.as.merchants.ui.SalerListActivity;
import com.product.as.merchants.util.JumpActivityUtil;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.UiUtils;
import com.product.as.merchants.view.NoScrollViewPager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//商品管理
public class PlatformCommodityFragment extends BaseFragment implements ViewPager.OnPageChangeListener,NoticeObserver.Observer {
    @Bind(R.id.sequencing_tab)
    TabLayout mTabLayout;
    @Bind(R.id.meal_viewpager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.search)
    EditText search;
    private List<BasePager> mBasePagers;

    private Class[] aClass = {PlatformCommodityPayAllPager.class, OnShelvesPager.class, OffShelfPager.class};

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_platform_commodity;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) throws IOException {
        NoticeObserver.getInstance().addObserver(this);
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("商品管理");
        ivRight.setBackgroundResource(R.mipmap.mores);
        ivRight.setOnClickListener(v -> {
            JumpActivityUtil.launchActivity(mContext, SalerListActivity.class);
        });
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mBasePagers = new ArrayList<>();
        for (Class aClass1 : aClass) {
            BasePager basePager = createPager(aClass1);
            mBasePagers.add(basePager);
        }

        //----------------------------------------设置标题------------------------------------------
        String[] strings = UiUtils.getStringArray(R.array.tabs_order);
        for (String string : strings) {
            mTabLayout.addTab(mTabLayout.newTab().setText(string));
        }

        CommodityManagementAdapter mAdapter = new CommodityManagementAdapter(mBasePagers);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        mViewPager.addOnPageChangeListener(this);

        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                NoticeObserver.getInstance().notifyObservers(Constants.SEARCHKeyword, search.getText().toString());
                UiUtils.hideSoftKeyboard(getActivity());
            }
            return false;
        });
    }


    //这里用反射的原因就是方便创建对象
    private <T extends BasePager> T createPager(Class<T> pager) {
        Constructor<T> constructor;
        T page = null;
        try {
            constructor = pager.getConstructor(Context.class);
            page = constructor.newInstance(getActivity());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (BasePager mBasePager : mBasePagers) {
            mBasePager.onDestroy();
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }

    @Override
    public <T> void update(int what, T t) {
        if (what ==Constants.SALERLIST){
            String name = (String) t;
            String nikename = name.substring(name.indexOf("&") + 1, name.length());  //获取名字
            tvTitles.setText(nikename);
        }
    }
}
