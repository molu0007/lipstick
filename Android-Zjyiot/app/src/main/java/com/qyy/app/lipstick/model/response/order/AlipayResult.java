package com.qyy.app.lipstick.model.response.order;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-14 18:50
 * @name: AlipayResult
 */
public class AlipayResult {

    /**
     * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2019022863441421","auth_app_id":"2019022863441421","charset":"utf-8","timestamp":"2019-03-14 18:45:02","out_trade_no":"112","total_amount":"0.01","trade_no":"2019031422001469051031949769","seller_id":"2088231666819242"}
     * sign : Ruwcv672q4U+6iWxDKlgI7t0TJd4iUm+pcy32hrvTQ/v/BFwOqgQBUaWJUdlh0Sn3fV8MmtoCCjEQ9R3P0NXDTMuhdinNVp5+2HGBuU3T3e5t1RtnyasgDFz1mq6gmFAe50YNZ4yvw2lEXnXMtq/maemlbSztwPqGwqgUwhIB+OfgI4Ggtb0UGZjy14Z5kAFGIf4VKkV04ymP6oFPVJ9VUmd+VGrB7mdeCItniqCVvOAbuLl+eUfmrfai4Hkw8dONFrCOvPmmjEKiW/MhxrxgHV+kgNveULzQ2iuM8zyuuilgQLZ7T5hLhQtIk9gmw1y1Qq03phL8Wrh9LUefmOTUQ==
     * sign_type : RSA2
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean {
        /**
         * code : 10000
         * msg : Success
         * app_id : 2019022863441421
         * auth_app_id : 2019022863441421
         * charset : utf-8
         * timestamp : 2019-03-14 18:45:02
         * out_trade_no : 112
         * total_amount : 0.01
         * trade_no : 2019031422001469051031949769
         * seller_id : 2088231666819242
         */

        private String code;
        private String msg;
        private String app_id;
        private String auth_app_id;
        private String charset;
        private String timestamp;
        private String out_trade_no;
        private String total_amount;
        private String trade_no;
        private String seller_id;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }
    }
}
