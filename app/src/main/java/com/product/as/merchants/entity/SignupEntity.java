package com.product.as.merchants.entity;

public class SignupEntity {


    /**
     * data :
     * flag : 1
     * msg : 成功
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
