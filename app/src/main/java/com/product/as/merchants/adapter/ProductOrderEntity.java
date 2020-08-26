package com.product.as.merchants.adapter;

import java.util.List;

public class ProductOrderEntity {

    /**
     * data : [{"rec_name":"胡先生","rec_tel":"13683601076","rec_add":"北京市广华新城二区2号1单元1302","product_name":"豇豆","sale_amount":1},{"rec_name":"陈洁雯","rec_tel":"13910125763","rec_add":"北京市少角中街8号院中海城2期2号楼1门102","product_name":"豇豆","sale_amount":1},{"rec_name":"郭莲珍","rec_tel":"13601378857","rec_add":"北京市成寿寺140号院中海城香克林11号楼702室","product_name":"豇豆","sale_amount":1},{"rec_name":"李阳","rec_tel":"18612562875","rec_add":"北京市小营路育慧西里锦绣馨园24楼1809","product_name":"豇豆","sale_amount":1},{"rec_name":"薛莉","rec_tel":"13641216280","rec_add":"北京市莲花池东路9号院3-105","product_name":"豇豆","sale_amount":1},{"rec_name":"李素清","rec_tel":"18518190081","rec_add":"北京市北京市朝阳区六里屯街道晨光家园B区332号楼1单元501","product_name":"豇豆","sale_amount":2},{"rec_name":"陈女士","rec_tel":"18611113728","rec_add":"北京市望京西园三区西门329楼2002","product_name":"豇豆","sale_amount":1}]
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
         * rec_name : 胡先生
         * rec_tel : 13683601076
         * rec_add : 北京市广华新城二区2号1单元1302
         * product_name : 豇豆
         * sale_amount : 1
         */

        private String rec_name;
        private String rec_tel;
        private String rec_add;
        private String product_name;
        private int sale_amount;

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

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getSale_amount() {
            return sale_amount;
        }

        public void setSale_amount(int sale_amount) {
            this.sale_amount = sale_amount;
        }
    }
}
