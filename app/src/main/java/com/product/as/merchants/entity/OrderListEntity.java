package com.product.as.merchants.entity;

import java.util.List;

public class OrderListEntity {

    /**
     * data : [{"uuid":462257124374749184,"order_no":"oCFAMVUIhXQZecgM","order_type":0,"group_id":0,"group_no":"oCFAMVUIhXQZecgM","products_id":0,"saler_id":460903870260846592,"count":1,"order_balance":0.1,"delivery_fee":0,"real_balance":0.1,"geoupno_balance":0,"point_balance":0,"acc_balance":0,"order_time":"2020-03-24 20:49:58","pay_time":"","rec_name":"雷雨","rec_tel":"13802222824","rec_add":"湖北省-武汉市-武昌区友谊大道合伙人大厦2501","rec_post":"","deli_com":"","deli_no":"","deli_status":"","order_status":1,"After_status":"","remark":"","List":[{"single_id":"oCFAMVUIhXQZecgM","products_id":1,"product_name":"南瓜","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 20:23:26/TITANS_20200324_201433.jpg","specs":0,"pro_price":0.1,"pro_oldprice":0.5,"sale_amount":1,"balance":0.1,"saler_id":460903870260846592,"sale_name":"XX"}]}]
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

    private int count;
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
         * uuid : 462257124374749184
         * order_no : oCFAMVUIhXQZecgM
         * order_type : 0
         * group_id : 0
         * group_no : oCFAMVUIhXQZecgM
         * products_id : 0
         * saler_id : 460903870260846592
         * count : 1
         * order_balance : 0.1
         * delivery_fee : 0
         * real_balance : 0.1
         * geoupno_balance : 0
         * point_balance : 0
         * acc_balance : 0
         * order_time : 2020-03-24 20:49:58
         * pay_time :
         * rec_name : 雷雨
         * rec_tel : 13802222824
         * rec_add : 湖北省-武汉市-武昌区友谊大道合伙人大厦2501
         * rec_post :
         * deli_com :
         * deli_no :
         * deli_status :
         * order_status : 1
         * After_status :
         * remark :
         * List : [{"single_id":"oCFAMVUIhXQZecgM","products_id":1,"product_name":"南瓜","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 20:23:26/TITANS_20200324_201433.jpg","specs":0,"pro_price":0.1,"pro_oldprice":0.5,"sale_amount":1,"balance":0.1,"saler_id":460903870260846592,"sale_name":"XX"}]
         */

        private long uuid;
        private String order_no;
        private int order_type;
        private int group_id;
        private String group_no;
        private int products_id;
        private long saler_id;
        private int count;
        private double order_balance;
        private int delivery_fee;
        private double real_balance;
        private int geoupno_balance;
        private int point_balance;
        private int acc_balance;
        private String order_time;
        private String pay_time;
        private String rec_name;
        private String rec_tel;
        private String rec_add;
        private String rec_post;
        private String deli_com;
        private String deli_no;
        private String deli_status;
        private int order_status;
        private String After_status;
        private String remark;

        public String getRec_area() {
            return rec_area;
        }

        public void setRec_area(String rec_area) {
            this.rec_area = rec_area;
        }

        private String rec_area;

        public String getString_order_price() {
            return string_order_price;
        }

        public void setString_order_price(String string_order_price) {
            this.string_order_price = string_order_price;
        }

        public String getString_order_balance() {
            return string_order_balance;
        }

        public void setString_order_balance(String string_order_balance) {
            this.string_order_balance = string_order_balance;
        }

        public int getOrder_count() {
            return order_count;
        }

        public void setOrder_count(int order_count) {
            this.order_count = order_count;
        }

        private String string_order_price;
        private String string_order_balance;
        private int order_count;
        public int getIs_print_fruits() {
            return is_print_fruits;
        }

        public void setIs_print_fruits(int is_print_fruits) {
            this.is_print_fruits = is_print_fruits;
        }

        public int getIs_print_vegetables() {
            return is_print_vegetables;
        }

        public void setIs_print_vegetables(int is_print_vegetables) {
            this.is_print_vegetables = is_print_vegetables;
        }

        private int is_print_fruits;
        private int is_print_vegetables;

        public int getIs_print_ticket() {
            return is_print_ticket;
        }

        public void setIs_print_ticket(int is_print_ticket) {
            this.is_print_ticket = is_print_ticket;
        }

        private int is_print_ticket;
        public String getSaler_name() {
            return saler_name;
        }

        public void setSaler_name(String saler_name) {
            this.saler_name = saler_name;
        }

