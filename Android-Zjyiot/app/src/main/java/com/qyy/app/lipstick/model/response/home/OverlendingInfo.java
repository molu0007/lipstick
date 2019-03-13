package com.qyy.app.lipstick.model.response.home;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-12 10:16
 * @name: OverlendingInfo
 */
public class OverlendingInfo {

    /**
     * dkid : 10000
     * dkUrl : http://www.baidu.com
     * dkCode : xiaolukeji
     * dkName : 晓鹿科技
     * dkIcon : https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/2116509538f9c8.png
     * dkRange : 1000-5000
     * dkType : 现金贷
     * dkApplyNum : 44031
     */

    private long dkid;
    private String dkUrl;
    private String dkCode;
    private String dkName;
    private String dkIcon;
    private String dkRange;
    private String dkType;
    private long dkApplyNum;

    public long getDkid() {
        return dkid;
    }

    public void setDkid(long dkid) {
        this.dkid = dkid;
    }

    public String getDkUrl() {
        return dkUrl;
    }

    public void setDkUrl(String dkUrl) {
        this.dkUrl = dkUrl;
    }

    public String getDkCode() {
        return dkCode;
    }

    public void setDkCode(String dkCode) {
        this.dkCode = dkCode;
    }

    public String getDkName() {
        return dkName;
    }

    public void setDkName(String dkName) {
        this.dkName = dkName;
    }

    public String getDkIcon() {
        return dkIcon;
    }

    public void setDkIcon(String dkIcon) {
        this.dkIcon = dkIcon;
    }

    public String getDkRange() {
        return dkRange;
    }

    public void setDkRange(String dkRange) {
        this.dkRange = dkRange;
    }

    public String getDkType() {
        return dkType;
    }

    public void setDkType(String dkType) {
        this.dkType = dkType;
    }

    public long getDkApplyNum() {
        return dkApplyNum;
    }

    public void setDkApplyNum(long dkApplyNum) {
        this.dkApplyNum = dkApplyNum;
    }
}
