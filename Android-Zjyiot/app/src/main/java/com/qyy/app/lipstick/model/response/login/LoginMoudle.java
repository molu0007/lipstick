package com.qyy.app.lipstick.model.response.login;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-09 17:25
 * @name: LoginMoudle
 */
public class LoginMoudle {

    /**
     * expire : 1583659299072
     * token : qlhl15bot9yjf8igg4rt2womb5i44w4h
     * user : {"userId":3,"username":"用户18770006803","password":"92925488b28ab12584ac8fcaa8a27a0f497b2c62940c8f4fbc8ef19ebc87c43e","gender":2,"birthday":null,"register_time":"2019-03-09 17:21:39","last_login_time":"2019-03-09 17:21:39","last_login_ip":"210.21.228.42","user_level_id":null,"nickname":"用户18770006803","mobile":"18770006803","register_ip":"210.21.228.42","avatar":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190227/2034293701158b.png","weixin_openid":null,"company_name":null,"company_address":null,"jifen":0}
     */

    private long expire;
    private String token;
    private UserBean user;

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * userId : 3
         * username : 用户18770006803
         * password : 92925488b28ab12584ac8fcaa8a27a0f497b2c62940c8f4fbc8ef19ebc87c43e
         * gender : 2
         * birthday : null
         * register_time : 2019-03-09 17:21:39
         * last_login_time : 2019-03-09 17:21:39
         * last_login_ip : 210.21.228.42
         * user_level_id : null
         * nickname : 用户18770006803
         * mobile : 18770006803
         * register_ip : 210.21.228.42
         * avatar : https://upload-1253528786.cosbj.myqcloud.com/upload/20190227/2034293701158b.png
         * weixin_openid : null
         * company_name : null
         * company_address : null
         * jifen : 0
         */

        private int userId;
        private String username;
        private String password;
        private int gender;
        private String birthday;
        private String register_time;
        private String last_login_time;
        private String last_login_ip;
        private String user_level_id;
        private String nickname;
        private String mobile;
        private String register_ip;
        private String avatar;
        private String weixin_openid;
        private String company_name;
        private String company_address;
        private int jifen;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getRegister_time() {
            return register_time;
        }

        public void setRegister_time(String register_time) {
            this.register_time = register_time;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public Object getUser_level_id() {
            return user_level_id;
        }

        public void setUser_level_id(String user_level_id) {
            this.user_level_id = user_level_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegister_ip() {
            return register_ip;
        }

        public void setRegister_ip(String register_ip) {
            this.register_ip = register_ip;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getWeixin_openid() {
            return weixin_openid;
        }

        public void setWeixin_openid(String weixin_openid) {
            this.weixin_openid = weixin_openid;
        }

        public Object getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public Object getCompany_address() {
            return company_address;
        }

        public void setCompany_address(String company_address) {
            this.company_address = company_address;
        }

        public int getJifen() {
            return jifen;
        }

        public void setJifen(int jifen) {
            this.jifen = jifen;
        }
    }
}
