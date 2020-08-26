package com.product.as.merchants.api;

import com.product.as.merchants.entity.ExpressEntity;
import com.product.as.merchants.entity.ExpressListEntity;
import com.product.as.merchants.entity.GoodsInfoEntity;
import com.product.as.merchants.entity.LoginEntity;
import com.product.as.merchants.entity.SendSmsEntity;
import com.product.as.merchants.entity.SignupEntity;
import com.product.as.merchants.entity.UserLinkEntity;
import com.product.as.merchants.entity.UserListEntity;
import com.product.as.merchants.entity.UserSearchEntity;
import com.product.as.merchants.entity.WholeSalerEntity;
import com.product.as.merchants.entity.WholesalerInfoEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
       //120.53.17.103      192.144.253.207
    String BASE_URL = "http://192.144.253.207:38703/api/v1/";
    String BASE_URL2 = "http://192.144.253.207:38702/version/check";

    class ApiFactory {
        private volatile static Object monitor = new Object();
        private static ApiInterface apiSingleton;

        public static ApiInterface createApi() {
            if (apiSingleton == null) {
                synchronized (monitor) {
                    if (apiSingleton == null) {
                        apiSingleton = RestApi.getInstance().create(BASE_URL, ApiInterface.class);
                    }
                }
            }
            return apiSingleton;
        }
    }

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/signup")
    Call<SendSmsEntity> register(@Field("mobile") String mobile,  @Field("code") String code,@Field("pwd") String pwd);
    /**
     * 发送短信
     */
    @FormUrlEncoded
    @POST("user/sendsms")
    Call<SignupEntity> sendsms(@Field("mobile") String mobile);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/signin")
    Call<LoginEntity> signin(@Field("mobile") String mobile,@Field("pwd") String pwd);

    /**
     * 创建批发商户
     */

    @FormUrlEncoded
    @POST("wholesaler/create")
    Call<WholeSalerEntity> wholesalercreate(@Field("uid") String uid,@Field("name") String name, @Field("branch_name") String branch_name, @Field("logo_url") String logo_url, @Field("front_url") String front_url, @Field("store_url") String store_url, @Field("address") String address, @Field("major") String major, @Field("secondary") String secondary, @Field("boss") String boss, @Field("tel") String tel);


    @FormUrlEncoded
    @POST("wholesaler/profile/create")
    Call<WholeSalerEntity> wholesalerprofilecreate(@Field("uid") String uid,@Field("saler_id") String SalerID, @Field("legal_name") String legal_name, @Field("legal_no") String legal_no, @Field("corp_url") String corp_url, @Field("licence_url") String licence_url, @Field("legal_pic") String legal_pic, @Field("legal_back_pic") String legal_back_pic, @Field("op_pic") String op_pic, @Field("op_back_pic") String op_back_pic);


    /**
     * 物流人员信息
     */
    @FormUrlEncoded
    @POST("user/express/list")
    Call<ExpressListEntity> expresslist(@Field("page") int page,@Field("area") String area);


    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("user/pwd/reset")
    Call<SendSmsEntity> pwdreset(@Field("uid") String uid,@Field("old_pwd") String old_pwd,@Field("pwd") String pwd);

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("wholesaler/info")
    Call<WholesalerInfoEntity> wholesalerinfo(@Field("sid") String sid);

    /**
     *  管理商户用户-关联
     */
    @FormUrlEncoded
    @POST("wholesaler/user/link")
    Call<UserLinkEntity> userlink(@Field("saler_id") String saler_id, @Field("uid") String uid, @Field("role_id") int role_id);

    /**
     * 管理商户用户-取消关联
     */
    @FormUrlEncoded
    @POST("wholesaler/user/unlink")
    Call<UserLinkEntity> userunlink(@Field("saler_id") String saler_id,@Field("uid") String uid);

    /**
     * 管理商户用户-用户列表
     */
    @FormUrlEncoded
    @POST("wholesaler/user/list")
    Call<UserListEntity> userlist(@Field("saler_id") String saler_id);

    /**
     *
     */
    @FormUrlEncoded
    @POST("user/search")
    Call<UserSearchEntity> usersearch(@Field("mobile") String mobile, @Field("page") int page);
}
