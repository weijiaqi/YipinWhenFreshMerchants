package com.product.as.merchants.entity;

public class GoodsInfoEntity {

    /**
     * data : {"product_name":"菠萝","short_name":"菠萝","cata_id":10,"cata_code":"01008","saler_id":460903870260846592,"pro_status":3,"specs":0,"unit":"100箱","pro_oldprice":0.1,"pro_price":0.1,"weight":2,"amount":1000,"sale_amount":0,"shelftime":"2020-03-24 21:34:07","downtime":"2020-03-24 21:36:19","origin":"新疆","products_id":6,"icon":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 21:34:06/1584781988262.jpg","pics":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 21:34:06/IMG_20200321_171307.jpg","detail_pics":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 21:34:06/mmexport1584889124527.jpg","detail":"好吃可甜","new_saler_id":"460903870260846592","saler_name":"11","count":1}
     * flag : 1
     * msg : 成功
     */

    private DataBean data;
    private int flag;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * product_name : 菠萝
         * short_name : 菠萝
         * cata_id : 10
         * cata_code : 01008
         * saler_id : 460903870260846592
         * pro_status : 3
         * specs : 0
         * unit : 100箱
         * pro_oldprice : 0.1
         * pro_price : 0.1
         * weight : 2
         * amount : 1000
         * sale_amount : 0
         * shelftime : 2020-03-24 21:34:07
         * downtime : 2020-03-24 21:36:19
         * origin : 新疆
         * products_id : 6
         * icon : https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 21:34:06/1584781988262.jpg
         * pics : https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 21:34:06/IMG_20200321_171307.jpg
         * detail_pics : https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 21:34:06/mmexport1584889124527.jpg
         * detail : 好吃可甜
         * new_saler_id : 460903870260846592
         * saler_name : 11
         * count : 1
         */

        private String product_name;
        private String short_name;
        private int cata_id;
        private String cata_code;
        private long saler_id;
        private int pro_status;
        private int specs;
        private String unit;

        public int getIs_delivery() {
            return is_delivery;
        }

        public void setIs_delivery(int is_delivery) {
            this.is_delivery = is_delivery;
        }

        private int is_delivery;
        public double getString_pro_oldprice() {
            return string_pro_oldprice;
        }

        public void setString_pro_oldprice(double string_pro_oldprice) {
            this.string_pro_oldprice = string_pro_oldprice;
        }

        public double getString_pro_price() {
            return string_pro_price;
        }

        public void setString_pro_price(double string_pro_price) {
            this.string_pro_price = string_pro_price;
        }

        public double getString_balance() {
            return string_balance;
        }

        public void setString_balance(double string_balance) {
            this.string_balance = string_balance;
        }

        private double string_pro_oldprice;
        private double string_pro_price;
        private double string_balance;
        private int weight;
        private int amount;
        private int sale_amount;
        private String shelftime;
        private String downtime;
        private String origin;
        private int products_id;
        private String icon;
        private String pics;
        private String detail_pics;
        private String detail;
        private String new_saler_id;
        private String saler_name;

        public String getDetail_video() {
            return detail_video;
        }

        public void setDetail_video(String detail_video) {
            this.detail_video = detail_video;
        }

        private String detail_video;
        private int count;

        public String getCata_name() {
            return cata_name;
        }

        public void setCata_name(String cata_name) {
            this.cata_name = cata_name;
        }

        private String cata_name;
        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public int getCata_id() {
            return cata_id;
        }

        public void setCata_id(int cata_id) {
            this.cata_id = cata_id;
        }

        public String getCata_code() {
            return cata_code;
        }

        public void setCata_code(String cata_code) {
            this.cata_code = cata_code;
        }

        public long getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(long saler_id) {
            this.saler_id = saler_id;
        }

        public int getPro_status() {
            return pro_status;
        }

        public void setPro_status(int pro_status) {
            this.pro_status = pro_status;
        }

        public int getSpecs() {
            return specs;
        }

        public void setSpecs(int specs) {
            this.specs = specs;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }



        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getSale_amount() {
            return sale_amount;
        }

        public void setSale_amount(int sale_amount) {
            this.sale_amount = sale_amount;
        }

        public String getShelftime() {
            return shelftime;
        }

        public void setShelftime(String shelftime) {
            this.shelftime = shelftime;
        }

        public String getDowntime() {
            return downtime;
        }

        public void setDowntime(String downtime) {
            this.downtime = downtime;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getProducts_id() {
            return products_id;
        }

        public void setProducts_id(int products_id) {
            this.products_id = products_id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public String getDetail_pics() {
            return detail_pics;
        }

        public void setDetail_pics(String detail_pics) {
            this.detail_pics = detail_pics;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getNew_saler_id() {
            return new_saler_id;
        }

        public void setNew_saler_id(String new_saler_id) {
            this.new_saler_id = new_saler_id;
        }

        public String getSaler_name() {
            return saler_name;
        }

        public void setSaler_name(String saler_name) {
            this.saler_name = saler_name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
