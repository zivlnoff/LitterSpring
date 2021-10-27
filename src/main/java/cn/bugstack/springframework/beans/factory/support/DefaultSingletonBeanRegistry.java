package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonObject = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObject.get(beanName);
    }

    public void addSingleton(String name, Object singleObject){
        singletonObject.put(name, singleObject);
    }
}
