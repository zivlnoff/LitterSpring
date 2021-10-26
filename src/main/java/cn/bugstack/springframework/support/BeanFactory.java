package cn.bugstack.springframework.support;

import cn.bugstack.springframework.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory implements AbstractBeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    //如何做到单例？？
    public Object getBean(String name) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class clazz = beanDefinitionMap.get(name).getBean();
        Constructor constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }
}
