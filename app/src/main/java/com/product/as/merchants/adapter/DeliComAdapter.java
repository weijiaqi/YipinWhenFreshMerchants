package com.product.as.merchants.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.adapter.RecyclerViewHolder;
import com.product.as.merchants.R;
import com.product.as.merchants.entity.ExpressListEntity;
import com.product.as.merchants.util.SpannableUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliComAdapter extends RecycleAdapter<String> {

    public DeliComAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String walletEntity) {
        TextView detailed = holder.getConvertView().findViewById(R.id.detailed);

        if (walletEntity.substring(0, 1).equals("1")) {
            SpannableStringBuilder style = new SpannableStringBuilder(walletEntity);
            style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_2b) ), 0, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            detailed.setText(style);
            detailed.setOnClickListener(view -> {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + walletEntity.substring(0, 11));
                intent.setData(data);
                mContext.startActivity(intent);
            });

        } else {
            detailed.setText(walletEntity);
        }
    }
}
