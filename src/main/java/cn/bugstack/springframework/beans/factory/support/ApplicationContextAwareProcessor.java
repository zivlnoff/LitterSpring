package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.context.ApplicationContext;
import cn.bugstack.springframework.beans.factory.ApplicationContextAware;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import sun.tools.jconsole.inspector.XObject;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
