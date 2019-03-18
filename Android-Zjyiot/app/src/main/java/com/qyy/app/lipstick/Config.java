package com.qyy.app.lipstick;

/**
 * @author dengwg
 * @date 2018/3/13
 */
public final class  Config {
//    public final static String URL_BASE="http://api.ifc.ibumobile.com:8004";
    public final static String URL_BASE="https://cms.jucaib.com";
    public final static String URL_HOME="/m1/ifc/getCardInfo";
    public final static String URL_PACKAGE_LIST="/m1/ifc/getPackageList";
    public final static String URL_PACKAGE_INFO="/m1/ifc/getPackageInfo";
    public final static String URL_ADDORDER="/m1/ifc/addOrder";
    public final static String URL_ORDER_LIST="/m1/ifc/getOrderList";
    public final static String URL_PAY_INFO="/m1/ifc/getPayInfo";
    public final static String URL_ACCOUNT_ID="/m1/ifc/getAccountId";

    public final static String URL_GETORDERSTATUS="/m1/ifc/getOrderStatus";


    /**
     * 口红机
     */
    /**
     * api/jucaib/user/smscode 短信验证码
     */
    public final static String URL_SMS_CODE="/api/jucaib/user/smscode";
    /**
     * /api/jucaib/user/login登录
     */
    public final static String URL_LOGIN="/api/jucaib/user/login";

    /**
     * /api/jucaib/game/index
     *
     * 首页
     */
    public final static String URL_HOME_INDEX="/api/jucaib/game/index";
    /**
     * /api/jucaib/game/daichao  超贷
     */
    public final static String URL_HOME_OVERLEND="/api/jucaib/game/daichao";
    /**
     * /api/jucaib/game/init   开始游戏
     */
    public final static String URL_HOME_GAME="/api/jucaib/game/init";

    /**
     * get  /api/jucaib/game/recharge 充值金额
     */
    public final static String URL_HOME_GAME_RECHARGE="/api/jucaib/game/recharge";
    /**
     * post  /api/jucaib/game/act用户行为记录
     */
    public final static String URL_HOME_ACTIVE="/api/jucaib/game/act";
    /**
     *  get  /api/jucaib/user/jsonconfig  配置信息
     */
    public final static String URL_USER_ACTIVE="/api/jucaib/user/jsonconfig";

    /**
     * get  /api/jucaib/user/jsonuser用户信息
     */
    public final static String URL_USER_INFO="/api/jucaib/user/jsonuser";
    /**
     * post  /api/jucaib/user/login_by_weixin 微信登录
     */
    public final static String URL_USER_WX_LOGIN="/api/jucaib/user/login_by_weixin";

    /**
     * post  /api/jucaib/order/drawlipstick  领取口红
     */
    public final static String URL_GET_LIPSPICK="/api/jucaib/order/drawlipstick";
    /**
     *   /api/jucaib/order/list  我的订单,0领取口红,1代发货,2已发货,3确认收货
     */
    public final static String URL_MY_ORDER="/api/jucaib/order/list";

    /**
     * post  /api/jucaib/order/submit 创建订单
     */
    public final static String URL_CRETE_ORDER="/api/jucaib/order/submit";

    /**
     * get  /api/jucaib/pay/prepay  获取支付的请求参数
     */
    public final static String URL_PAY_PREPAY="/api/jucaib/pay/prepay ";

    /**
     * /api/jucaib/pay/get_pay_result  getPayResult
     */
    public final static String URL_PAY_RESULT="/api/jucaib/pay/get_pay_result ";
    /**
     * post  /api/jucaib/order/drawlipstick
     *领取口红
     */
    public final static String URL_DRAWLINPSTICK="/api/jucaib/order/drawlipstick ";
}
