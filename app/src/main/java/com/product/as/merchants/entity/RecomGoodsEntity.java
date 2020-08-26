package com.product.as.merchants.entity;

import java.util.List;

public class RecomGoodsEntity {

    /**
     * data : [{"product_id":1,"product_img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue","product_name":"南非葡萄柚子"},{"product_id":2,"product_img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue","product_name":"新奇士脐橙"},{"product_id":3,"product_img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue","product_name":"菲律宾凤梨"},{"product_id":4,"product_img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue","product_name":"进口南非葡萄柚子"}]
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
         * product_id : 1
         * product_img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584603021297&di=cc552482522b7d2da7ac6bb07dc4e5b9&imgtype=0&src=http%3A%2F%2Fpic.soutu123.cn%2Felement_origin_min_pic%2F16%2F11%2F21%2F8cfd1d717225396dbc60b32b895fb800.jpg%2521%2Ffw%2F700%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue
         * product_name : 南非葡萄柚子
         */

        private int product_id;
        private String product_img;
        private String product_name;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }
}
