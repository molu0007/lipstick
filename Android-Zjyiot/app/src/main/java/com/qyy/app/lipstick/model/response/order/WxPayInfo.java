package com.qyy.app.lipstick.model.response.order;


public class WxPayInfo {
//    {"appid":"wx9002438d10b58a95","noncestr":"out32h07ptq6kn7btynzslnsxlb0fz1q",
//            "package":"Sign\u003dWXPay","partnerid":"1521922641","prepayid":"wx231204226685013697faf4fc0765317186",
//            "sign":"0DC42872C78EEF2D1E332BB1AF7B3B78","timestamp":"1553313862"}
    public String appid;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
//    public String package;
    public String sign;
    public String extdata;
    public String signtype;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExtdata() {
        return extdata;
    }

    public void setExtdata(String extdata) {
        this.extdata = extdata;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }
}
