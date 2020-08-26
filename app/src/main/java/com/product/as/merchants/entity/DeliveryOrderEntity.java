package com.product.as.merchants.entity;

import java.util.List;

public class DeliveryOrderEntity {

    /**
     * data : [{"deli_name":"郭师傅","deli_id":467375376557940736},{"deli_name":"赵师傅","deli_id":470216509893910528},{"deli_name":"老赵师傅","deli_id":471125779774709760}]
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
         * deli_name : 郭师傅
         * deli_id : 467375376557940736
         */

        private String deli_name;
        private long deli_id;

        public String getDeli_name() {
            return deli_name;
        }

        public void setDeli_name(String deli_name) {
            this.deli_name = deli_name;
        }

        public long getDeli_id() {
            return deli_id;
        }

        public void setDeli_id(long deli_id) {
            this.deli_id = deli_id;
        }
    }
}
