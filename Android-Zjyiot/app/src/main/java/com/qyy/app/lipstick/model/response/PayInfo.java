package com.qyy.app.lipstick.model.response;

/**
 * @author dengwg
 * @date 2018/3/21
 */
public class PayInfo {


    /**
     * payInfo : weixin://wxpay/bizpayurl?pr=j7XIyPZ
     * orderNo : 20180315151054388068
     * price : 0.04
     * createDate : 1521097855568
     * deadTime : 7199
     */

    private String payInfo;
    private String orderNo;
    private String price;
    private long createDate;
    private String deadTime;

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }
}
