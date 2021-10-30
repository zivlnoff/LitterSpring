package cn.bugstack.springframework.beans.context;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.util.Map;

public interface ConfigurableApplicationContext extends ApplicationContext{
    void refresh(Map<String, BeanDefinition> beanDefinitionMap);
}
