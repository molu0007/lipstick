package com.qyy.app.lipstick.event;

/**
 * 通知事件(不带参数)
 * Created by 曾丽 on 2017/6/24.
 */

public class EmptyEvent {

    public EventType type;

    public EmptyEvent(EventType type) {
        this.type = type;
    }
}
