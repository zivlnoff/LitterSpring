package cn.bugstack.springframework.event;

import cn.bugstack.springframework.beans.context.config.ContextRefreshedEvent;
import cn.bugstack.springframework.beans.context.support.EventListener;

public class ContextRefreshedEventListener implements EventListener<ContextRefreshedEvent> {
    @Override
    public void callback(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }
}
