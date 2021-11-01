package cn.bugstack.springframework.beans.context.config;


import cn.bugstack.springframework.beans.context.Event;

public class ContextClosedEvent extends Event {
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
