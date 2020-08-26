package com.product.as.merchants.entity;

import java.util.List;

public class RecomPlaceEntity {

    /**
     * data : [{"saler_id":1,"saler_img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue"},{"saler_id":2,"saler_img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue"}]
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
         * saler_id : 1
         * saler_img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue
         */

        private int saler_id;
        private String saler_img;

        public int getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(int saler_id) {
            this.saler_id = saler_id;
        }

        public String getSaler_img() {
            return saler_img;
        }

        public void setSaler_img(String saler_img) {
            this.saler_img = saler_img;
        }
    }
}
