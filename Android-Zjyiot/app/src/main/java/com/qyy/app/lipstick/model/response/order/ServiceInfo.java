package com.qyy.app.lipstick.model.response.order;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-18 14:08
 * @name: ServiceInfo
 */
public class ServiceInfo {

    /**
     * ad_url : https://upload-1253528786.cosbj.myqcloud.com/upload/20190227/20345411627a47.png
     * app_name : 口红机
     * free_game_url : https://api.jucaib.com/taste.html?h5=1
     * kefu : https://upload-1253528786.cosbj.myqcloud.com/upload/20190303/214002732385e9.png
     * show_daicao : 1
     * weixin_id : cyl817825
     */

    private String ad_url;
    private String app_name;
    private String free_game_url;
    private String kefu;
    private int show_daicao;
    private String weixin_id;

    private String h5Url;
    private String h5Name;

    public String getH5Name() {
        return h5Name;
    }

    public void setH5Name(String h5Name) {
        this.h5Name = h5Name;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getFree_game_url() {
        return free_game_url;
    }

    public void setFree_game_url(String free_game_url) {
        this.free_game_url = free_game_url;
    }

    public String getKefu() {
        return kefu;
    }

    public void setKefu(String kefu) {
        this.kefu = kefu;
    }

    public int getShow_daicao() {
        return show_daicao;
    }

    public void setShow_daicao(int show_daicao) {
        this.show_daicao = show_daicao;
    }

    public String getWeixin_id() {
        return weixin_id;
    }

    public void setWeixin_id(String weixin_id) {
        this.weixin_id = weixin_id;
    }
}
