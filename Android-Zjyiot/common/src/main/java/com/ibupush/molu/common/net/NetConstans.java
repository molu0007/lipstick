package com.ibupush.molu.common.net;

/**
 * 网络请求常量
 * Created by 曾丽 on 2017/7/31.
 */
public interface NetConstans {
    /**
     * 后台返回成功响应吗
     */
    String CODE_SUCCESS = "100";

    /**
     * 接口调用成功
     */
    String  STATUS_SUCCESS = "0";

    /**
     * 接口调用失败
     */
    int STATUS_ERROR = 0;

    /**
     * 网络请求异常
     */
    String CODE_EXCEPTION = "-1";

    /**
     * 第三方未绑定
     */
    String CODE_NOT_BIND = "10017";

    /**
     * 账号已存在
     */
    String ACCOUNT_EXIST = "10007";

    /**
     * 账号不存在
     */
    String ACCOUNT_NOT_EXIST = "10010";

    /**
     * 需要登录的错误码
     */
    String CODE_NEED_LOGIN = "1064";
    /**
     * 当前账号已在其他终端登陆
     */
    String CODE_LOGIN_ON_OTHER_DEVICE = "10060";
    /**
     * 没有关注的用户
     */
    String CODE_NO_FOLLOW_USER = "10043";
    /**
     * 后台返回错误码(非100)
     */
    int ERROR_CODE_SERVER = 0;

    /**
     * 网络请求错误(http响应码非2xx成功类)
     */
    int ERROR_CODE_HTTP = 1;

    /**
     * 网络请求异常
     */
    int ERROR_EXCEPTION = 2;
}