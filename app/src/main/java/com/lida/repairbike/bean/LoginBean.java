package com.lida.repairbike.bean;

import com.google.gson.JsonSyntaxException;
import com.midian.base.app.AppException;
import com.midian.base.bean.NetResult;

/**
 * Created by Administrator on 2017/3/7.
 */

public class LoginBean extends NetResult {

    /**
     * data : {"id":"1","name":"小张","head_img":"2017-03-06/58bcdf6ad2fb1.jpg","phone":"2147483647","address":"广州番禺桥南街福景路916号","logintime":"1489108654","account":"test1","passwd":"202cb962ac59075b964b07152d234b70"}
     */

    private DataBean data;

    public static LoginBean parse(String json) throws AppException{
        LoginBean res = new LoginBean();
        try{
            res = gson.fromJson(json, LoginBean.class);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            throw AppException.json(e);
        }
        return res;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 小张
         * head_img : 2017-03-06/58bcdf6ad2fb1.jpg
         * phone : 2147483647
         * address : 广州番禺桥南街福景路916号
         * logintime : 1489108654
         * account : test1
         * passwd : 202cb962ac59075b964b07152d234b70
         */

        private String id;
        private String name;
        private String head_img;
        private String phone;
        private String address;
        private String logintime;
        private String account;
        private String passwd;
        private String birth;
        private String repair_life;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getRepair_life() {
            return repair_life;
        }

        public void setRepair_life(String repair_life) {
            this.repair_life = repair_life;
        }
    }
}
