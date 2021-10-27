package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void beanDefinitionRegistry(String name, BeanDefinition beanDefinition);
}
