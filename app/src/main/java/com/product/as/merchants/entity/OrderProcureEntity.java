package com.product.as.merchants.entity;

import java.util.List;

public class OrderProcureEntity {

    /**
     * data : [{"product_name":"南非葡萄柚子【特惠10斤】啊啊啊","pro_pic":"xxx","count":6}]
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
         * product_name : 南非葡萄柚子【特惠10斤】啊啊啊
         * pro_pic : xxx
         * count : 6
         */

        private String product_name;
        private String pro_pic;
        private int count;
        private String saler_id;
        private String detail;
        private int is_replenish;
        private String origin;
        private String unit;
        private int is_choice;
        private String products_id;
        private String day;

        public String getPro_price() {
            return pro_price;
        }

        public void setPro_price(String pro_price) {
            this.pro_price = pro_price;
        }

        private String pro_price;

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        private int parent_id;

        public int getIs_replenish() {
            return is_replenish;
        }

        public void setIs_replenish(int is_replenish) {
            this.is_replenish = is_replenish;
        }


        public String getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(String saler_id) {
            this.saler_id = saler_id;
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




        public String getProducts_id() {
            return products_id;
        }

        public void setProducts_id(String products_id) {
            this.products_id = products_id;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }


        public int getIs_choice() {
            return is_choice;
        }

        public void setIs_choice(int is_choice) {
            this.is_choice = is_choice;
        }


        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
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
    }
}
