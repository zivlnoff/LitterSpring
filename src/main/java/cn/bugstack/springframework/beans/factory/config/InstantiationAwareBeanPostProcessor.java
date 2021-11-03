package cn.bugstack.springframework.beans.factory.config;

import cn.bugstack.springframework.aop.ClassFilter;
import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName);

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    //不愿给子类写了，懒
    default Properties postProcessPropertyValues(Properties properties, Object bean, String beanName) {
        return null;
    }
}
