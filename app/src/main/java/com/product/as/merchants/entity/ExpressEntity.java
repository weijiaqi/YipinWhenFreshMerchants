package com.product.as.merchants.entity;

import java.util.List;

public class ExpressEntity {

    /**
     * data : [{"uid":467375376557940736,"mobile":"13522798850","we_chat":"","pwd":"","nick_name":"郭师傅","head_icon":"","grade":0,"is_logistical":2,"is_site":0,"is_delegate":0,"is_service":0,"status":0,"puid":0,"invite_code":"","delivery_code":"A","ex_num":1,"ex_data":"东城区,西城区,崇文区,宣武区,海淀区,朝阳区,丰台区,石景山区,通州区,大兴区,房山区,昌平区,顺义区","memo":""}]
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
         * uid : 467375376557940736
         * mobile : 13522798850
         * we_chat :
         * pwd :
         * nick_name : 郭师傅
         * head_icon :
         * grade : 0
         * is_logistical : 2
         * is_site : 0
         * is_delegate : 0
         * is_service : 0
         * status : 0
         * puid : 0
         * invite_code :
         * delivery_code : A
         * ex_num : 1
         * ex_data : 东城区,西城区,崇文区,宣武区,海淀区,朝阳区,丰台区,石景山区,通州区,大兴区,房山区,昌平区,顺义区
         * memo :
         */

        private long uid;
        private String mobile;
        private String we_chat;
        private String pwd;
        private String nick_name;
        private String head_icon;
        private int grade;
        private int is_logistical;
        private int is_site;
        private int is_delegate;
        private int is_service;
        private int status;
        private int puid;
        private String invite_code;
        private String delivery_code;
        private int ex_num;
        private String ex_data;
        private String memo;

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

        public String getWe_chat() {
            return we_chat;
        }

        public void setWe_chat(String we_chat) {
            this.we_chat = we_chat;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_icon() {
            return head_icon;
        }

        public void setHead_icon(String head_icon) {
            this.head_icon = head_icon;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getIs_logistical() {
            return is_logistical;
        }

        public void setIs_logistical(int is_logistical) {
            this.is_logistical = is_logistical;
        }

        public int getIs_site() {
            return is_site;
        }

        public void setIs_site(int is_site) {
            this.is_site = is_site;
        }

        public int getIs_delegate() {
            return is_delegate;
        }

        public void setIs_delegate(int is_delegate) {
            this.is_delegate = is_delegate;
        }

        public int getIs_service() {
            return is_service;
        }

        public void setIs_service(int is_service) {
            this.is_service = is_service;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPuid() {
            return puid;
        }

        public void setPuid(int puid) {
            this.puid = puid;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getDelivery_code() {
            return delivery_code;
        }

        public void setDelivery_code(String delivery_code) {
            this.delivery_code = delivery_code;
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
