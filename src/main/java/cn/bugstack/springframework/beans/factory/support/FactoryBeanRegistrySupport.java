package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.factory.FactoryBean;
import cn.bugstack.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    private Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String name){
        if(factoryBean.isSingleton()){
            Object object = factoryBeanObjectCache.get(name);
            if(object != null){
            }
            else{
                object = doGetObjectFromFactoryBean(factoryBean);
                factoryBeanObjectCache.put(name, object);
            }
            return object;
        }
        else{
            return doGetObjectFromFactoryBean(factoryBean);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factoryBean){
        return factoryBean.getObject();
    }

}
