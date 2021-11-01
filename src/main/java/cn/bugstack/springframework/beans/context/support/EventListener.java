package cn.bugstack.springframework.beans.context.support;

import cn.bugstack.springframework.beans.context.Event;

public interface EventListener<T> {
    void callback(T event);
}
