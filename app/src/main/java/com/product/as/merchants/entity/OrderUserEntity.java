package com.product.as.merchants.entity;

public class OrderUserEntity {

    /**
     * data : {"invite_code":"","uid":468503520811687936,"mobile":"13501255256","we_chat":"oPyyR4is7hz4ldUNOVl2ZP4BD8Y4","pwd":"25d55ad283aa400af464c76d713c07ad","nick_name":"赵军","head_icon":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbeAqhWrxcpbMBZzPtz8ibFaOhI9SCfoVHjEfUEfDA0OdGOicmib2G8XzMfGOB3nMeDM4J9mdthniaGA/132","grade":0,"is_logistical":0,"is_site":0,"is_delegate":0,"status":0,"puid":466401268819042304,"ex_num":0,"ex_data":"","memo":""}
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
         * invite_code :
         * uid : 468503520811687936
         * mobile : 13501255256
         * we_chat : oPyyR4is7hz4ldUNOVl2ZP4BD8Y4
         * pwd : 25d55ad283aa400af464c76d713c07ad
         * nick_name : 赵军
         * head_icon : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbeAqhWrxcpbMBZzPtz8ibFaOhI9SCfoVHjEfUEfDA0OdGOicmib2G8XzMfGOB3nMeDM4J9mdthniaGA/132
         * grade : 0
         * is_logistical : 0
         * is_site : 0
         * is_delegate : 0
         * status : 0
         * puid : 466401268819042304
         * ex_num : 0
         * ex_data :
         * memo :
         */

        private String invite_code;
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
        private int status;
        private long puid;
        private int ex_num;
        private String ex_data;
        private String memo;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getPuid() {
            return puid;
        }

        public void setPuid(long puid) {
            this.puid = puid;
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
