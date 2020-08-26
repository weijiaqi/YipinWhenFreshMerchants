package com.product.as.merchants.entity;

import java.util.List;

public class OrderDeliveryEntity {


    /**
     * data : [{"group_no":"479514679157202944","rec_name":"徐小姐","rec_tel":"15810055998","rec_add":"北京市-市辖区-海淀区五道口华清嘉园小区13号楼909","rec_area":"北京市-市辖区-海淀区","deli_com":"绿宝香瓜(中)1件,西红柿1件,秋黄瓜1件,长茄1件,金针菇2件,山竹（4A)1件","deli_name":"郭师傅","status":3,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479632871963762688","rec_name":"赵杨","rec_tel":"13811927815","rec_add":"北京市-市辖区-海淀区知春路蓟门农工商公司","rec_area":"北京市-市辖区-海淀区","deli_com":"妃子笑荔枝现货1件,山竹（4A)1件,普吉岛迷你小凤梨1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479654008361852928","rec_name":"赵芳芳","rec_tel":"13911059312","rec_add":"北京市-市辖区-丰台区建欣苑6里2号楼1单元1302室","rec_area":"北京市-市辖区-丰台区","deli_com":"怡颗莓蓝莓1件,大台农芒果1件,海南黄皮木瓜1件,丹东99草莓盒装1件,百香果（中）1件,进口白心火龙果  中果1件,西州蜜25号1件,普吉岛迷你小凤梨1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479670351182176256","rec_name":"赵玲","rec_tel":"13910160951","rec_add":"北京市-市辖区-丰台区西府颐园万和园6号楼1单元804","rec_area":"北京市-市辖区-丰台区","deli_com":"白心新土豆1件,贝贝南瓜1件,芦笋1件,绿宝香瓜(中)1件,山竹（4A)1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479686893827530752","rec_name":"孙燕","rec_tel":"13911995433","rec_add":"北京市-市辖区-丰台区北甲地路2号院玺萌鹏苑3号楼10","rec_area":"北京市-市辖区-丰台区","deli_com":"妃子笑荔枝现货1件,普吉岛迷你小凤梨3件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479713390147674112","rec_name":"刘悦","rec_tel":"13011895191","rec_add":"北京市-市辖区-西城区太平桥大街8号西城晶华1-1-502","rec_area":"北京市-市辖区-西城区","deli_com":"妃子笑荔枝现货1件,新西兰金奇异果1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479725936053329920","rec_name":"宋艳彦","rec_tel":"18510211557","rec_add":"北京市-市辖区-石景山区鲁谷路双锦园10号楼6门1102","rec_area":"北京市-市辖区-石景山区","deli_com":"红富士苹果80-85mm1件,新西兰金奇异果1件,绿宝香瓜(中)1件,糯玉米1件,四季豆1件,西红柿2件,口蘑1件,白心新土豆1件,妃子笑荔枝现货1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479745493535039488","rec_name":"郭女士","rec_tel":"13811373039","rec_add":"北京市-市辖区-西城区太平桥大街8号西城晶华2号楼2单元606","rec_area":"北京市-市辖区-西城区","deli_com":"怡颗莓蓝莓5件,庞各庄L600小西瓜1件,菲律宾凤梨1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479747696433831936","rec_name":"王美丽","rec_tel":"15510202281","rec_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479775801349251072","rec_name":"高","rec_tel":"13601086221","rec_add":"北京市-市辖区-海淀区永丰基地永翔北路5号","rec_area":"北京市-市辖区-海淀区","deli_com":"芦笋1件,贝贝南瓜1件,西红柿1件,散菜花1件,杏鲍菇1件,甜玉米1件,红葱头1件,白心新土豆1件,豌豆1件,荷兰小黄瓜1件,怡颗莓蓝莓1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479804131033227264","rec_name":"李茜","rec_tel":"13901228025","rec_add":"北京市-市辖区-海淀区长春桥路5号新起点嘉园6号楼709","rec_area":"北京市-市辖区-海淀区","deli_com":"大葱1件,秋黄瓜1件,油菜1件,丹东99草莓盒装1件,西州蜜25号1件,新鲜黄瓜1件,芹菜1件,庞各庄L600小西瓜1件,丰水梨1件,普吉岛迷你小凤梨1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479812468751736832","rec_name":"周芳竹","rec_tel":"18101333843","rec_add":"北京市-市辖区-丰台区鸿业兴园一区北一门","rec_area":"北京市-市辖区-丰台区","deli_com":"妃子笑荔枝现货1件,国产香蕉1件,大台农芒果1件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479831842837504000","rec_name":"王卓异","rec_tel":"18601362220","rec_add":"北京市-市辖区-海淀区北京市海淀区彩和坊路11号华一控股大厦7层","rec_area":"北京市-市辖区-海淀区","deli_com":"妃子笑荔枝现货1件,网红闹闹椰奶冻2件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""},{"group_no":"479869889364893696","rec_name":"李先生","rec_tel":"13811350465","rec_add":"北京市-市辖区-丰台区御槐园24号楼1单元703","rec_area":"北京市-市辖区-丰台区","deli_com":"皇冠梨（苹果梨）1件,红富士苹果80-85mm1件,尖椒2件,团生菜2件,散叶生菜1件,杏鲍菇1件,香菜3件,柿子椒2件,西红柿1件,小葱3件,新鲜黄瓜2件","deli_name":"郭师傅","status":2,"saler_id":466679676677398528,"saler_uid":468209586071937000,"saler_user_name":"小武","saler_user_phone":"15801336013","order_type":0,"code":""}]
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
         * group_no : 479514679157202944
         * rec_name : 徐小姐
         * rec_tel : 15810055998
         * rec_add : 北京市-市辖区-海淀区五道口华清嘉园小区13号楼909
         * rec_area : 北京市-市辖区-海淀区
         * deli_com : 绿宝香瓜(中)1件,西红柿1件,秋黄瓜1件,长茄1件,金针菇2件,山竹（4A)1件
         * deli_name : 郭师傅
         * status : 3
         * saler_id : 466679676677398528
         * saler_uid : 468209586071937000
         * saler_user_name : 小武
         * saler_user_phone : 15801336013
         * order_type : 0
         * code :
         * rec_user_phone : 15801336013
         */

