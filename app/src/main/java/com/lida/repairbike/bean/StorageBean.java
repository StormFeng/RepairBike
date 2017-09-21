package com.lida.repairbike.bean;

import com.google.gson.JsonSyntaxException;
import com.midian.base.app.AppException;
import com.midian.base.bean.NetResult;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageBean extends NetResult {

    private List<DataBean> data;

    public static StorageBean parse(String json) throws AppException{
        StorageBean res = new StorageBean();
        try{
            res = gson.fromJson(json, StorageBean.class);
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

    public static class DataBean {
        /**
         * id : 3
         * name : 3号仓库
         * type : 0
         */

        private String id;
        private String name;
        private String type;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
