package cn.bugstack.springframework.beans.factory.config;

import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    /*
    * 在所有的BeanDefinition加载完成之后，实例化bean对象之前，提供干预机制
    * */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
}
