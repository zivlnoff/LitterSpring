package cn.bugstack.springframework.beans.context;

import cn.bugstack.springframework.beans.context.support.EventListener;

public interface EventMultiCaster {
    void addEventListener(EventListener eventListener);
    void removeEventListener(EventListener eventListener);
    void multiCastEvent(Event event);
}