        private String saler_name;

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        private String order_price;
        private java.util.List<ListBean> List;

        public long getUuid() {
            return uuid;
        }

        public void setUuid(long uuid) {
            this.uuid = uuid;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_no() {
            return group_no;
        }

        public void setGroup_no(String group_no) {
            this.group_no = group_no;
        }

        public int getProducts_id() {
            return products_id;
        }

        public void setProducts_id(int products_id) {
            this.products_id = products_id;
        }

        public long getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(long saler_id) {
            this.saler_id = saler_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getOrder_balance() {
            return order_balance;
        }

        public void setOrder_balance(double order_balance) {
            this.order_balance = order_balance;
        }

        public int getDelivery_fee() {
            return delivery_fee;
        }

        public void setDelivery_fee(int delivery_fee) {
            this.delivery_fee = delivery_fee;
        }

        public double getReal_balance() {
            return real_balance;
        }

        public void setReal_balance(double real_balance) {
            this.real_balance = real_balance;
        }

        public int getGeoupno_balance() {
            return geoupno_balance;
        }

        public void setGeoupno_balance(int geoupno_balance) {
            this.geoupno_balance = geoupno_balance;
        }

        public int getPoint_balance() {
            return point_balance;
        }

        public void setPoint_balance(int point_balance) {
            this.point_balance = point_balance;
        }

        public int getAcc_balance() {
            return acc_balance;
        }

        public void setAcc_balance(int acc_balance) {
            this.acc_balance = acc_balance;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getRec_name() {
            return rec_name;
        }

        public void setRec_name(String rec_name) {
            this.rec_name = rec_name;
        }

        public String getRec_tel() {
            return rec_tel;
        }

        public void setRec_tel(String rec_tel) {
            this.rec_tel = rec_tel;
        }

        public String getRec_add() {
            return rec_add;
        }

        public void setRec_add(String rec_add) {
            this.rec_add = rec_add;
        }

        public String getRec_post() {
            return rec_post;
        }

        public void setRec_post(String rec_post) {
            this.rec_post = rec_post;
        }

        public String getDeli_com() {
            return deli_com;
        }

        public void setDeli_com(String deli_com) {
            this.deli_com = deli_com;
        }

        public String getDeli_no() {
            return deli_no;
        }

        public void setDeli_no(String deli_no) {
            this.deli_no = deli_no;
        }

        public String getDeli_status() {
            return deli_status;
        }

        public void setDeli_status(String deli_status) {
            this.deli_status = deli_status;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getAfter_status() {
            return After_status;
        }

        public void setAfter_status(String After_status) {
            this.After_status = After_status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            /**
             * single_id : oCFAMVUIhXQZecgM
             * products_id : 1
             * product_name : 南瓜
             * pro_pic : https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/459912889642262528/2020-03-24 20:23:26/TITANS_20200324_201433.jpg
             * specs : 0
             * pro_price : 0.1
             * pro_oldprice : 0.5
             * sale_amount : 1
             * balance : 0.1
             * saler_id : 460903870260846592
             * sale_name : XX
             */

            private String single_id;
            private int products_id;
            private String product_name;
            private String pro_pic;
            private int specs;
            private double pro_price;
            private double pro_oldprice;
            private int sale_amount;
            private double balance;
            private long saler_id;
            private String sale_name;

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            private String origin;
            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            private String unit;
            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            private int parent_id;
            public String getSingle_id() {
                return single_id;
            }

            public void setSingle_id(String single_id) {
                this.single_id = single_id;
            }

            public int getProducts_id() {
                return products_id;
            }

            public void setProducts_id(int products_id) {
                this.products_id = products_id;
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

            public int getSpecs() {
                return specs;
            }

            public void setSpecs(int specs) {
                this.specs = specs;
            }

            public double getPro_price() {
                return pro_price;
            }

            public void setPro_price(double pro_price) {
                this.pro_price = pro_price;
            }

            public double getPro_oldprice() {
                return pro_oldprice;
            }

            public void setPro_oldprice(double pro_oldprice) {
                this.pro_oldprice = pro_oldprice;
            }

            public int getSale_amount() {
                return sale_amount;
            }

            public void setSale_amount(int sale_amount) {
                this.sale_amount = sale_amount;
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public long getSaler_id() {
                return saler_id;
            }

            public void setSaler_id(long saler_id) {
                this.saler_id = saler_id;
            }

            public String getSale_name() {
                return sale_name;
            }

            public void setSale_name(String sale_name) {
                this.sale_name = sale_name;
            }
        }
    }
}
