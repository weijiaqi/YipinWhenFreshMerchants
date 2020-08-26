package com.product.as.merchants.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;

import com.product.as.merchants.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    public static final String SUCCESS_STRING = "SUCCESS";

    /**
     * 是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        Pattern p = Pattern.compile("^[1]([3|4|5|6|7|8|9][0-9]{1}|59|58|88|89)[0-9]{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }


    public static boolean CheckPhone(Context context, String name) {

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(context.getString(R.string.login_empty_phone));
            return false;
        }
        if (!CommonUtils.checkMobile(name)) {
            ToastUtil.showShort(context.getString(R.string.please_correct_phone));
            return false;
        }

        return true;
    }



}