        private String group_no;
        private String rec_name;
        private String rec_tel;
        private String rec_add;
        private String rec_area;
        private String deli_com;
        private String deli_name;
        private int status;
        private long saler_id;
        private long saler_uid;
        private String saler_user_name;
        private String saler_user_phone;
        private int order_type;
        private String code;
        private String rec_user_phone;

        public String getGroup_no() {
            return group_no;
        }

        public void setGroup_no(String group_no) {
            this.group_no = group_no;
        }

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

        public String getRec_area() {
            return rec_area;
        }

        public void setRec_area(String rec_area) {
            this.rec_area = rec_area;
        }

        public String getDeli_com() {
            return deli_com;
        }

        public void setDeli_com(String deli_com) {
            this.deli_com = deli_com;
        }

        public String getDeli_name() {
            return deli_name;
        }

        public void setDeli_name(String deli_name) {
            this.deli_name = deli_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(long saler_id) {
            this.saler_id = saler_id;
        }

        public long getSaler_uid() {
            return saler_uid;
        }

        public void setSaler_uid(long saler_uid) {
            this.saler_uid = saler_uid;
        }

        public String getSaler_user_name() {
            return saler_user_name;
        }

        public void setSaler_user_name(String saler_user_name) {
            this.saler_user_name = saler_user_name;
        }

        public String getSaler_user_phone() {
            return saler_user_phone;
        }

        public void setSaler_user_phone(String saler_user_phone) {
            this.saler_user_phone = saler_user_phone;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRec_user_phone() {
            return rec_user_phone;
        }

        public void setRec_user_phone(String rec_user_phone) {
            this.rec_user_phone = rec_user_phone;
        }
    }
}
