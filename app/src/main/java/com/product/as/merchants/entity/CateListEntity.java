package com.product.as.merchants.entity;

import java.util.List;

public class CateListEntity {

    /**
     * data : [{"ID":1,"CreatedAt":"2020-03-17T16:24:19+08:00","UpdatedAt":"2020-03-17T16:24:19+08:00","DeletedAt":null,"cata_code":"10000","cata_name":"水果","parent_id":0,"level":0,"is_lift":0,"cata_img":"xxxx"},{"ID":2,"CreatedAt":"2020-03-17T16:25:03+08:00","UpdatedAt":"2020-03-17T16:25:03+08:00","DeletedAt":null,"cata_code":"20000","cata_name":"蔬菜","parent_id":0,"level":0,"is_lift":0,"cata_img":"xxxx"}]
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
         * ID : 1
         * CreatedAt : 2020-03-17T16:24:19+08:00
         * UpdatedAt : 2020-03-17T16:24:19+08:00
         * DeletedAt : null
         * cata_code : 10000
         * cata_name : 水果
         * parent_id : 0
         * level : 0
         * is_lift : 0
         * cata_img : xxxx
         */

        private int ID;
        private String CreatedAt;
        private String UpdatedAt;
        private Object DeletedAt;
        private String cata_code;
        private String cata_name;
        private int parent_id;
        private int level;
        private int is_lift;
        private String cata_img;

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

        public String getCata_code() {
            return cata_code;
        }

        public void setCata_code(String cata_code) {
            this.cata_code = cata_code;
        }

        public String getCata_name() {
            return cata_name;
        }

        public void setCata_name(String cata_name) {
            this.cata_name = cata_name;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIs_lift() {
            return is_lift;
        }

        public void setIs_lift(int is_lift) {
            this.is_lift = is_lift;
        }

        public String getCata_img() {
            return cata_img;
        }

        public void setCata_img(String cata_img) {
            this.cata_img = cata_img;
        }
    }
}
