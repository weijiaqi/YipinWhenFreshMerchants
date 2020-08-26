package com.product.as.merchants.entity;

import java.util.List;

public class GoodsListEntity {


    /**
     * data : [{"product_name":"香蕉","short_name":"香蕉","cata_id":9,"cata_code":"10003","saler_id":460903870260846592,"pro_status":1,"specs":1000,"unit":"件","pro_oldprice":10.25,"pro_price":10.25,"weight":100,"amount":10000,"sale_amount":0,"shelftime":"2020-03-23 15:04:34","downtime":"","products_id":19,"icon":"product/459912889642262528/2020-03-23 15:04:38/IMG_1570790054827.jpg","pics":"product/459912889642262528/2020-03-23 15:04:38/photo_20191210151358.jpeg","detail_pics":"product/459912889642262528/2020-03-23 15:04:38/photo_20191210205209.jpeg,product/459912889642262528/2020-03-23 15:04:38/photo_20191210155707.jpeg","detail":"意大利香蕉","new_saler_id":"460903870260846592","saler_name":"11","count":0},{"product_name":"啦啦啦","short_name":"啦啦啦","cata_id":1,"cata_code":"1","saler_id":460903870260846592,"pro_status":1,"specs":10,"unit":"箱","pro_oldprice":100,"pro_price":100,"weight":10000,"amount":1000,"sale_amount":0,"shelftime":"2020-03-23 13:13:51","downtime":"","products_id":18,"icon":"product/459912889642262528/2020-03-23 13:13:55/photo_20191211163253.jpeg","pics":"product/459912889642262528/2020-03-23 13:13:55/photo_20191210205209.jpeg","detail_pics":"product/459912889642262528/2020-03-23 13:13:55/photo_20191210155707.jpeg,product/459912889642262528/2020-03-23 13:13:55/photo_20191210155602.jpeg,product/459912889642262528/2020-03-23 13:13:55/photo_20191210155449.jpeg,product/459912889642262528/2020-03-2","detail":"苹果","new_saler_id":"460903870260846592","saler_name":"11","count":0},{"product_name":"橘子","short_name":"橘子","cata_id":1,"cata_code":"1","saler_id":460903870260846592,"pro_status":3,"specs":10,"unit":"箱","pro_oldprice":10,"pro_price":10,"weight":10,"amount":10,"sale_amount":0,"shelftime":"2020-03-20 22:35:19","downtime":"2020-03-21 12:42:05","products_id":1,"icon":"product/459912889642262528/2020-03-20 16:01:30/photo_20191219165309.jpeg","pics":"product/459912889642262528/2020-03-20 16:01:30/photo_20191219164415.jpeg","detail_pics":"product/459912889642262528/2020-03-20 16:01:30/photo_20191210180446.jpeg,product/459912889642262528/2020-03-20 16:01:30/photo_20191210155707.jpeg,product/459912889642262528/2020-03-20 16:01:30/photo_20191210155602.jpeg,product/459912889642262528/2020-03-2","detail":"乐天","new_saler_id":"460903870260846592","saler_name":"11","count":0}]
     * flag : 1
     * msg : 成功
     */

    private int flag;
    private String msg;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int  count;
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
         * product_name : 香蕉
         * short_name : 香蕉
         * cata_id : 9
         * cata_code : 10003
         * saler_id : 460903870260846592
         * pro_status : 1
         * specs : 1000
         * unit : 件
         * pro_oldprice : 10.25
         * pro_price : 10.25
         * weight : 100
         * amount : 10000
         * sale_amount : 0
         * shelftime : 2020-03-23 15:04:34
         * downtime :
         * products_id : 19
         * icon : product/459912889642262528/2020-03-23 15:04:38/IMG_1570790054827.jpg
         * pics : product/459912889642262528/2020-03-23 15:04:38/photo_20191210151358.jpeg
         * detail_pics : product/459912889642262528/2020-03-23 15:04:38/photo_20191210205209.jpeg,product/459912889642262528/2020-03-23 15:04:38/photo_20191210155707.jpeg
         * detail : 意大利香蕉
         * new_saler_id : 460903870260846592
         * saler_name : 11
         * count : 0
         */

        private String product_name;
        private String short_name;
        private int cata_id;
        private String cata_code;
        private long saler_id;
        private int pro_status;
        private int specs;
        private String unit;
        private double pro_oldprice;
        private double pro_price;
        private int weight;
        private int amount;
        private int sale_amount;
        private String shelftime;
        private String downtime;
        private int products_id;
        private String icon;
        private String pics;
        private String detail_pics;
        private String detail;
        private String new_saler_id;
        private String saler_name;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        private String balance;
        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        private String origin;
        private int count;

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

        public double getPro_oldprice() {
            return pro_oldprice;
        }

        public void setPro_oldprice(double pro_oldprice) {
            this.pro_oldprice = pro_oldprice;
        }

        public double getPro_price() {
            return pro_price;
        }

        public void setPro_price(double pro_price) {
            this.pro_price = pro_price;
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
