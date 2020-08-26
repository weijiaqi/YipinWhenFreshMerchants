package com.product.as.merchants.api;

import com.product.as.merchants.entity.ExpressEntity;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterFaceFour {
    String BASE_URL = "http://192.144.253.207:38703/api/v1/";

    class ApiFactory {
        private volatile static Object monitor = new Object();
        private static ApiInterFaceFour apiSingleton;

        public static ApiInterFaceFour createApi() {
            if (apiSingleton == null) {
                synchronized (monitor) {
                    if (apiSingleton == null) {
                        apiSingleton = RestApi.getInstance().create(BASE_URL, ApiInterFaceFour.class);
                    }
                }
            }
            return apiSingleton;
        }
    }

    @FormUrlEncoded
    @POST("user/express/list")
    Call<ExpressEntity> expresslist(@Field("page") int page, @Field("area") String area);
}
