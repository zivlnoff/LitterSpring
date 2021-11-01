package cn.bugstack.springframework.beans.context.config;

import cn.bugstack.springframework.beans.context.Event;

public class ContextRefreshedEvent extends Event {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }

}