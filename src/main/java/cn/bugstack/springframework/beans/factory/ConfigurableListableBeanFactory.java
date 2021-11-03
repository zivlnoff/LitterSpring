package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.factory.config.*;
import cn.bugstack.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.List;

//感觉没必要实现AutowireCapableBeanFactory这个接口呢？ß
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, AutowireCapableBeanFactory, ListableBeanFactory, BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String name);
    void preInstantiateSingletons();

}
