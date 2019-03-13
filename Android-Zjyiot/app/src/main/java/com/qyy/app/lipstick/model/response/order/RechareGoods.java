package com.qyy.app.lipstick.model.response.order;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-13 14:28
 * @name: RechareGoods
 */
public class RechareGoods {

    /**
     * id : 6
     * payNum : 200
     * jifen : 2000
     */

    private int id;
    private int payNum;
    private long jifen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }

    public long getJifen() {
        return jifen;
    }

    public void setJifen(long jifen) {
        this.jifen = jifen;
    }
}
