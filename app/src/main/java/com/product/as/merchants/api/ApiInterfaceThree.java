package com.product.as.merchants.api;

import com.product.as.merchants.entity.AccountBalanceEntity;
import com.product.as.merchants.entity.AccountHisToryEntity;
import com.product.as.merchants.entity.BindCreateEntity;
import com.product.as.merchants.entity.BindInfoEntity;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.WithDeawEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceThree {
    //120.53.17.103      192.144.253.207
    String BASE_URL = "http://192.144.253.207:38721/api/v1/";

    class ApiFactory {
        private volatile static Object monitor = new Object();
        private static ApiInterfaceThree apiSingleton;

        public static ApiInterfaceThree createApi() {
            if (apiSingleton == null) {
                synchronized (monitor) {
                    if (apiSingleton == null) {
                        apiSingleton = RestApi.getInstance().create(BASE_URL, ApiInterfaceThree.class);
                    }
                }
            }
            return apiSingleton;
        }
    }

    /**
     * 获取账户balance
     */
    @FormUrlEncoded
    @POST("account/balance")
    Call<AccountBalanceEntity> accountbalance(@Field("uid") String uid);

    /**
     * 获取账户history
     */
    @FormUrlEncoded
    @POST("account/history")
    Call<AccountHisToryEntity> accounthistory(@Field("uid") String uid, @Field("token_id") int token_id, @Field("page") int page);

    /**
     * 订单收货确定
     */
    @FormUrlEncoded
    @POST("order/ensure")
    Call<OrderEnsureEntity> orderensure(@Field("saler_id") String saler_id, @Field("group_no") String group_no);

    /**
     * 获取绑定信息
     */
    @FormUrlEncoded
    @POST("bind/info/type")
    Call<BindInfoEntity> bindinfo(@Field("uid") String uid, @Field("account_type") int account_type);

    /**
     * 创建绑定
     */
    @FormUrlEncoded
    @POST("bind/create")
    Call<BindCreateEntity> bindcreate(@Field("uid") String uid, @Field("account_type") int account_type, @Field("account_name") String account_name, @Field("account_no") String account_no, @Field("bank_name") String bank_name, @Field("bank_detail") String bank_detail);

    /**
     * 编辑
     */
    @FormUrlEncoded
    @POST("bind/edit")
    Call<BindCreateEntity> bindedit(@Field("bind_id") String bind_id,  @Field("uid") String uid, @Field("account_type") int account_type, @Field("account_name") String account_name, @Field("account_no") String account_no, @Field("bank_name") String bank_name, @Field("bank_detail") String bank_detail);

    /**
     * 申请提现
     */
    @FormUrlEncoded
    @POST("account/withdraw/req")
    Call<WithDeawEntity> withdrawreq(@Field("uid") String uid, @Field("role_id") String role_id, @Field("account_type") int account_type, @Field("account_name") String account_name, @Field("account_no") String account_no, @Field("bank_name") String bank_name, @Field("bank_detail") String bank_detail, @Field("token_id") int token_id, @Field("value") String value);

}
