package com.product.as.merchants.entity;

public class BindInfoEntity {

    /**
     * data : {"bind_id":"","uid":"","account_type":0,"account_name":"","account_no":"","bank_name":"","bank_detail":"","is_default":0}
     * flag : 2
     * msg : 获取绑定信息错误
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
         * bind_id :
         * uid :
         * account_type : 0
         * account_name :
         * account_no :
         * bank_name :
         * bank_detail :
         * is_default : 0
         */

        private String bind_id;
        private String uid;
        private int account_type;
        private String account_name;
        private String account_no;
        private String bank_name;
        private String bank_detail;
        private int is_default;

        public String getBind_id() {
            return bind_id;
        }

        public void setBind_id(String bind_id) {
            this.bind_id = bind_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getAccount_type() {
            return account_type;
        }

        public void setAccount_type(int account_type) {
            this.account_type = account_type;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_detail() {
            return bank_detail;
        }

        public void setBank_detail(String bank_detail) {
            this.bank_detail = bank_detail;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
