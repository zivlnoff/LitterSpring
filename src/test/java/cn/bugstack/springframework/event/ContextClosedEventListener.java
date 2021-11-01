package cn.bugstack.springframework.event;

import cn.bugstack.springframework.beans.context.config.ContextClosedEvent;
import cn.bugstack.springframework.beans.context.support.EventListener;

public class ContextClosedEventListener implements EventListener<ContextClosedEvent> {
    @Override
    public void callback(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }
}
