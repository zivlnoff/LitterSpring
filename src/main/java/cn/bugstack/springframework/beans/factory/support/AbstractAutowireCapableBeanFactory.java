package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InitiationStrategy initiationStrategy = new SimpleInitiationStrategy();

    @Override
    protected Object createBeanObject(String name, BeanDefinition beanDefinition, Object... objects) throws BeansException {
        Object object = initiationStrategy.initialMethod(beanDefinition, objects);
        addSingleton(name, object);
        System.out.println("单例创建");
        return object;
    }
}
