package com.qyy.app.lipstick.model.response.order;

import com.qyy.app.lipstick.model.response.home.GoodsBean;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-13 10:23
 * @name: OrderEntry
 */
public class OrderEntry {


    /**
     * list : [{"id":100008,"orderSn":"20190309160721048518675","gid":10009,"uid":3,"orderStatus":0,"shippingStatus":0,"consignee":"","address":"","mobile":"","shippingFee":0,"shippingNo":null,"shippingName":"","addTime":"2019-03-09 16:07:21","updateTime":null,"confirmTime":null,"good":{"gid":10009,"name":"海蓝之谜鎏光焕光遮瑕膏","description":"","brand":"海蓝之谜","counterPrice":600,"marketPrice":130,"colorName":"鎏光","colorValue":"EEC900","primaryPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","listPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","addTime":"2019-03-03 17:23:30","updateTime":"2019-03-06 18:53:02"}},{"id":100009,"orderSn":"20190309160756155049097","gid":10010,"uid":3,"orderStatus":0,"shippingStatus":0,"consignee":"","address":"","mobile":"","shippingFee":0,"shippingNo":null,"shippingName":"","addTime":"2019-03-09 16:07:56","updateTime":null,"confirmTime":null,"good":{"gid":10010,"name":"萝卜丁口红女王权杖口红唇膏","description":"","brand":"Christian","counterPrice":800,"marketPrice":150,"colorName":"红色","colorValue":"EE0000","primaryPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","listPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","addTime":"2019-03-03 17:23:30","updateTime":"2019-03-06 18:53:13"}},{"id":100010,"orderSn":"20190309160807822774900","gid":10011,"uid":3,"orderStatus":2,"shippingStatus":0,"consignee":"王五","address":"是否的双方的身份水电费","mobile":"","shippingFee":10,"shippingNo":"100008976548888","shippingName":"韵达快递","addTime":"2019-03-09 16:08:07","updateTime":null,"confirmTime":null,"good":{"gid":10011,"name":"蔻驰新款PVC中号手提单肩包","description":"","brand":"COACH","counterPrice":1280,"marketPrice":240,"colorName":"米色","colorValue":"EECFA1","primaryPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","listPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","addTime":"2019-03-03 17:23:30","updateTime":"2019-03-06 18:53:25"}},{"id":100011,"orderSn":"20190309160818078413791","gid":10012,"uid":3,"orderStatus":1,"shippingStatus":0,"consignee":"","address":"","mobile":"","shippingFee":0,"shippingNo":null,"shippingName":"","addTime":"2019-03-09 16:08:18","updateTime":null,"confirmTime":null,"good":{"gid":10012,"name":"Dior迪奥小姐花漾甜心女士淡香水","description":"","brand":"迪奥","counterPrice":600,"marketPrice":120,"colorName":"粉色","colorValue":"FFC0CB","primaryPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","listPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","addTime":"2019-03-03 17:53:43","updateTime":"2019-03-06 18:53:26"}}]
     * total : 4
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 100008
         * orderSn : 20190309160721048518675
         * gid : 10009
         * uid : 3
         * orderStatus : 0
         * shippingStatus : 0
         * consignee : 
         * address : 
         * mobile : 
         * shippingFee : 0
         * shippingNo : null
         * shippingName : 
         * addTime : 2019-03-09 16:07:21
         * updateTime : null
         * confirmTime : null
         * good : {"gid":10009,"name":"海蓝之谜鎏光焕光遮瑕膏","description":"","brand":"海蓝之谜","counterPrice":600,"marketPrice":130,"colorName":"鎏光","colorValue":"EEC900","primaryPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","listPicUrl":"https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png","addTime":"2019-03-03 17:23:30","updateTime":"2019-03-06 18:53:02"}
         */

        private long id;
        private String orderSn;
        private long gid;
        private long uid;
        private int orderStatus;
        private int shippingStatus;
        private String consignee;
        private String address;
        private String mobile;
        private int shippingFee;
        private String shippingNo;
        private String shippingName;
        private String addTime;
        private String updateTime;
        private String confirmTime;
        private GoodsBean good;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public long getGid() {
            return gid;
        }

        public void setGid(long gid) {
            this.gid = gid;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getShippingStatus() {
            return shippingStatus;
        }

        public void setShippingStatus(int shippingStatus) {
            this.shippingStatus = shippingStatus;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(int shippingFee) {
            this.shippingFee = shippingFee;
        }

        public String getShippingNo() {
            return shippingNo;
        }

        public void setShippingNo(String shippingNo) {
            this.shippingNo = shippingNo;
        }

        public String getShippingName() {
            return shippingName;
        }

        public void setShippingName(String shippingName) {
            this.shippingName = shippingName;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public GoodsBean getGood() {
            return good;
        }

        public void setGood(GoodsBean good) {
            this.good = good;
        }

    }
}
