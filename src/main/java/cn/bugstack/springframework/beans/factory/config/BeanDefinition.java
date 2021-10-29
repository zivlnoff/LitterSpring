package cn.bugstack.springframework.beans.factory.config;


import cn.bugstack.springframework.beans.Properties;

/*
 * 用于定义Bean实例化信息
 * */
public class BeanDefinition {
    private Class beanClass;
    private Properties properties;

    public BeanDefinition(Class beanClass) {
        this(beanClass, new Properties());
    }

    public BeanDefinition(Class beanClass, Properties properties) {
        this.beanClass = beanClass;
        this.properties = properties;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public Properties getBeanDefinitionProperties() {
        return properties;
    }
}


