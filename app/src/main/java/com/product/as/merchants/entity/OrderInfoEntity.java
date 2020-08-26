package com.product.as.merchants.entity;

import java.util.List;

public class OrderInfoEntity {


    /**
     * data : {"order_no":"ZGOi3UwecFTxP76Vei","order_type":0,"group_id":0,"saler_id":461945245349715968,"group_no":"ZGOi3UwecFTxP76Vei","count":2,"order_balance":9.430000000000001,"pay_time":"2020-03-30 14:37:00","rec_name":"12","rec_tel":"1","rec_add":"北京市-市辖区-东城区1","rec_post":"","deli_com":"","deli_no":"","deli_status":"","order_status":1,"List":[{"single_id":"ZGOi3UwecFTxP76Vei","products_id":52,"product_name":"冰糖心红富士苹果","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/461944536877244416/1585265546682206.png","unit":"斤","pro_price":0.8,"pro_oldprice":1,"sale_amount":1,"balance":0.8,"saler_id":461945245349715968},{"single_id":"ZGOi3UwecFTxP76Vei","products_id":52,"product_name":"红富士90以上","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/461944536877244416/2020-03-24 21:31:42/TITANS_20200324_210235.jpg","unit":"KG","pro_price":8.63,"pro_oldprice":8.63,"sale_amount":1,"balance":8.63,"saler_id":461945245349715968}]}
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
         * order_no : ZGOi3UwecFTxP76Vei
         * order_type : 0
         * group_id : 0
         * saler_id : 461945245349715968
         * group_no : ZGOi3UwecFTxP76Vei
         * count : 2
         * order_balance : 9.430000000000001
         * pay_time : 2020-03-30 14:37:00
         * rec_name : 12
         * rec_tel : 1
         * rec_add : 北京市-市辖区-东城区1
         * rec_post :
         * deli_com :
         * deli_no :
         * deli_status :
         * order_status : 1
         * List : [{"single_id":"ZGOi3UwecFTxP76Vei","products_id":52,"product_name":"冰糖心红富士苹果","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/461944536877244416/1585265546682206.png","unit":"斤","pro_price":0.8,"pro_oldprice":1,"sale_amount":1,"balance":0.8,"saler_id":461945245349715968},{"single_id":"ZGOi3UwecFTxP76Vei","products_id":52,"product_name":"红富士90以上","pro_pic":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/461944536877244416/2020-03-24 21:31:42/TITANS_20200324_210235.jpg","unit":"KG","pro_price":8.63,"pro_oldprice":8.63,"sale_amount":1,"balance":8.63,"saler_id":461945245349715968}]
         */

        private String order_no;
        private int order_type;
        private int group_id;
        private long saler_id;
        private String group_no;
        private int count;
        private double order_balance;
        private String pay_time;
        private String rec_name;
        private String rec_tel;
        private String rec_add;
        private int is_print_fruits;

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

        private int is_print_vegetables;
        private String string_order_price;
        private String string_order_balance;
        public String getRec_area() {
            return rec_area;
        }

        public void setRec_area(String rec_area) {
            this.rec_area = rec_area;
        }

        private String rec_area;
        private String rec_post;
        private String deli_com;
        private String deli_no;
        private String deli_status;
        private int order_status;

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        private String order_price;
        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSaler_name() {
            return saler_name;
        }

        public void setSaler_name(String saler_name) {
            this.saler_name = saler_name;
        }

        private String remark;
        private String saler_name;
        private java.util.List<ListBean> List;

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

        public long getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(long saler_id) {
            this.saler_id = saler_id;
        }

        public String getGroup_no() {
            return group_no;
        }

        public void setGroup_no(String group_no) {
            this.group_no = group_no;
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

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            /**
             * single_id : ZGOi3UwecFTxP76Vei
             * products_id : 52
             * product_name : 冰糖心红富士苹果
             * pro_pic : https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/product/461944536877244416/1585265546682206.png
             * unit : 斤
             * pro_price : 0.8
             * pro_oldprice : 1
             * sale_amount : 1
             * balance : 0.8
             * saler_id : 461945245349715968
             */

            private String single_id;
            private int products_id;
            private String product_name;
            private String pro_pic;
            private String unit;
            private double pro_price;
            private double pro_oldprice;
            private int sale_amount;
            private double balance;
            private long saler_id;

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                parent_id = parent_id;
            }

            private int parent_id;
            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            private String origin;
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

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public double getPro_price() {
                return pro_price;
            }

            public void setPro_price(double pro_price) {
                this.pro_price = pro_price;
            }

            public Double getPro_oldprice() {
                return pro_oldprice;
            }

            public void setPro_oldprice(int pro_oldprice) {
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
        }
    }
}
