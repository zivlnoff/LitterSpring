package cn.bugstack.springframework.beans.factory.config;

public interface AutowireCapableBeanFactory {
    Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName);
    Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName);
}
