package com.product.as.merchants.entity;

import java.util.List;

public class UserListEntity {

    /**
     * data : [{"uid":459912889642262528,"mobile":"18510507182","nick_name":"18510507182","role_id":1}]
     * flag : 1
     * msg : 成功
     */

    private int flag;
    private String msg;
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
         * uid : 459912889642262528
         * mobile : 18510507182
         * nick_name : 18510507182
         * role_id : 1
         */

        private long uid;
        private String mobile;
        private String nick_name;
        private int role_id;

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }
    }
}
