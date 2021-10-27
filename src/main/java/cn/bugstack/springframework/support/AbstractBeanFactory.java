package cn.bugstack.springframework.support;

import cn.bugstack.springframework.BeanFactory;
import cn.bugstack.springframework.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractBeanFactory implements BeanFactory {
    public abstract void beanDefinitionRegister(String name, BeanDefinition beanDefinition);
}
