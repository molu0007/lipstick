package com.qyy.app.lipstick.event;

/**
 * 通用事件
 * Created by 曾丽 on 2017/6/24.
 */

public class MessageEvent<T> {

    public EventType type;

    public T data;

    public MessageEvent(EventType type, T data) {
        this.type = type;
        this.data = data;
    }
}
