package com.product.as.merchants.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.product.as.merchants.api.App;


/**
 * Created by Administrator on 2017\11\14 0014.
 */

public class UiUtils {
    private UiUtils() {
        throw new AssertionError("无法实例化该类");
    }

    /**
     * 获取到字符数组
     *
     * @param tabNames 字符数组的id
     */
    public static String[] getStringArray(@ArrayRes int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    //这里采用资源注解，如传递参数与资源注解不符合则会报错
    public static int getColor(@ColorRes int color) {
        return getResource().getColor(color);
    }

    @NonNull //当返回值为null时会出现警告
    public static String getText(@StringRes int str) {
        return getResource().getString(str);
    }

    //这里采用资源注解，如传递参数与资源注解不符合则会报错
    public static Drawable getDrawable(@DrawableRes int drawable) {
        return getResource().getDrawable(drawable);
    }

    public static Resources getResource() {
        return App.getContext().getResources();
    }

    public static Context getContext() {
        return App.getContext();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * dip转换px
     */
    public static int dip2px(Activity context, float dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    /**
     * dip转换px
     */
    public static int sp2px(int dip) {
        final float scale = getResource().getDisplayMetrics().scaledDensity;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */

    public static int px2sp(int px) {
        final float scale = getResource().getDisplayMetrics().scaledDensity;
        return (int) (px / scale + 0.5f);
    }

    public static int getScreenWidth() {
        return getResource().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getResource().getDisplayMetrics().heightPixels;
    }


    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param
     * @param
     * @param
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }


    /**
     * 强制隐藏软键盘
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
    }


    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     * @param v
     */
    public static void backgroundAlpha(Activity context, float v) {
        WindowManager.LayoutParams lp =context.getWindow().getAttributes();
        lp.alpha = v; //0.0-1.0
        context.getWindow().setAttributes(lp);
        context. getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
