package com.product.as.merchants.api;

import com.product.as.merchants.adapter.ProductOrderEntity;
import com.product.as.merchants.entity.CateListEntity;
import com.product.as.merchants.entity.DeliveryEntity;
import com.product.as.merchants.entity.DeliveryOrderEntity;
import com.product.as.merchants.entity.GetPriceEntity;
import com.product.as.merchants.entity.GoodsAddEntity;
import com.product.as.merchants.entity.GoodsInfoEntity;
import com.product.as.merchants.entity.GoodsListEntity;
import com.product.as.merchants.entity.GoodsStatusEntity;
import com.product.as.merchants.entity.GroupInfoEntity;
import com.product.as.merchants.entity.HomeLedgerEntity;
import com.product.as.merchants.entity.OrderDeliveryEntity;
import com.product.as.merchants.entity.OrderEnsureEntity;
import com.product.as.merchants.entity.OrderInfoEntity;
import com.product.as.merchants.entity.OrderListEntity;
import com.product.as.merchants.entity.OrderListsEntity;
import com.product.as.merchants.entity.OrderProcureEntity;
import com.product.as.merchants.entity.OrderProcuresEntity;
import com.product.as.merchants.entity.OrderUpdateEntity;
import com.product.as.merchants.entity.OrderUserEntity;
import com.product.as.merchants.entity.PlatformListEntity;
import com.product.as.merchants.entity.RecomGoodsEntity;
import com.product.as.merchants.entity.RecomPlaceEntity;
import com.product.as.merchants.entity.SaleStaticEntity;
import com.product.as.merchants.entity.SalerListEntity;
import com.product.as.merchants.entity.SignupEntity;
import com.product.as.merchants.entity.UpdateAddressEntity;
import com.product.as.merchants.entity.UpdateAmountEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceTwo {
    //120.53.17.103      192.144.253.207
    String BASE_URL = "http://192.144.253.207:21000/v1/";

    class ApiFactory {
        private volatile static Object monitor = new Object();
        private static ApiInterfaceTwo apiSingleton;

        public static ApiInterfaceTwo createApi() {
            if (apiSingleton == null) {
                synchronized (monitor) {
                    if (apiSingleton == null) {
                        apiSingleton = RestApi.getInstance().create(BASE_URL, ApiInterfaceTwo.class);
                    }
                }
            }
            return apiSingleton;
        }
    }

    /**
     * 销售统计
     */
    @FormUrlEncoded
    @POST("home/sale_static")
    Call<SaleStaticEntity> sale_static(@Field("saler_id") String saler_id);

    /**
     * 台账
     */
    @FormUrlEncoded
    @POST("home/ledger")
    Call<HomeLedgerEntity> ledger(@Field("saler_id") String saler_id, @Field("day") String day);

    /**
     * 好货推荐
     */
    @FormUrlEncoded
    @POST("home/recom_goods")
    Call<RecomGoodsEntity> recom_goods(@Field("uid") String uid);

    /**
     * 产地推荐
     */
    @FormUrlEncoded
    @POST("home/recom_place")
    Call<RecomPlaceEntity> recom_place(@Field("uid") String uid);


    /**
     * 添加产品
     */
    @FormUrlEncoded
    @POST("goods/add")
    Call<GoodsAddEntity> goodsadd(@Field("product_name") String product_name, @Field("short_name") String short_name, @Field("cata_id") int cata_id, @Field("cata_code") String cata_code, @Field("saler_id") String saler_id, @Field("balance") String balance, @Field("origin") String origin, @Field("unit") String unit, @Field("weight") String weight, @Field("amount") String amount, @Field("icon") String icon, @Field("pics") String pics, @Field("detail_pics") String detail_pics, @Field("detail") String detail, @Field("is_delivery") int is_delivery);

    /**
     * 产品列表
     */
    @FormUrlEncoded
    @POST("goods/lists")
    Call<GoodsListEntity> goodslists(@Field("page") int page, @Field("pro_status") int pro_status, @Field("product_name") String product_name, @Field("saler_id") String saler_id);

    /**
     * 产品上下架
     */
    @FormUrlEncoded
    @POST("goods/status")
    Call<GoodsStatusEntity> goodsstatus(@Field("products_id") int products_id, @Field("pro_status") int pro_status);

    /**
     * 配货清单
     */
    @FormUrlEncoded
    @POST("order/procure")
    Call<OrderProcureEntity> orderprocure(@Field("saler_id") String saler_id, @Field("day") String day);

    /**
     * 确认备货
     */
    @FormUrlEncoded
    @POST("order/update_procure")
    Call<OrderEnsureEntity> orderupdate_procure(@Field("saler_id") String saler_id, @Field("day") String day, @Field("products_id") String products_id, @Field("is_replenish") int is_replenish);



    @FormUrlEncoded
    @POST("goods/update_amount")
    Call<UpdateAmountEntity> update_amount(@Field("products_id") int products_id, @Field("amount") int amount);



    @FormUrlEncoded
    @POST("goods/update_goodsvideo")
    Call<UpdateAmountEntity> update_goodsvideo(@Field("products_id") int products_id, @Field("detail_video") String detail_video);


    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST("order/lists")
    Call<OrderListEntity> orderlists(@Field("saler_id") String saler_id, @Field("status") int status, @Field("page") int page, @Field("day") String day);


    /**
     * 配送清单
     */
    @FormUrlEncoded
    @POST("order/delivery_list")
    Call<OrderDeliveryEntity> orderdelivery(@Field("day") String day, @Field("deli_id") String deli_id);

    /**
     * 商品配送清单
     */
    @FormUrlEncoded
    @POST("order/delivery_products")
    Call<DeliveryEntity> delivery_products(@Field("day") String day, @Field("deli_id") String deli_id);

    /**
     * 类别列表
     */
    @FormUrlEncoded
    @POST("cata/list")
    Call<CateListEntity> catalist(@Field("parent_id") int parent_id);


    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("order/info")
    Call<OrderInfoEntity> orderinfo(@Field("saler_id") String saler_id, @Field("order_no") String order_no);

    /**
     * 团购订单详情
     */
    @FormUrlEncoded
    @POST("order/group_info")
    Call<GroupInfoEntity> ordergroup_info(@Field("order_no") String order_no);

    /**
     * 修改收货地址
     */
    @FormUrlEncoded
    @POST("order/update_address")
    Call<UpdateAddressEntity> updateaddress(@Field("group_no") String group_no, @Field("rec_name") String rec_name, @Field("rec_tel") String rec_tel, @Field("rec_add") String rec_add);

    /**
     * 发货
     */
    @FormUrlEncoded
    @POST("order/deliver_goods")
    Call<OrderUpdateEntity> orderupdate(@Field("saler_id") String saler_id, @Field("group_no") String group_no, @Field("deli_com") String deli_com, @Field("deli_no") String deli_no, @Field("deli_status") int deli_status, @Field("saler_uid") String saler_uid, @Field("saler_user_name") String saler_user_name, @Field("saler_user_phone") String saler_user_phone);

    @FormUrlEncoded
    @POST("order/deliver_goods")
    Call<OrderUpdateEntity> orderupdate2(@Field("saler_id") String saler_id, @Field("group_no") String group_no, @Field("deli_id") String deli_id, @Field("deli_name") String deli_name, @Field("deli_phone") String deli_phone, @Field("deli_status") int deli_status, @Field("saler_uid") String saler_uid, @Field("saler_user_name") String saler_user_name, @Field("saler_user_phone") String saler_user_phone);

    /**
     * 产品详情
     */
    @FormUrlEncoded
    @POST("goods/info")
    Call<GoodsInfoEntity> goodsinfo(@Field("products_id") int products_id);

    /**
     * 更新产品
     */
    @FormUrlEncoded
    @POST("goods/saler_update")
    Call<GoodsAddEntity> goodsupdate(@Field("products_id") int products_id, @Field("product_name") String product_name, @Field("short_name") String short_name, @Field("cata_id") int cata_id, @Field("cata_code") String cata_code, @Field("saler_id") String saler_id, @Field("balance") String balance, @Field("origin") String origin, @Field("unit") String unit, @Field("weight") String weight,  @Field("icon") String icon, @Field("pics") String pics, @Field("detail_pics") String detail_pics, @Field("detail") String detail, @Field("is_delivery") int is_delivery);

    /**
     * 记录打印信息
     */
    @FormUrlEncoded
    @POST("order/update_print")
    Call<OrderEnsureEntity> update_print(@Field("group_no") String group_no, @Field("saler_id") String saler_id, @Field("parent_id") int parent_id);
    /**
     * 记录打印小票信息
     */
    @FormUrlEncoded
    @POST("order/update_print_ticket")
    Call<OrderEnsureEntity> update_print_ticket(@Field("group_no") String group_no, @Field("saler_id") String saler_id);


    /**
     * 配货订单详情
     */
    @FormUrlEncoded
    @POST("order/get_product_order")
    Call<ProductOrderEntity> get_product_order(@Field("day") String day, @Field("products_id") String products_id, @Field("saler_id") String saler_id);


    /**
     * 新增订单凭证
     */
    @FormUrlEncoded
    @POST("order/add_order_voucher")
    Call<OrderUpdateEntity> add_order_voucher(@Field("group_no") String group_no,@Field("voucher") String voucher,@Field("saler_id") String saler_id,@Field("deli_id") String deli_id);


    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST("order/order_lists")
    Call<OrderListsEntity> order_lists(@Field("page") int page, @Field("day") String day, @Field("search") String search);


    @FormUrlEncoded
    @POST("order/platform/lists")
    Call<PlatformListEntity> platform_lists(@Field("page") int page, @Field("day") String day);



    /**
     * 记录打印小票信息
     */
    @FormUrlEncoded
    @POST("order/order_print_ticket")
    Call<OrderEnsureEntity> order_print_ticket(@Field("group_no") String group_no);


    /**
     * 平台配送清单
     */
    @FormUrlEncoded
    @POST("order/order_delivery")
    Call<DeliveryOrderEntity> order_delivery(@Field("day") String day);

    /**
     * 修改司机
     */
    @FormUrlEncoded
    @POST("order/update_deli")
    Call<OrderUpdateEntity> update_deli(@Field("group_no") String groupno,@Field("saler_id")String saler_id,@Field("deli_id")String deliid,@Field("deli_name")String deliname,@Field("deli_phone")String deliphone,@Field("code")String code);

    /**
     * 查询用户信息
     */
    @FormUrlEncoded
    @POST("order/order_user")
    Call<OrderUserEntity> order_user(@Field("group_no") String group_no);

    /**
     * 平台备货清单
     */
    @FormUrlEncoded
    @POST("order/order_procure")
    Call<OrderProcuresEntity> order_procure(@Field("day") String day);

    @FormUrlEncoded
    @POST("order/saler_list")
    Call<SalerListEntity> saler_list(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("goods/get_price")
    Call<GetPriceEntity> get_price(@Field("products_id") int products_id);



    @FormUrlEncoded
    @POST("goods/platform_update")
    Call<GoodsAddEntity> platform_update(@Field("products_id") int products_id, @Field("product_name") String product_name, @Field("short_name") String short_name, @Field("cata_id") int cata_id, @Field("cata_code") String cata_code, @Field("saler_id") String saler_id, @Field("balance") String balance, @Field("origin") String origin, @Field("unit") String unit, @Field("weight") String weight,  @Field("icon") String icon, @Field("pics") String pics, @Field("detail_pics") String detail_pics, @Field("detail") String detail, @Field("is_delivery") int is_delivery,@Field("pro_price") String pro_price,@Field("pro_oldprice") String pro_oldprice);

}
