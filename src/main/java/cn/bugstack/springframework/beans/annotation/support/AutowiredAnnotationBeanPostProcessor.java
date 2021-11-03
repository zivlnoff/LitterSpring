package cn.bugstack.springframework.beans.annotation.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.annotation.Autowired;
import cn.bugstack.springframework.beans.annotation.Qualifier;
import cn.bugstack.springframework.beans.annotation.Value;
import cn.bugstack.springframework.beans.factory.BeanFactory;
import cn.bugstack.springframework.beans.factory.BeanFactoryAware;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.bugstack.springframework.utils.ClassUtils;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory){
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
     }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return beanClass;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Properties postProcessPropertyValues(Properties properties, Object bean, String beanName) {
        //1.处理注解@value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declareFields = clazz.getDeclaredFields();

        for(Field field: declareFields){
            Value valueAnnotation = field.getAnnotation(Value.class);
            if(null != valueAnnotation){
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        //2.处理注解@Autowired
        for(Field field: declareFields){
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if(null != autowiredAnnotation){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);

                Object dependentBean = null;
                if(null != qualifierAnnotation){
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                }
                else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return properties;
    }
}
