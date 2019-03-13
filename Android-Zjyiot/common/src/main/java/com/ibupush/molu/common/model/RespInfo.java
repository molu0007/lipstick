package com.ibupush.molu.common.model;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public class RespInfo<T> {
    public String errmsg;
    public int status;
    public String errno;
    public T data;
}