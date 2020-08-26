package com.product.as.merchants.entity;

import java.util.List;

public class DeliveryEntity {

    /**
     * data : [{"saler_id":466679676677398528,"product_name":"甜玉米","detail":"  ","origin":"海南/广西/广东","unit":"5斤","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/2020-04-06 17:03:53/1586163814520485.png","count":1,"is_choice":0,"products_id":88,"day":"2020-04-12"},{"saler_id":466679676677398528,"product_name":"刺黄瓜","detail":"  ","origin":"河北","unit":"3斤/份","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/2020-04-06 17:16:19/1586164522499136.png","count":1,"is_choice":0,"products_id":91,"day":"2020-04-12"},{"saler_id":466679676677398528,"product_name":"香菇","detail":" ","origin":"河北","unit":"3斤装","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/2020-04-06 17:17:31/1586164621054905.png","count":1,"is_choice":0,"products_id":92,"day":"2020-04-12"},{"saler_id":466679676677398528,"product_name":"圆茄子","detail":" ","origin":"云南、山东","unit":"3斤/份","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/2020-04-06 18:59:46/TITANS_20200406_184602.jpg","count":2,"is_choice":0,"products_id":113,"day":"2020-04-12"},{"saler_id":466679676677398528,"product_name":"大葱","detail":" ","origin":"山东","unit":"3斤/份","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/2020-04-06 19:07:51/TITANS_20200402_180840.jpg","count":1,"is_choice":0,"products_id":116,"day":"2020-04-12"},{"saler_id":466679676677398528,"product_name":"耙耙柑","detail":" 好吃实惠，单果110-120mm","origin":"四川","unit":"10斤/份","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/1586439427884028.png","count":1,"is_choice":0,"products_id":179,"day":"2020-04-12"}]
     * flag : 1
     * msg : 成功
     */

    private int flag;
    private String msg;
    private List<DataBean> data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * saler_id : 466679676677398528
         * product_name : 甜玉米
         * detail :
         * origin : 海南/广西/广东
         * unit : 5斤
         * pro_pic : https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/466671037044498432/2020-04-06 17:03:53/1586163814520485.png
         * count : 1
         * is_choice : 0
         * products_id : 88
         * day : 2020-04-12
         */

        private long saler_id;
        private String product_name;
        private String detail;
        private String origin;
        private String unit;
        private String pro_pic;
        private int count;
        private int is_choice;
        private int products_id;
        private String day;

        public long getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(long saler_id) {
            this.saler_id = saler_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPro_pic() {
            return pro_pic;
        }

        public void setPro_pic(String pro_pic) {
            this.pro_pic = pro_pic;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getIs_choice() {
            return is_choice;
        }

        public void setIs_choice(int is_choice) {
            this.is_choice = is_choice;
        }

        public int getProducts_id() {
            return products_id;
        }

        public void setProducts_id(int products_id) {
            this.products_id = products_id;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}
