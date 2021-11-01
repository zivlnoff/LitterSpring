package cn.bugstack.springframework.beans.context.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.context.Event;
import cn.bugstack.springframework.beans.context.EventMultiCaster;

import java.beans.Beans;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventMultiCaster implements EventMultiCaster {
    private List<EventListener<? extends Event>> eventListenerTable;

    public AbstractEventMultiCaster(){
        eventListenerTable = new ArrayList<>();
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        eventListenerTable.add(eventListener);
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        eventListenerTable.remove(eventListener);
    }

    protected List<EventListener> getMatchedListeners(Event event){
        List<EventListener> allListeners = new ArrayList<>();
        for(EventListener<? extends Event> eventListener: eventListenerTable){
            if(supportEventMatch(eventListener, event)) allListeners.add(eventListener);
        }
        return allListeners;
    }

    private boolean supportEventMatch(EventListener<? extends Event> eventListener, Event event){
        Class<? extends EventListener> aClass = eventListener.getClass();
        Type genericInterfaces = aClass.getGenericInterfaces()[0];

        Type actualArgumentType = ((ParameterizedType)genericInterfaces).getActualTypeArguments()[0];

        //好像更安全？
        String className = actualArgumentType.getTypeName();
        Class<?> eventClass;
        try{
            eventClass = Class.forName(className);
        }
        catch (ClassNotFoundException e){
            throw new BeansException("wrong event class name: " + className);
        }

        return eventClass.isAssignableFrom(event.getClass());
    }
}
