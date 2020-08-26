package com.product.as.merchants.api;

import android.text.TextUtils;

import com.meikoz.core.api.HttpCacheInterceptor;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    private static RestApi mInstance;
    public static boolean isDebug = true;

    public static synchronized RestApi getInstance() {
        if (mInstance == null)
            mInstance = new RestApi();
        return mInstance;
    }

    public void bug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    // create retrofit singleton
    private Retrofit createApiClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttpClient(isDebug))
                .build();
    }

    // create api service singleton
    public <T> T create(String baseUrl, Class<T> clz) {
        String service_url = "";
        try {
            Field field1 = clz.getField("BASE_URL");
            service_url = (String) field1.get(clz);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return createApiClient(
                TextUtils.isEmpty(service_url) ? baseUrl : service_url).create(clz);
    }

    // create api service baseUrl singleton
    public <T> T create(Class<T> clz) {

        String service_url = "";
        try {
            Field field1 = clz.getField("BASE_URL");
            service_url = (String) field1.get(clz);
            if (TextUtils.isEmpty(service_url)) {
                throw new NullPointerException("base_url is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createApiClient(service_url).create(clz);
    }

    // create okHttpClient singleton
    OkHttpClient createOkHttpClient(boolean debug) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addInterceptor(
                        new TokenInterceptord())
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }




}
