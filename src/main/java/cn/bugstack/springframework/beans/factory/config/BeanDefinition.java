package cn.bugstack.springframework.beans.factory.config;


import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.context.ApplicationContext;
import cn.bugstack.springframework.beans.factory.BeanFactory;

/*
 * 用于定义Bean实例化信息
 * */
public class BeanDefinition {
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private Class beanClass;
    private Properties properties;
    private String initMethodName;
    private String destroyMethodName;
    private String scope = SCOPE_SINGLETON;
    private boolean singleton = true;
    private boolean prototype = false;


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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public void setPrototype(boolean prototype) {
        this.prototype = prototype;
    }
}


