package cn.bugstack.springframework.beans.context.support;

import cn.bugstack.springframework.beans.context.Event;
import cn.bugstack.springframework.beans.context.EventMultiCaster;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SimpleEventMultiCaster extends AbstractEventMultiCaster {
    //理解广播的意思
    @Override
    public void multiCastEvent(Event event) {
        for(EventListener eventListener: getMatchedListeners(event)){
            eventListener.callback(event);
        }
    }
}
