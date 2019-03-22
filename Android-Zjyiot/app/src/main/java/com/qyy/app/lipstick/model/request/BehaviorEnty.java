package com.qyy.app.lipstick.model.request;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-22 10:30
 * @name: BehaviorEnty
 */
public class BehaviorEnty {


    /**
     * addTime : 2019-03-21T11:11:38.180Z
     * deviceId : string
     * dkId : 0
     * source : string
     * uid : 0
     */

    private String addTime;
    private String deviceId;
    private int dkId;
    private String source;
    private int uid;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDkId() {
        return dkId;
    }

    public void setDkId(int dkId) {
        this.dkId = dkId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
