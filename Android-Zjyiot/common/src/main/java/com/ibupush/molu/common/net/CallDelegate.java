package com.ibupush.molu.common.net;

/**
 * 网络请求的分发接口
 * Created by 曾丽 on 2017/8/3.
 */

public interface CallDelegate {


    /**
     * 宿主Activity或Fragment是否已销毁
     * @return  trrue:已销毁,false,正常情况
     */
    boolean isHostDestroyed();

}
