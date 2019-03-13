package com.qyy.app.lipstick.event;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus的管理者
 * Created by 曾丽 on 2017/6/24.
 * @author hoop
 */

public class EventManager {

    /**
     * EventBus实例
     */
    private static EventBus sInstance;

    /**
     * Registers the given subscriber to receive events. Subscribers must call {@link #unregister(Object)} once they
     * are no longer interested in receiving events.
     */
    public static void register(Object obj) {
        if (!getInstance().isRegistered(obj)){
            getInstance().register(obj);
        }
    }

    /**
     * Unregisters the given subscriber from all event classes.
     */
    public static void unregister(Object obj) {
        if (getInstance().isRegistered(obj)){
            getInstance().unregister(obj);
        }
    }

    /**
     * Posts the given event to the event bus.
     *
     * @param event 消息体
     */
    public static void post(EmptyEvent event) {
        getInstance().post(event);
    }

    /**
     * Posts the given event to the event bus and holds on to the event (because it is sticky). The most recent sticky
     * event of an event's type is kept in memory for future access by subscribers using
     */
    public static void postStiky(EmptyEvent event) {
        getInstance().postSticky(event);
    }

    /**
     * Posts the given event to the event bus.
     *
     * @param event 消息体
     */
    public static <T> void post(MessageEvent<T> event) {
        getInstance().post(event);
    }





    /**
     * Posts the given event to the event bus and holds on to the event (because it is sticky). The most recent sticky
     * event of an event's type is kept in memory for future access by subscribers using
     */
    public static <T> void postStiky(MessageEvent<T> event) {
        getInstance().postSticky(event);
    }

    /**
     * Remove and gets the recent sticky event for the given event type.
     */
    public static <E> E removeStickyEvent(Class<E> eventType) {
        return getInstance().removeStickyEvent(eventType);
    }

    /**
     * 获取EventBus实例(单例模式)
     *
     * @return  事件树实例
     */
    private static EventBus getInstance() {
        if (sInstance == null) {
            synchronized (EventManager.class) {
                if (sInstance == null) {
                    sInstance = EventBus.builder()
                            .logNoSubscriberMessages(false)
                            .sendNoSubscriberEvent(false)
//                            .throwSubscriberException(true)
                            .build();
                }
            }
        }
        return sInstance;
    }

}
