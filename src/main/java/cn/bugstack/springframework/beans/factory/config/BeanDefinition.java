package cn.bugstack.springframework.beans.factory.config;


import cn.bugstack.springframework.beans.Properties;

/*
* 用于定义Bean实例化信息
* */
public class BeanDefinition {
    private Class bean;
    private Properties properties;

    public BeanDefinition(Class bean) {
        this.bean = bean;
    }
    public BeanDefinition(Class bean, Properties properties){
        this.bean = bean;
        this.properties = properties;
    }

    public Class getBeanClass() {
        return bean;
    }

    public Properties getBeanDefinitionProperties(){
        return properties;
    }
}


