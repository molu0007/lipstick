package com.qyy.app.lipstick.model.response;

import java.math.BigDecimal;

/**
 * @author dengwg
 * @date 2018/3/19
 */
public class PackageInfo {

    private int flowCardProductId;
    private BigDecimal goodsAmountDis;
    private BigDecimal orderAmount;



    public int getFlowCardProductId() {
        return flowCardProductId;
    }

    public void setFlowCardProductId(int flowCardProductId) {
        this.flowCardProductId = flowCardProductId;
    }

    public BigDecimal getGoodsAmountDis() {
        return goodsAmountDis;
    }

    public void setGoodsAmountDis(BigDecimal goodsAmountDis) {
        this.goodsAmountDis = goodsAmountDis;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }


}
