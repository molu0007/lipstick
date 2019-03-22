package com.qyy.app.lipstick.model.response.home;

import java.util.List;

public class GoodsList {

    private List<BannerBean> banner;
    private List<GoodsBean> goods;
    private List<String> notice;

    public int getJifen() {
        return jifen;
    }

    public void setJifen(int jifen) {
        this.jifen = jifen;
    }

    private int jifen;
    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public List<String> getNotice() {
        return notice;
    }

    public void setNotice(List<String> notice) {
        this.notice = notice;
    }

    public static class BannerBean {
        /**
         * id : 8
         * ad_position_id : 1
         * media_type : 0
         * name : 口红机APP
         * link : https://www.baidu.com
         * image_url : https://upload-1253528786.cosbj.myqcloud.com/upload/20190227/20345411627a47.png
         * content : 口红机App
         * end_time : null
         * enabled : 1
         */

        private long id;
        private long ad_position_id;
        private long media_type;
        private String name;
        private String link;
        private String image_url;
        private String content;
        private Object end_time;
        private int enabled;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getAd_position_id() {
            return ad_position_id;
        }

        public void setAd_position_id(long ad_position_id) {
            this.ad_position_id = ad_position_id;
        }

        public long getMedia_type() {
            return media_type;
        }

        public void setMedia_type(long media_type) {
            this.media_type = media_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getEnd_time() {
            return end_time;
        }

        public void setEnd_time(Object end_time) {
            this.end_time = end_time;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }
    }


}
