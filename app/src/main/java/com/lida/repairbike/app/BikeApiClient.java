package com.lida.repairbike.app;

import com.lida.repairbike.bean.ActivityMyRepairHistoryBean;
import com.lida.repairbike.bean.ActivityRepairListBean;
import com.lida.repairbike.bean.ActivityRepairQueryBean;
import com.lida.repairbike.bean.BikeStateBean;
import com.lida.repairbike.bean.FragmentHistoryBean;
import com.lida.repairbike.bean.LoginBean;
import com.lida.repairbike.bean.PartBean;
import com.lida.repairbike.bean.PartsBean;
import com.lida.repairbike.bean.StorageBean;
import com.lida.repairbike.bean.TroubleBean;
import com.midian.base.afinal.http.AjaxParams;
import com.midian.base.api.ApiCallback;
import com.midian.base.api.BaseApiClient;
import com.midian.base.app.AppContext;
import com.midian.base.bean.NetResult;

/**
 * Created by Administrator on 2017/3/7.
 */

public class BikeApiClient extends BaseApiClient {

    public BikeApiClient(AppContext ac) {
        super(ac);
    }
    static public void init(AppContext appcontext) {
        if (appcontext == null)
            return;
        appcontext.api.addApiClient(new BikeApiClient(appcontext));
    }

    /**
     * 登录
     * @param name
     * @param pass
     * @param callback
     */
    public void login(String name, String pass, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("name",name);
        params.put("passwd",pass);
        post(callback, Constant.LOGIN, params, LoginBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 故障接口
     * @param type
     * @param callback
     */
    public void getTroubleSoftWare(String type, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("type",type);
        post(callback, Constant.GETTROUBLE, params, TroubleBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }
    /**
     * 故障接口
     * @param type
     * @param callback
     */
    public void getTroubleHardWare(String type, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("type",type);
        post(callback, Constant.GETTROUBLE, params, TroubleBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 配件
     * @param callback
     */
    public void getPart(ApiCallback callback){
        AjaxParams params=new AjaxParams();
//        params.put("type",type);
        post(callback, Constant.GETPART, params, PartsBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 检修记录提交
     * @param eid
     * @param mid
     * @param type
     * @param trouble
     * @param callback
     */
    public void repair(String eid, String mid, String  lockid, String type, String trouble,String address,
                       String factory, String storage, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        params.put("mid",mid);
        params.put("lockid",lockid);
        params.put("type",type);
        params.put("trouble",trouble);
        params.put("address",address);
        params.put("factory",factory);
        params.put("storage",storage);
        post(callback, Constant.REPAIR, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 获取单车状态
     * @param mid
     * @param callback
     */
    public void getRepair(String mid, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("mid",mid);
        post(callback, Constant.GETREPAIR, params, BikeStateBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     *维修记录提交
     * @param mid
     * @param trouble
     * @param parts
     * @param address
     * @param callback
     */
    public void submitRepair(String eid, String mid, String trouble, String parts, String address,
                             String id,ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        params.put("mid",mid);
        params.put("trouble",trouble);
        params.put("parts",parts);
        params.put("address",address);
        params.put("id",id);
        post(callback, Constant.SUBMITREPAIR, params, BikeStateBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 新添加配件
     * @param name
     * @param type
     * @param callback
     */
    public void addNewPart(String name,String type, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("name",name);
        params.put("type",type);
        post(callback, Constant.ADDNEWPART, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 新添加配件
     * @param name
     * @param type
     * @param callback
     */
    public void addNewTrouble(String name,String type, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("name",name);
        params.put("type",type);
        post(callback, Constant.ADDNEWTROUBLE, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 我的维修历史
     * @param eid
     * @return
     * @throws Exception
     */
    public ActivityMyRepairHistoryBean getRepairHistory(String eid) throws Exception{
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        return (ActivityMyRepairHistoryBean) postSync(Constant.GETTRPAIRBYEID,params,ActivityMyRepairHistoryBean.class);
    }

    /**
     * 故障列表
     * @param
     * @return
     * @throws Exception
     */
    public ActivityRepairListBean getRepairALL(String type) throws Exception{
        AjaxParams params=new AjaxParams();
        params.put("type", type);
        return (ActivityRepairListBean) postSync(Constant.GETREPAIRALL,params,ActivityRepairListBean.class);
    }

    /**
     * 获取某辆单车的历史维修记录
     * @param
     * @return
     * @throws Exception
     */
    public FragmentHistoryBean getRepairByMid(String mid, String staus) throws Exception{
        AjaxParams params=new AjaxParams();
        params.put("mid", mid);
        params.put("staus", staus);
        return (FragmentHistoryBean) postSync(Constant.GETREPAIRBYMID,params,FragmentHistoryBean.class);
    }

    /**
     * 获取仓库
     * @param
     * @return
     * @throws Exception
     */
    public void getStorage(ApiCallback callback){
        AjaxParams params=new AjaxParams();
        post(callback, Constant.GETSTORAGE, params, StorageBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 获取工厂
     * @param
     * @return
     * @throws Exception
     */
    public void getFactory(ApiCallback callback){
        AjaxParams params=new AjaxParams();
        post(callback, Constant.GETFACTORY, params, StorageBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 领取配件
     * @param part
     * @param callback
     */
    public void getPartFromStock(String eid, String part, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        params.put("part",part);
        post(callback, Constant.GETPARTFROMSTOCK, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 退回配件
     * @param part
     * @param callback
     */
    public void returnPartFromStock(String eid, String part, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        params.put("part",part);
        post(callback, Constant.RETURNPARTFROMSTOCK, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 个人中心-配件管理-领取的配件
     * @param callback
     */
    public void getPartByEid(String eid, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        post(callback, Constant.GETPARTBYEID, params, PartsBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 个人中心-配件管理-领取的配件
     * @param callback
     */
    public void getReturnPartByEid(String eid, ApiCallback callback){
        AjaxParams params=new AjaxParams();
        params.put("eid",eid);
        post(callback, Constant.GETRETURNPARTBYEID, params, PartsBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 个人中心-配件管理-领取的配件
     * @param callback
     */
    public void getPartCount(ApiCallback callback){
        AjaxParams params=new AjaxParams();
        post(callback, Constant.GETPARTCOUNT, params, PartBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 维修查询
     * @param
     * @return
     * @throws Exception
     */
    public ActivityRepairQueryBean getReAll() throws Exception{
        AjaxParams params=new AjaxParams();
        return (ActivityRepairQueryBean) postSync(Constant.GETREALL,params,ActivityRepairQueryBean.class);
    }
}
