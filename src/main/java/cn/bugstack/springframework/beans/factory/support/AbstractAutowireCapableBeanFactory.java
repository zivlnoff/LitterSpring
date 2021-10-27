package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBeanObject(String name, BeanDefinition beanDefinition) throws BeansException{
        Object object = null;
        try {
            object = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("创建bean单例失败");
        }
        addSingleton(name, object);

        System.out.println("单例创建");
        return object;
    }
}
