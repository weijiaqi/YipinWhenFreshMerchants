package com.product.as.merchants.pager;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.util.TimeUtils;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.DistributionAdapter;
import com.product.as.merchants.adapter.OffShelfAdapter;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.api.ApiInterfaceThree;
import com.product.as.merchants.api.ApiInterfaceTwo;
import com.product.as.merchants.entity.AccountBalanceEntity;
import com.product.as.merchants.entity.OrderDeliveryEntity;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.entity.SignupEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//配送清单
public class DistributionPager extends BasePager {
    private TextView dates;
    private RecyclerView rcydistribution;
    private TimePickerView pvTime;
    private DistributionAdapter distributionAdapter;
    List<OrderDeliveryEntity.DataBean> dataBeanList;

    public DistributionPager(Context context) {
        super(context);
    }

    @Override
    public View initView(LayoutInflater layoutInflater) {

        View view = layoutInflater.inflate(R.layout.distribution_pager, null);
        dates = view.findViewById(R.id.date);
        rcydistribution = view.findViewById(R.id.rcydistribution);
        rcydistribution.setLayoutManager(new LinearLayoutManager(context));
        dates = view.findViewById(R.id.date);
        dates.setText(TimeUtils.millisDateString(System.currentTimeMillis()) + ">");

        dates.setOnClickListener(v -> {
            pvTime.show();
        });
        return view;
    }

    @Override
    public <DATA> void initData(int type, DATA data) {

    }


}
