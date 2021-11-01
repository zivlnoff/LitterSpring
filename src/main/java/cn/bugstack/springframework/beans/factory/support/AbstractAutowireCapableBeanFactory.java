package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.Property;
import cn.bugstack.springframework.beans.factory.*;
import cn.bugstack.springframework.beans.factory.config.*;
import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object beanObject = null;
        try {
            // 判断是否返回代理 Bean 对象
            beanObject = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != beanObject) {
                return beanObject;
            }

            //生成beanObject
            beanObject = createBeanObject(beanDefinition);

            //properties注入
            beanPropertiesInjection(beanObject, beanName, beanDefinition);

            //执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            beanObject = initializeBean(beanName, beanObject, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        //
        registerDisposableBeanIfNecessary(beanName, beanObject, beanDefinition);

        //单例存入内存
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, beanObject);
        }
        return beanObject;
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
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
                Object tmp = value;
                value = null;
                value = getBean(((BeanReference) tmp).getName());
            }

            BeanUtil.setFieldValue(beanObject, name, value);
        }
    }

    private Object initializeBean(String beanName, Object beanObject, BeanDefinition beanDefinition) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (beanObject instanceof BeanNameAware) {
            ((BeanNameAware) beanObject).setBeanName(beanName);
        }
        if (beanObject instanceof BeanFactoryAware) {
            ((BeanFactoryAware) beanObject).setBeanFactory(this);
        }
        if (beanObject instanceof BeanClassLoaderAware) {
            ((BeanClassLoaderAware) beanObject).setBeanClassLoaderAware(getBeanClassLoader());
        }

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

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (wrappedBean instanceof InitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(wrappedBean);
        }
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object beanObject, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessBeforeInitialization(beanObject, beanName);
        }
        return beanObject;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object wrappedBean, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessAfterInitialization(wrappedBean, beanName);
        }
        return wrappedBean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (!beanDefinition.isSingleton()) return;

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }
}
