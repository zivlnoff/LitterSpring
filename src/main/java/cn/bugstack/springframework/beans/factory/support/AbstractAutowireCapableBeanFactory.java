package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.Property;
import cn.bugstack.springframework.beans.factory.config.AutowireCapableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import cn.bugstack.springframework.beans.factory.config.BeanReference;
import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.hutool.core.bean.BeanUtil;

import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        //生成beanObject
        Object beanObject = createBeanObject(beanDefinition);

        //properties注入
        beanPropertiesInjection(beanObject, beanName, beanDefinition);

        //执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
        beanObject = initializeBean(beanName, beanObject, beanDefinition);

        //单例存入内存
        addSingleton(beanName, beanObject);
        return beanObject;
    }

    private Object createBeanObject(BeanDefinition beanDefinition) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        Object object = null;
        try {
            return object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("createBeanObject error");
        }
    }

    private void beanPropertiesInjection(Object beanObject, String beanName, BeanDefinition beanDefinition) {
        List<Property> properties = beanDefinition.getBeanDefinitionProperties().getPropertiesList();
        int len = properties.size();
        for (int i = 0; i < len; i++) {
            Property property = properties.get(i);
            String name = property.getName();
            Object value = property.getValue();

            if (value instanceof BeanReference) {
                value = getBean(name);
            }

            BeanUtil.setFieldValue(beanObject, name, value);
        }
    }

    private Object initializeBean(String beanName, Object beanObject, BeanDefinition beanDefinition){
        //实例化前置操作
        //为什么不直接在beanObject上进行修改
        Object wrappedBean = applyBeanPostProcessorBeforeInitialization(beanObject, beanName);

        //因为这里困扰了很久
        //待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        //实例化后置操作
        wrappedBean = applyBeanPostProcessorAfterInitialization(wrappedBean, beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String name, Object wrappedBean, BeanDefinition beanDefinition){
        //待完成
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object beanObject, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for(BeanPostProcessor beanPostProcessor: beanPostProcessors){
            beanPostProcessor.postProcessBeforeInitialization(beanObject, beanName);
        }
        return beanObject;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object wrappedBean, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for(BeanPostProcessor beanPostProcessor: beanPostProcessors){
            beanPostProcessor.postProcessAfterInitialization(wrappedBean, beanName);
        }
        return wrappedBean;
    }
}
