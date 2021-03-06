package com.product.as.merchants.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.PlatformDriverMainAdapter;
import com.product.as.merchants.pager.BasePager;

import com.product.as.merchants.pager.PlatformConstPager;
import com.product.as.merchants.pager.PlatformListofCommoditiesPager;
import com.product.as.merchants.util.UiUtils;
import com.product.as.merchants.view.NoScrollViewPager;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

public class PlatformConstActivity extends BaseActivity  implements ViewPager.OnPageChangeListener{

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    @Bind(R.id.sequencing_tab)
    TabLayout mTabLayout;
    @Bind(R.id.meal_viewpager)
    NoScrollViewPager mViewPager;
    private List<BasePager> mBasePagers;
    private Class[] aClass ;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_platform_const;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        ivBack.setVisibility(View.GONE);
        tvTitles.setText("清单");
        aClass = new Class[]{PlatformConstPager.class, PlatformListofCommoditiesPager.class};
        mBasePagers = new ArrayList<>();
        for (Class aClass1 : aClass) {
            BasePager basePager = createPager(aClass1);
            mBasePagers.add(basePager);
        }

        //----------------------------------------设置标题------------------------------------------
        String[] strings = UiUtils.getStringArray(R.array.order_platformcouriers);
        for (String string : strings) {
            mTabLayout.addTab(mTabLayout.newTab().setText(string));
        }

        PlatformDriverMainAdapter mAdapter = new PlatformDriverMainAdapter(mBasePagers);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        mViewPager.addOnPageChangeListener(this);
    }



    //这里用反射的原因就是方便创建对象
    private <T extends BasePager> T createPager(Class<T> pager) {
        Constructor<T> constructor;
        T page = null;
        try {
            constructor = pager.getConstructor(Context.class);
            page = constructor.newInstance(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Fragment.InstantiationException e) {
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
    protected void onDestroy() {
        super.onDestroy();
        for (BasePager mBasePager : mBasePagers) {
            mBasePager.onDestroy();
        }
    }
}
