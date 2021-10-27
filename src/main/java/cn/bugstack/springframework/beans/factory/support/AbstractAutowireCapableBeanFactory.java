package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBeanObject(String name, BeanDefinition beanDefinition, Object... objects) throws BeansException{
        Object object = null;
        Constructor constructor = null;
        try {
            int len = objects.length;
            Class[] classes = new Class[len];
            for (int i = 0; i<len; i++){
                classes[i] = objects[i].getClass();
            }
            constructor = beanDefinition.getBeanClass().getDeclaredConstructor(classes);
            object = constructor.newInstance(objects);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("创建bean单例失败");
        } catch (NullPointerException e){
            throw new BeansException("未注册Bean, NullPointerException");
        }
        addSingleton(name, object);

        System.out.println("单例创建");
        return object;
    }
}
