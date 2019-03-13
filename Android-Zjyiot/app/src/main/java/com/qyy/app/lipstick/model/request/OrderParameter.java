package com.qyy.app.lipstick.model.request;

import com.qyy.app.lipstick.BaseApplication;

/**
 * @author dengwg
 * @date 2018/3/21
 */
public class OrderParameter {

    private String iccid= BaseApplication.iccid;
    private String cardNo=BaseApplication.cardNo;
    private String accountId=BaseApplication.accoundId;
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
