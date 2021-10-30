package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.util.Map;

public interface BeanDefinitionRegistry {
    void beanDefinitionRegistry(String name, BeanDefinition beanDefinition);
    void beanDefinitionRegistry(Map<String, BeanDefinition> beanDefinitionMap);
}
