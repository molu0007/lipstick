package com.qyy.app.lipstick.model.response;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public class CardInfo {

    /**
     * flowCardNo : 1000378283934
     * inStorageNo : R2018010310471823440
     * outStorageNo : R2018010310531614108
     * flowCardNo20 : 85913340873905980000
     * dateCreated : 1514947792000
     * status :
     * batchNo : R2018010310471823440
     * aisleKey : IFC_AISLE_TEST
     * aliseName : 测试通道
     * flow :
     * defaultPrdCode : lt_yk_100m
     * currentPrdCode : lt_yk_100m
     * nextPrdCode : 0
     * dateActive :
     * isShutdown : 0
     * cardStatus : 1
     * totalFlow : 340
     * useFlow : 0
     * useTotalFlow : 0
     * dateExpire : 1543247999000
     * mobileBureau : zglt
     * currBillingCycleEndTime : 1522079999000
     * communicationPlan :
     * cardType : 1
     * prdName : 联通yk
     * dateLeft : 256    
     */

    private String flowCardNo;
    private String inStorageNo;
    private String outStorageNo;
    private String flowCardNo20;
    private long dateCreated;
    private String status;
    private String batchNo;
    private String aisleKey;
    private String aliseName;
    private String flow;
    private String defaultPrdCode;
    private String currentPrdCode;
    private String nextPrdCode;
    private String dateActive;
    private String isShutdown;
    private String cardStatus;
    private float totalFlow;
    private float useFlow;
    private float useTotalFlow;
    private long dateExpire;
    private String mobileBureau;
    private long currBillingCycleEndTime;
    private String communicationPlan;
    private String cardType;
    private String prdName;
    private String dateLeft;

    public String getFlowCardNo() {
        return flowCardNo;
    }

    public void setFlowCardNo(String flowCardNo) {
        this.flowCardNo = flowCardNo;
    }

    public String getInStorageNo() {
        return inStorageNo;
    }

    public void setInStorageNo(String inStorageNo) {
        this.inStorageNo = inStorageNo;
    }

    public String getOutStorageNo() {
        return outStorageNo;
    }

    public void setOutStorageNo(String outStorageNo) {
        this.outStorageNo = outStorageNo;
    }

    public String getFlowCardNo20() {
        return flowCardNo20;
    }

    public void setFlowCardNo20(String flowCardNo20) {
        this.flowCardNo20 = flowCardNo20;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAisleKey() {
        return aisleKey;
    }

    public void setAisleKey(String aisleKey) {
        this.aisleKey = aisleKey;
    }

    public String getAliseName() {
        return aliseName;
    }

    public void setAliseName(String aliseName) {
        this.aliseName = aliseName;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getDefaultPrdCode() {
        return defaultPrdCode;
    }

    public void setDefaultPrdCode(String defaultPrdCode) {
        this.defaultPrdCode = defaultPrdCode;
    }

    public String getCurrentPrdCode() {
        return currentPrdCode;
    }

    public void setCurrentPrdCode(String currentPrdCode) {
        this.currentPrdCode = currentPrdCode;
    }

    public String getNextPrdCode() {
        return nextPrdCode;
    }

    public void setNextPrdCode(String nextPrdCode) {
        this.nextPrdCode = nextPrdCode;
    }

    public String getDateActive() {
        return dateActive==null?"":dateActive;
    }

    public void setDateActive(String dateActive) {
        this.dateActive = dateActive;
    }

    public String getIsShutdown() {
        return isShutdown==null?"":isShutdown;
    }

    public void setIsShutdown(String isShutdown) {
        this.isShutdown = isShutdown;
    }

    public String getCardStatus() {
        return cardStatus==null?"":cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public float getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(float totalFlow) {
        this.totalFlow = totalFlow;
    }

    public float getUseFlow() {
        return useFlow;
    }

    public void setUseFlow(float useFlow) {
        this.useFlow = useFlow;
    }

    public float getUseTotalFlow() {
        return useTotalFlow;
    }

    public void setUseTotalFlow(float useTotalFlow) {
        this.useTotalFlow = useTotalFlow;
    }

    public long getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(long dateExpire) {
        this.dateExpire = dateExpire;
    }

    public String getMobileBureau() {
        return mobileBureau==null?"":mobileBureau;
    }

    public void setMobileBureau(String mobileBureau) {
        this.mobileBureau = mobileBureau;
    }

    public long getCurrBillingCycleEndTime() {
        return currBillingCycleEndTime;
    }

    public void setCurrBillingCycleEndTime(long currBillingCycleEndTime) {
        this.currBillingCycleEndTime = currBillingCycleEndTime;
    }

    public String getCommunicationPlan() {
        return communicationPlan;
    }

    public void setCommunicationPlan(String communicationPlan) {
        this.communicationPlan = communicationPlan;
    }

    public String getCardType() {
        return cardType==null?"":cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPrdName() {
        return prdName==null?"":prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getDateLeft() {
        return dateLeft==null?"":dateLeft;
    }

    public void setDateLeft(String dateLeft) {
        this.dateLeft = dateLeft;
    }
}
