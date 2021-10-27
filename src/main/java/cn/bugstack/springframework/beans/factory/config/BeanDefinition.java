package cn.bugstack.springframework.beans.factory.config;

/*
* 用于定义Bean实例化信息
* */
public class BeanDefinition {
    private Class bean;

    public BeanDefinition(Class bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return bean;
    }
}

