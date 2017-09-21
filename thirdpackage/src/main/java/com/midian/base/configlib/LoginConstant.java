package com.midian.base.configlib;

/**
 * 注册登录url
 *
 * @author MIDIAN
 */
public class LoginConstant {

    public static final String MBASEURL = ServerConstant.BHOST;// PP选址
//    public static final String BBASEURL = ServerConstant.BHOST;// PP选址
    /**
     * 1.1注册
     */
    public static final String MEMBER_AUTHORIZE_REGISTER = MBASEURL + "register";
    /**
     * 1.2登陆
     */
    public static final String MEMBER_AUTHORIZE_LOGIN = MBASEURL + "login";
    /**
     * 1.3第三方登录
     */
    public static final String AUTHORIZE_AUTHLOGIN = MBASEURL + "third_user_login";
    /**
     * 1.4发送验证码
     */
    public static final String member_platform_codes_send = MBASEURL + "send_code";
    /**
     * 1.5验证验证码
     */
    public static final String member_platform_codes_verify = MBASEURL + "validate_code";
    /**
     * 1.6验证原密码
     */
    public static final String VALIDATE_PWD = MBASEURL + "validate_pwd";
    /**
     * 1.7修改密码
     */
    public static final String member_membership_info_updatePassword = MBASEURL + "update_pwd";
    /**
     * 1.12找回密码
     */
    public static final String member_membership_info_findPassword = MBASEURL + "find_pwd";
}
