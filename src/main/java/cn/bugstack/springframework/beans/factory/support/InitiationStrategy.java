package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InitiationStrategy {
    Object initialMethod(BeanDefinition beanDefinition, Object... objects);
}
