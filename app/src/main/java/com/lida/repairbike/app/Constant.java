package com.lida.repairbike.app;

/**
 * Created by Administrator on 2017/3/7.
 */

public class Constant {

//    public static final String BASEURL = "http://www.gzldkj.com/moby/admin.php/inter/";
//    public static final String IMGBASEURL = "http://www.gzldkj.com/moby/Public/Uploads/";

    public static final String BASEURL = "http://zp.lc-shipping.com/admin.php/inter/";
    public static final String IMGBASEURL = "http://zp.lc-shipping.com/Public/Uploads/";
    /**
     * 登录
     */
    public static final String LOGIN = BASEURL + "login";

    /**
     * 故障接口
     */
    public static final String GETTROUBLE = BASEURL + "getTrouble";

    /**
     * 配件接口
     */
    public static final String GETPART = BASEURL + "getPart";

    /**
     * 提交检修记录
     */
    public static final String REPAIR = BASEURL + "repair";

    /**
     * 获取单车状态
     */
    public static final String GETREPAIR = BASEURL + "getRepair";

    /**
     *提交维修好单车
     */
    public static final String SUBMITREPAIR = BASEURL + "submitRepair";

    /**
     *新添加故障
     */
    public static final String ADDNEWTROUBLE = BASEURL + "addNewTrouble";

    /**
     *新添加配件
     */
    public static final String ADDNEWPART = BASEURL + "addNewPart";

    /**
     *个人中心维修历史记录
     */
    public static final String GETTRPAIRBYEID = BASEURL + "getRepairByEid";

    /**
     *故障列表
     */
    public static final String GETREPAIRALL = BASEURL + "getRepairALL";

    /**
     *获取仓库
     */
    public static final String GETSTORAGE = BASEURL + "getStorage";

    /**
     *获取工厂
     */
    public static final String GETFACTORY = BASEURL + "getFactory";

    /**
     *领取配件
     */
    public static final String GETPARTFROMSTOCK = BASEURL + "getPartFromStock";

    /**
     *退回配件
     */
    public static final String RETURNPARTFROMSTOCK = BASEURL + "returnPartFromStock";

    /**
     * 个人中心—配件管理-领取的配件
     */
    public static final String GETPARTBYEID = BASEURL + "getPartByEid";

    /**
     * 个人中心—配件管理-领取的配件
     */
    public static final String GETRETURNPARTBYEID = BASEURL + "getReturnPartByEid";

    /**
     * 获取所有需求的统计
     */
    public static final String GETPARTCOUNT = BASEURL + "getPartCount";

    /**
     * 获取某辆单车的历史维修记录
     */
    public static final String GETREPAIRBYMID = BASEURL + "getRepairByMid";

    /**
     * 维修查询
     */
    public static final String GETREALL = BASEURL + "getReALL";
}
