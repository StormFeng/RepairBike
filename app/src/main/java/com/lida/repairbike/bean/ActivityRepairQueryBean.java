package com.lida.repairbike.bean;

import com.google.gson.JsonSyntaxException;
import com.midian.base.app.AppException;
import com.midian.base.bean.NetResult;

import java.util.List;

/**
 * 维修查询
 * Created by Administrator on 2017/3/7.
 */

public class ActivityRepairQueryBean extends NetResult {

    private List<DataBean> data;

    public static ActivityRepairQueryBean parse(String json) throws AppException{
        ActivityRepairQueryBean res = new ActivityRepairQueryBean();
        try{
            res = gson.fromJson(json, ActivityRepairQueryBean.class);
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
         * id : 40
         * eid : 1
         * mid : 1300000000
         * rtime : 1491528123
         * staus : 1
         * type : 1
         * trouble : {33:锁不闭合;36:锁身编号与锁系统编号不一致;}
         * parts :
         * lockid : 1300000000
         * address : 在徳埔小区附近
         * reid : 0
         * rtime_complete : 0
         * factory : 0
         * storage : 0
         * name : 小张
         * parts_name :
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
        private String name;
        private String parts_name;

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
    }
}
