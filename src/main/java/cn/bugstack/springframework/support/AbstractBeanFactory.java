package cn.bugstack.springframework.support;

import cn.bugstack.springframework.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;

//提供Bean工厂最基本的功能
public interface AbstractBeanFactory {
    Object getBean(String name) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
