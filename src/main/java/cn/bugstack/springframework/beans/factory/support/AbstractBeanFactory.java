package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.BeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import sun.swing.BeanInfoUtils;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    abstract protected Object createBeanObject(String name, BeanDefinition beanDefinition) throws BeansException;

    abstract protected BeanDefinition getBeanDefinition(String name);

    @Override
    public Object getBean(String name) {
        Object object = getSingleton(name);
        if (object != null) {
            return object;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        object = createBeanObject(name, beanDefinition);

        return object;
    }
}
