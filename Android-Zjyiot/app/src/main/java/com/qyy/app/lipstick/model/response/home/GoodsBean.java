package com.qyy.app.lipstick.model.response.home;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-13 11:05
 * @name: GoodsBean
 */
public  class GoodsBean {
    /**
     * gid : 10000
     * name : 阿玛尼红管唇釉
     * description :
     * brand : ARMANI
     * counterPrice : 310
     * marketPrice : 60
     * colorName : 番茄红
     * colorValue : FF7256
     * primaryPicUrl : https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png
     * listPicUrl : https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/1722546797ea4c.png
     * addTime : 2019-03-03 17:23:30
     * updateTime : 2019-03-06 18:51:39
     */

    private long gid;
    private String name;
    private String description;
    private String brand;
    private float counterPrice;
    private float marketPrice;
    private String colorName;
    private String colorValue;
    private String primaryPicUrl;
    private String listPicUrl;
    private String addTime;
    private String updateTime;

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(float counterPrice) {
        this.counterPrice = counterPrice;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }

    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl;
    }

    public String getListPicUrl() {
        return listPicUrl;
    }

    public void setListPicUrl(String listPicUrl) {
        this.listPicUrl = listPicUrl;
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
}
