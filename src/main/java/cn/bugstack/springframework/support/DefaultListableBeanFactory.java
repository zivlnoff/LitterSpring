package cn.bugstack.springframework.support;

import cn.bugstack.springframework.BeanFactory;
import cn.bugstack.springframework.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DefaultListableBeanFactory {
    private AbstractBeanFactory beanFactory = new DefaultAutowireCapableBeanFactory();
    private SingleObjectRegister singleObjectRegister = new DefaultSingletonBeanRegister();

    public void register(String name, Class<?> clazz){
        if(getBean(name) != null){
            return;
        }
        BeanDefinition beanDefinition = new BeanDefinition(clazz);
        beanFactory.beanDefinitionRegister(name, beanDefinition);

        try {
            Object object = clazz2Object(clazz);
            singleObjectRegister.singleObjectRegister(name, object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String name){
        return singleObjectRegister.getBean(name);
    }

    private Object clazz2Object(Class<? extends Object> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = clazz.getConstructor();
        return constructor.newInstance();
    }
}
