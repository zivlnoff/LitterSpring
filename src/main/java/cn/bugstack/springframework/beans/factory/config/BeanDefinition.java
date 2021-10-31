package cn.bugstack.springframework.beans.factory.config;


import cn.bugstack.springframework.beans.Properties;

/*
 * 用于定义Bean实例化信息
 * */
public class BeanDefinition {
    private Class beanClass;
    private Properties properties;
    private String initMethodName;
    private String destroyMethodName;

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

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


