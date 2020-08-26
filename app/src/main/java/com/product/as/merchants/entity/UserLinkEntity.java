package com.product.as.merchants.entity;

public class UserLinkEntity {

    /**
     * data :
     * flag : 2
     * msg : 商户用户已存在
     */

    private String data;
    private int flag;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
