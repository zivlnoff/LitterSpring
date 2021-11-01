package cn.bugstack.springframework.event;

import cn.bugstack.springframework.beans.context.support.EventListener;

import java.util.Date;

public class CustomEventListener implements EventListener<CustomEvent> {

    @Override
    public void callback(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }
}
