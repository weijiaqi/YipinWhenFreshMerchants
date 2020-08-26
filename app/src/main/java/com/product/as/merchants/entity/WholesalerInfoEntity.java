package com.product.as.merchants.entity;

public class WholesalerInfoEntity {

    /**
     * data : {"sid":459951839221542912,"name":"XXX商贸","branch_name":"","logo_url":"http://logo.jpg","front_url":"http://front.jpg","store_url":"","address":"北京新发地88号","major":"生鲜","secondary":"进口水果","boss":"王总","tel":"138314asd","auth_type":0,"is_auth":0,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""}
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
         * sid : 459951839221542912
         * name : XXX商贸
         * branch_name :
         * logo_url : http://logo.jpg
         * front_url : http://front.jpg
         * store_url :
         * address : 北京新发地88号
         * major : 生鲜
         * secondary : 进口水果
         * boss : 王总
         * tel : 138314asd
         * auth_type : 0
         * is_auth : 0
         * grade : 0
         * status : 1
         * ex_num : 0
         * ex_data :
         * memo :
         */

        private long sid;
        private String name;
        private String branch_name;
        private String logo_url;
        private String front_url;
        private String store_url;
        private String address;
        private String major;
        private String secondary;
        private String boss;
        private String tel;
        private int auth_type;
        private int is_auth;
        private int grade;
        private int status;
        private int ex_num;
        private String ex_data;
        private String memo;

        public long getSid() {
            return sid;
        }

        public void setSid(long sid) {
            this.sid = sid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getFront_url() {
            return front_url;
        }

        public void setFront_url(String front_url) {
            this.front_url = front_url;
        }

        public String getStore_url() {
            return store_url;
        }

        public void setStore_url(String store_url) {
            this.store_url = store_url;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getSecondary() {
            return secondary;
        }

        public void setSecondary(String secondary) {
            this.secondary = secondary;
        }

        public String getBoss() {
            return boss;
        }

        public void setBoss(String boss) {
            this.boss = boss;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getAuth_type() {
            return auth_type;
        }

        public void setAuth_type(int auth_type) {
            this.auth_type = auth_type;
        }

        public int getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(int is_auth) {
            this.is_auth = is_auth;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getEx_num() {
            return ex_num;
        }

        public void setEx_num(int ex_num) {
            this.ex_num = ex_num;
        }

        public String getEx_data() {
            return ex_data;
        }

        public void setEx_data(String ex_data) {
            this.ex_data = ex_data;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
