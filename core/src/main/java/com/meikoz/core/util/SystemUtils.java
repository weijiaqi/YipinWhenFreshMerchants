package com.meikoz.core.util;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.meikoz.core.MainApplication;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public class SystemUtils {

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    public static final String EN = "en";
    public static final String CN = "cn";

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (@NonNull final IOException e) {
            return false;
        }
    }

    public static boolean isHUAWEI() {
        if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (@NonNull final Exception e) {
            return false;
        }
    }

    //切换应用内语言
    @SuppressLint("NewApi")
    public static void switchLanguage(String language) {
        Resources resources = MainApplication.getInstance().getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (TextUtils.equals(EN, language)) {
            config.setLocale(Locale.ENGLISH);
        } else {
            config.setLocale(Locale.SIMPLIFIED_CHINESE);
        }
        resources.updateConfiguration(config, dm);
    }

    //判断当前应用内语言是否为中文        
    public static boolean getLocalLanguageIsCN() {
        Resources resources = MainApplication.getInstance().getResources();
        Configuration config = resources.getConfiguration();
        Locale locale = config.locale;
        if (locale.equals(Locale.ENGLISH)) {
            return false;
        } else {
            return true;
        }
    }

}
