package com.product.as.merchants.entity;

public class LoginEntity {

    /**
     * data : {"uid":458727659406782464,"jwt_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODQ2MDUyNTksImp0aSI6IjQ1ODcyNzY1OTQwNjc4MjQ2NCIsImlhdCI6MTU4NDM0NTk5OSwiaXNzIjoiZnJlc2hjeWJlciIsIm5iZiI6MTU4NDM0NTM5OX0.buNY8muIAxIxl8KuxDqIX5sZ4JZzFBSgQ2QnNg4Sa50","sid":457680503602311168,"role_id":1,"is_auth":0}
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
         * uid : 458727659406782464
         * jwt_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODQ2MDUyNTksImp0aSI6IjQ1ODcyNzY1OTQwNjc4MjQ2NCIsImlhdCI6MTU4NDM0NTk5OSwiaXNzIjoiZnJlc2hjeWJlciIsIm5iZiI6MTU4NDM0NTM5OX0.buNY8muIAxIxl8KuxDqIX5sZ4JZzFBSgQ2QnNg4Sa50
         * sid : 457680503602311168
         * role_id : 1
         * is_auth : 0
         */

        private long uid;
        private String jwt_token;
        private long sid;
        private int role_id;
        private int is_auth;

        public int getIs_service() {
            return is_service;
        }

        public void setIs_service(int is_service) {
            this.is_service = is_service;
        }

        private int is_service;

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getJwt_token() {
            return jwt_token;
        }

        public void setJwt_token(String jwt_token) {
            this.jwt_token = jwt_token;
        }

        public long getSid() {
            return sid;
        }

        public void setSid(long sid) {
            this.sid = sid;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public int getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(int is_auth) {
            this.is_auth = is_auth;
        }
    }
}
