package cn.bugstack.springframework.beans.factory.config;

/**
 * Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link cn.bugstack.springframework.beans.factory.BeanFactory}
 * interface.
 */
public interface ConfigurableBeanFactory {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
