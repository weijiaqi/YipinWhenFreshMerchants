package com.product.as.merchants.api;

import android.content.res.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uuzuche.lib_zxing.ZApplication;


public class App extends ZApplication {
    private static App globalApp;
    private Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        globalApp = this;
        initData();
    }

    public synchronized static App getContext() {
        return globalApp;
    }

    public static Resources getAppResources() {
        return globalApp.getResources();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 不转换没有 @Expose 注解的字段
        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

    }

    /**
     * 获取不使用注解的gson实例
     *
     * @return
     */
    public synchronized Gson getSimpleGson() {
        if (mGson == null)
            mGson = new Gson();
        return mGson;
    }

    /**
     * 获取Gson 实例
     *
     * @return Gson实例
     */
    public synchronized Gson getGson() {
        if (mGson == null)
            // 不转换没有 @Expose 注解的字段
            mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .create();
        return mGson;
    }
}
