package cn.bugstack.springframework.support;

import cn.bugstack.springframework.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    @Override
    public void beanDefinitionRegister(String name, BeanDefinition beanDefinition) {
        register(name, beanDefinition);
    }

    abstract protected void register(String name, BeanDefinition beanDefinition);
}
