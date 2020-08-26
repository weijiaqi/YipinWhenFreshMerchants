package com.product.as.merchants.entity;

public class GetPriceEntity {

    /**
     * data : {"products_id":352,"pro_oldprice":"5.40","pro_price":"3.45","balance":"3.00"}
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
         * products_id : 352
         * pro_oldprice : 5.40
         * pro_price : 3.45
         * balance : 3.00
         */

        private int products_id;
        private String pro_oldprice;
        private String pro_price;
        private String balance;

        public int getProducts_id() {
            return products_id;
        }

        public void setProducts_id(int products_id) {
            this.products_id = products_id;
        }

        public String getPro_oldprice() {
            return pro_oldprice;
        }

        public void setPro_oldprice(String pro_oldprice) {
            this.pro_oldprice = pro_oldprice;
        }

        public String getPro_price() {
            return pro_price;
        }

        public void setPro_price(String pro_price) {
            this.pro_price = pro_price;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
