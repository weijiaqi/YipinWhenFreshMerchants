package com.product.as.merchants.entity;

public class AccountBalanceEntity {

    /**
     * balance : {"cny":"0.00","coupon":"","point":"0.00"}
     * flag : 1
     * msg : 成功
     */

    private BalanceBean balance;
    private int flag;
    private String msg;

    public BalanceBean getBalance() {
        return balance;
    }

    public void setBalance(BalanceBean balance) {
        this.balance = balance;
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

    public static class BalanceBean {
        /**
         * cny : 0.00
         * coupon :
         * point : 0.00
         */

        private String cny;
        private String coupon;
        private String point;

        public String getCny() {
            return cny;
        }

        public void setCny(String cny) {
            this.cny = cny;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }
    }
}
