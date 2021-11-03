package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.BeansException;

public interface BeanFactory {
    Object getBean(String name);
    <T> T getBean(String name, Class<T> tClass);
    <T> T getBean(Class<T> requiredType) throws BeansException;
}
