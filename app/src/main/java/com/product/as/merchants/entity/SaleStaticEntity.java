package com.product.as.merchants.entity;

public class SaleStaticEntity {


    /**
     * data : {"day_tatol":834.06,"day_count":14,"day_delive":6,"tatol":834.06,"count":14,"delive":7}
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
         * day_tatol : 834.06
         * day_count : 14
         * day_delive : 6
         * tatol : 834.06
         * count : 14
         * delive : 7
         */

        private double day_tatol;
        private int day_count;
        private int day_delive;
        private double tatol;
        private int count;
        private int delive;

        public double getDay_tatol() {
            return day_tatol;
        }

        public void setDay_tatol(double day_tatol) {
            this.day_tatol = day_tatol;
        }

        public int getDay_count() {
            return day_count;
        }

        public void setDay_count(int day_count) {
            this.day_count = day_count;
        }

        public int getDay_delive() {
            return day_delive;
        }

        public void setDay_delive(int day_delive) {
            this.day_delive = day_delive;
        }

        public double getTatol() {
            return tatol;
        }

        public void setTatol(double tatol) {
            this.tatol = tatol;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getDelive() {
            return delive;
        }

        public void setDelive(int delive) {
            this.delive = delive;
        }
    }
}
