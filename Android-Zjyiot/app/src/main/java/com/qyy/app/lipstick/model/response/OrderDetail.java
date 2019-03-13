package com.qyy.app.lipstick.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengwg
 * @date 2018/3/21
 */
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = -7060210544600464480L;
    private String ibuOrderNo;//后台实际订单号
    private String clientOrderNo;//用户展示订单号
    private String idBatchOrder;
    private String accountId;
    private String companyName;
    private String clientType;
    private String createdUser;
    private long dateCreated;
    private String lastModifyDate;
    private String flowCardProductId;
    private String flowCardName;
    private String buyCount;
    private String mobileBureau;
    private String flowCardNo;
    private BigDecimal goodsAmount;
    private BigDecimal goodsAmountDis;
    private String orderAmount;
    private String status;//00：待支付，10：支付成功，20：交易中，30：交易成功，31：交易失败，88：人工订单，98：失效订单；99：退款订单
    private String requestStatus;
    private String orderType;//1：套餐续费,2:套餐变更,3:购买流量包,4：购买短信包,5:购卡
    private String effectType;
    private String idRenewType;
    private String callbackStatus;
    private String callbackUrl;
    private String callbackDate;
    private String callbackCount;
    private String orderDesc;
    private String startTime;
    private String endTime;
    private String prdCode;
    private String volume;
    private String payMethod;
    private String orderSource;
    private String queryAccountIds;
    private String onliePay_json;
    private String financeIsEnough;
    private String currentAmouts;
    private String parentId;
    private String custId;
    private String cardNos;
    private String poolid;
    private String poolname;
    private String account;
    private String parentOrderNo;
    private String queryAisleIds;
    private String levalFlag;
    private String secOrderNo;
    private String source;
    private String wxUserId;
    private String year;
    private String mobileBureaus;
    private String flowCardNo20;

    public String getIbuOrderNo() {
        return ibuOrderNo;
    }

    public void setIbuOrderNo(String ibuOrderNo) {
        this.ibuOrderNo = ibuOrderNo;
    }

    public String getClientOrderNo() {
        return clientOrderNo;
    }


    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getFlowCardProductId() {
        return flowCardProductId;
    }

    public void setFlowCardProductId(String flowCardProductId) {
        this.flowCardProductId = flowCardProductId;
    }

    public String getFlowCardName() {
        return flowCardName==null?"":flowCardName;
    }


    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public BigDecimal getGoodsAmountDis() {
        return goodsAmountDis;
    }

    public void setGoodsAmountDis(BigDecimal goodsAmountDis) {
        this.goodsAmountDis = goodsAmountDis;
    }

    public String getOrderAmount() {
        return orderAmount==null?"":orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getStatus() {
        return status==null?"":status;
    }


    public String getOrderType() {
        return orderType==null?"":orderType;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
