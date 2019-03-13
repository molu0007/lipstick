package com.ibupush.molu.common.net;

/**
 * 下载文件回调监听器
 *
 * @author Shyky
 * @date 2017/9/11
 */
public interface DownloadCallback {
    void onSuccess(String absoluteFileName);

    void onFailure(String message);
}