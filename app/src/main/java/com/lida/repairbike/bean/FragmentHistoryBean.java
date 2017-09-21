package com.lida.repairbike.bean;

import com.google.gson.JsonSyntaxException;
import com.midian.base.app.AppException;
import com.midian.base.bean.NetResult;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class FragmentHistoryBean extends NetResult {


    private List<DataBean> data;

    public static FragmentHistoryBean parse(String json) throws AppException{
        FragmentHistoryBean res = new FragmentHistoryBean();
        try{
            res = gson.fromJson(json, FragmentHistoryBean.class);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            throw AppException.json(e);
        }
        return res;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends NetResult{
        /**
         * id : 3
         * eid : 1
         * mid : 1234567890
         * rtime : 1490838380
         * staus : 2
         * type : 1
         * trouble : {29:低电;30:锁不上报;31:定位不准;}
         * parts : {}
         * lockid : 1234567890
         * address : 在徳埔小区附近
         * reid : 1
         * rtime_complete : 1490838380
         * factory : 0
         * storage : 0
         */

        private String id;
        private String eid;
        private String mid;
        private String rtime;
        private String staus;
        private String type;
        private String trouble;
        private String parts;
        private String lockid;
        private String address;
        private String reid;
        private String rtime_complete;
        private String factory;
        private String storage;
        private String parts_name;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParts_name() {
            return parts_name;
        }

        public void setParts_name(String parts_name) {
            this.parts_name = parts_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getRtime() {
            return rtime;
        }

        public void setRtime(String rtime) {
            this.rtime = rtime;
        }

        public String getStaus() {
            return staus;
        }

        public void setStaus(String staus) {
            this.staus = staus;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTrouble() {
            return trouble;
        }

        public void setTrouble(String trouble) {
            this.trouble = trouble;
        }

        public String getParts() {
            return parts;
        }

        public void setParts(String parts) {
            this.parts = parts;
        }

        public String getLockid() {
            return lockid;
        }

        public void setLockid(String lockid) {
            this.lockid = lockid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReid() {
            return reid;
        }

        public void setReid(String reid) {
            this.reid = reid;
        }

        public String getRtime_complete() {
            return rtime_complete;
        }

        public void setRtime_complete(String rtime_complete) {
            this.rtime_complete = rtime_complete;
        }

        public String getFactory() {
            return factory;
        }

        public void setFactory(String factory) {
            this.factory = factory;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }
    }
}
