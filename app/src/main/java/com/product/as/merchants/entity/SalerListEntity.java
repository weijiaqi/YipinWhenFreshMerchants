package com.product.as.merchants.entity;

import java.util.List;

public class SalerListEntity {

    /**
     * data : [{"ID":8,"CreatedAt":"2020-03-23T12:53:37+08:00","UpdatedAt":"2020-03-23T12:53:37+08:00","DeletedAt":null,"sid":461945245349715968,"name":"北京新发地批发市场","branch_name":"店名","logo_url":"saler/461944536877244416/2020-03-23 12:53:35/photo_20200323125233.jpeg","front_url":"saler/461944536877244416/2020-03-23 12:53:35/photo_20200323125148.jpeg","store_url":"saler/461944536877244416/2020-03-23 12:53:35/photo_20200323125228.jpeg","address":"测试地址","major":"水果","secondary":"蔬菜","boss":"易梅","tel":"15826657921","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""},{"ID":9,"CreatedAt":"2020-03-23T21:13:59+08:00","UpdatedAt":"2020-03-23T21:13:59+08:00","DeletedAt":null,"sid":462071167319023616,"name":"北京杰明汇商贸有限公司","branch_name":"","logo_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/462068186573971456/1584969238518538.png","front_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/462068186573971456/1584969238510953.png","store_url":"","address":"新发地","major":"苹果","secondary":"","boss":"王慧东","tel":"18910553320","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""},{"ID":11,"CreatedAt":"2020-03-28T16:25:59+08:00","UpdatedAt":"2020-03-28T16:25:59+08:00","DeletedAt":null,"sid":463810628625113088,"name":"北京锦绣","branch_name":"北京锦绣","logo_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/463805778684289024/2020-03-28 16:26:03/photo_20200328162453.jpeg","front_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/463805778684289024/2020-03-28 16:26:03/photo_20200328162443.jpeg","store_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/463805778684289024/2020-03-28 16:26:03/photo_20200328162422.jpeg","address":"锦绣花园","major":"水果","secondary":"蔬菜","boss":"彭文俊","tel":"18001212784","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""},{"ID":13,"CreatedAt":"2020-04-05T14:26:33+08:00","UpdatedAt":"2020-04-05T14:26:33+08:00","DeletedAt":null,"sid":466679676677398528,"name":"北京宏丰永正商贸有限公司","branch_name":"","logo_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/466671037044498432/1586067992714133.png","front_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/466671037044498432/1586067992700265.png","store_url":"","address":"北京新发地农副产品批发市场海南厅","major":"国产水果,蔬菜,进口水果","secondary":"","boss":"王纪霖","tel":"13811889280","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""},{"ID":19,"CreatedAt":"2020-04-08T13:52:00+08:00","UpdatedAt":"2020-04-08T13:52:00+08:00","DeletedAt":null,"sid":467758144034971648,"name":"北京杰明汇商贸有限公司","branch_name":"","logo_url":"https://www.fruits1688.com/static/saler/jmh.jpg","front_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/467755336921522176/1586325115480867.png","store_url":"","address":"北京市大兴区新发地","major":"水果类","secondary":"","boss":"郭帅成","tel":"15841654617","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""},{"ID":21,"CreatedAt":"2020-04-13T20:50:35+08:00","UpdatedAt":"2020-04-13T20:50:35+08:00","DeletedAt":null,"sid":469675422011891712,"name":"田野计划","branch_name":"","logo_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/469674886168584192/1586782234107059.png","front_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/469674886168584192/1586782234100123.png","store_url":"","address":"新发地","major":"水果","secondary":"","boss":"闫雪刚","tel":"15510953524","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""},{"ID":23,"CreatedAt":"2020-05-10T20:17:31+08:00","UpdatedAt":"2020-05-10T20:17:31+08:00","DeletedAt":null,"sid":479451573127225344,"name":"北京恒彩技高商贸有限公司","branch_name":"","logo_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/479425543922524160/2020-05-10 20:15:55/photo_20200510201541.jpeg","front_url":"https://freshcyber-1257941117.cos.ap-beijing.myqcloud.com/saler/479425543922524160/2020-05-10 20:15:23/photo_20200510201515.jpeg","store_url":"","address":"新发地农产品批发市场","major":"生鲜水果","secondary":"","boss":"赵斌全","tel":"13343265893","auth_type":0,"is_auth":1,"grade":0,"status":1,"ex_num":0,"ex_data":"","memo":""}]
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
         * ID : 8
         * CreatedAt : 2020-03-23T12:53:37+08:00
         * UpdatedAt : 2020-03-23T12:53:37+08:00
         * DeletedAt : null
         * sid : 461945245349715968
         * name : 北京新发地批发市场
         * branch_name : 店名
         * logo_url : saler/461944536877244416/2020-03-23 12:53:35/photo_20200323125233.jpeg
         * front_url : saler/461944536877244416/2020-03-23 12:53:35/photo_20200323125148.jpeg
         * store_url : saler/461944536877244416/2020-03-23 12:53:35/photo_20200323125228.jpeg
         * address : 测试地址
         * major : 水果
         * secondary : 蔬菜
         * boss : 易梅
         * tel : 15826657921
         * auth_type : 0
         * is_auth : 1
         * grade : 0
         * status : 1
         * ex_num : 0
         * ex_data :
         * memo :
         */

        private int ID;
        private String CreatedAt;
        private String UpdatedAt;
        private Object DeletedAt;
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

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String CreatedAt) {
            this.CreatedAt = CreatedAt;
        }

        public String getUpdatedAt() {
            return UpdatedAt;
        }

        public void setUpdatedAt(String UpdatedAt) {
            this.UpdatedAt = UpdatedAt;
        }

        public Object getDeletedAt() {
            return DeletedAt;
        }

        public void setDeletedAt(Object DeletedAt) {
            this.DeletedAt = DeletedAt;
        }

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
