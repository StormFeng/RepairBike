package com.lida.repairbike.bean;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.midian.base.app.AppException;
import com.midian.base.bean.NetResult;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ActivityMyRepairHistoryBean extends NetResult {

    private List<DataBean> data;

    public static ActivityMyRepairHistoryBean parse(String json) throws AppException{
        ActivityMyRepairHistoryBean res = new ActivityMyRepairHistoryBean();
        try{
            res = gson.fromJson(json, ActivityMyRepairHistoryBean.class);
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


    public class DataBean extends NetResult{
        /**
         * id : 1
         * eid : 1
         * mid : 1
         * rtime : 1488873787
         * staus : 0
         * type : 1
         * trouble : 1
         * parts : 2
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
    }
}
