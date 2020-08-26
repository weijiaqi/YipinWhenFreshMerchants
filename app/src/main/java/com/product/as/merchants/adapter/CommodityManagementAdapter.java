package com.product.as.merchants.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.product.as.merchants.R;
import com.product.as.merchants.pager.BasePager;
import com.product.as.merchants.util.UiUtils;
import java.util.List;

public class CommodityManagementAdapter extends PagerAdapter {
    private List<BasePager> basePagers;

    private boolean isDestroyItem = true;

    public CommodityManagementAdapter(List<BasePager> basePagers) {
        this.basePagers = basePagers;
    }


    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            container.addView(basePagers.get(position).getView());
        } catch (Exception e) {

        }
        return basePagers.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        if (isDestroyItem && basePagers.size() >3) {
            container.removeView((View) object);
            object = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return UiUtils.getStringArray(R.array.tabs_order)[position];
    }
}


