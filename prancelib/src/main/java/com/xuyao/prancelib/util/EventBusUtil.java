package com.xuyao.prancelib.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by T470 on 2017/9/26.
 * 事件总线
 */

public class EventBusUtil {

    EventMode eventModes[]=new EventMode[0];
    public EventBusUtil(EventMode ...eventModes){
        this.eventModes=eventModes;
    }
    /**
     * 事件枚举
     */
    public enum EventMode {
        pleaseLogin,
        reserver;
        private Object data;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    public EventMode[] getEventModes() {
        return eventModes;
    }

    public EventBusUtil setEventModes(EventMode[] eventModes) {
        this.eventModes = eventModes;
        return this;
    }

    /**
     * 注册
     * @param o
     */
    public static void registerEventBus(Object o){
        EventBus.getDefault().register(o);
    }

    /**
     * 反注册
     * @param o
     */
    public static void unregisterEventBus(Object o){
        EventBus.getDefault().unregister(o);
    }

    /**
     * 发送订阅
     * @param sendOb
     */
    public static void sendEventBus(Object sendOb){
        EventBus.getDefault().post(sendOb);
    }

}
