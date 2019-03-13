package com.qyy.app.lipstick.event;

/**
 * 事件类型
 * Created by 曾丽 on 2017/6/24.
 */

public enum EventType {

    /**
     * 登出
     */
    LOG_OUT,

    /**
     * 登录成功
     */
    LOGIN_SUCCESS,

    /**
     * 非法Token
     */
    INVALIDTOKEN,


    /**
     * 高级搜索--排序改变
     */
    FILTER_ORDER_CHANGE,
    /**
     * 高级搜索--类型改变
     */
    FILTER_TYPE_CHANGE,
    /**
     * 高级搜索--商圈改变
     */
    FILTER_CIRCLE_CHANGE,

    /**
     * 高级搜索--赛制改变
     */
    FILTER_DISCIPLINE_CHANGE,

    /**
     * 高级搜索--球队水平改变
     */
    FILTER_TEAM_LEVEL_CHANGE,

    /**
     * 高级搜索,输入-->模糊匹配
     */
    FILTER_SEARCH_INPUT,

    /**
     * 取消订单
     */
    CANCELORDER,

    /**
     * 绑定成功
     */
    BIND_OK,

    /**
     * 绑定成功
     */
    THIRD_LOGIN_OK,

    /**
     * 完善信息成功
     */
    COMPLETE_INFO_SUCCESS,

    /**
     * 修改密码成功
     */
    UPDATE_PASSWORD_SUCCESS,
    /**
     * 定时结束,刷新数据
     */
    COUNT_DONW_FINISH,
    /**
     * 支付成功
     */
    PAY_SUCCESS,

    /**
     * 支付失败
     */
    PAY_FAILURE,

    /**
     * 未支付
     */
    PAY_WAIT,

    /**
     * 城市选择成功
     */
    CITY_SELECT_SUCCESS,

    /**
     * 确认下单
     */
    CONFIRM_ORDER_SUCCESS,

    /**
     * 需要跳转拍卖列表
     */
    SEND_AUCTION,

    /**
     * 评价成功
     */
    SEND_COMMENT_SUCCESS,
    /**
     * 绑定银行卡成功
     */
    BIND_BANK_SUCCESS,
    /**
     * 选择银行卡成功
     */
    SELECT_BANK_SUCCESS,

    /**
     * 选择常驻场馆
     */
    PICK_RESIDENT,

    /**
     * 状态改变刷新我的详情页面
     */
    UPDATE_MYDATILS,

    /**
     * 状态改变刷新我的详情个性页面
     */
    UPDATE_MYDATILS_CHARACTER,

    /**
     * 创建了一个活动
     */
    EVENT_ADD,

    /**
     * 修改了一个活动
     */
    EVENT_MODIFY,

    /**
     * 修改了活动成员
     */
    EVENT_MEMBER_MODIFY,

    /**
     * 成功报名了活动
     */
    EVENT_APPLY,

    /**
     * 取消活动
     */
    EVENT_CANCEL,
    /**
     * 退出活动
     */
    EVENT_QUIT,

    /**
     * 添加了一个圈子
     */
    CIRCLE_ADD,

    /**
     * 修改了一个圈子
     */
    CIRCLE_MODIFY,

    /**
     * 解散了一个圈子
     */
    CIRCLE_DISBAND,

    /**
     * 退出了一个圈子
     */
    CIRCLE_QUIT,

    /**
     * 添加了一个公告
     */
    NOTIFY_ADD,

    /**
     * 删除了一个公告
     */
    NOTIFY_DEL,

    /**
     * 圈子审核通过
     */
    CIRCLE_PASS,

    /**
     * 添加了一个活动模板
     */
    EVENT_FIX_ADD,

    /**
     * 删除了一个活动模板
     */
    EVENT_FIX_DEL,

    /**
     * 修改了一个活动模板
     */
    EVENT_FIX_MODIFY,

    /**
     * 更新积分任务页面
     */
    INTEGRAL_TACKLIST_UPDATE,

    /**
     * 更新我的关注
     */
    UPDATE_MYATTENTION,

    /**
     * 关注更新我的详情
     */
    ATTENTION_UPDATE_MYDETAILS,

    /**
     * 更新互相关注
     */
    UPDATE_EACHATTENTION,

    /**
     * 更新关注我的
     */
    UPDATE_ATTENTIONMY,

    /**
     * 领取代金券成功
     */
    GET_VOUCHER_SUCCESS,

    /**
     * 更新好友列表
     */
    UPDATE_FRIDEN,

    /**
     * 开场成功
     */
    STARTPLACESUCCESS,

    /**
     * 任命教练成功
     */
    CONFIRM_COACH_SUCCESS,

    /**
     * 创建球队成功
     */
    ADD_TEAM_SUCCESS,

    /**
     * 入队请求成功
     */
    ENQUEUE_REQUEST_SUCCESS,

    /**
     * 删除入队请求成功
     */
    ENQUEUE_REQUEST_DELETE,

    /**
     * 删除球员成功
     */
    DELETE_TEAM_MEMBER_SUCCESS,
    /**
     * 解散球队成功
     */
    DISMISS_TEAM_MEMBER_SUCCESS,
    /**
     * 移除队员成功
     */
    DELETE_MEMBER_SUCCESS,
    /**
     * 关注赛事
     */
    ATTENTIONEVENT,
    /**
     * 取消关注赛事
     */
    CANCLEATTENTIONEVENT,

    /**
     * 刷新比分直播页面
     */
    REFRESHSCOREDATA,

    /**
     * 关闭弹幕
     */
    CLOSEBARRAGE,

    /**
     * 打开弹幕
     */
    OPENBARRAGE,
    /**
     * 发起约球下单支付成功
     */
    YQ_PAY_SUCCESS,

    /**
     * 积分改变刷新我的详情个性页面
     */
    UPDATE_MYDATILS_CHARACTER_TOINTEGRAL,

}
