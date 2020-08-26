package com.product.as.merchants.entity;

public class HomeLedgerEntity {


    /**
     * data : {"tatol":834.06,"on_credit":0,"on_credit_repay":0,"surplus_on_credit":0}
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
         * tatol : 834.06
         * on_credit : 0
         * on_credit_repay : 0
         * surplus_on_credit : 0
         */

        private double tatol;
        private int on_credit;
        private int on_credit_repay;
        private int surplus_on_credit;

        public double getTatol() {
            return tatol;
        }

        public void setTatol(double tatol) {
            this.tatol = tatol;
        }

        public int getOn_credit() {
            return on_credit;
        }

        public void setOn_credit(int on_credit) {
            this.on_credit = on_credit;
        }

        public int getOn_credit_repay() {
            return on_credit_repay;
        }

        public void setOn_credit_repay(int on_credit_repay) {
            this.on_credit_repay = on_credit_repay;
        }

        public int getSurplus_on_credit() {
            return surplus_on_credit;
        }

        public void setSurplus_on_credit(int surplus_on_credit) {
            this.surplus_on_credit = surplus_on_credit;
        }
    }
}
