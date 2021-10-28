package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import com.sun.tools.internal.jxc.ap.Const;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInitiationStrategy implements InitiationStrategy {
    @Override
    public Object initialMethod(BeanDefinition beanDefinition, Object... objects) {
        Object object = null;
        Constructor constructor = null;

        int len = objects.length;
        Class[] classes = new Class[len];

        for(int i = 0; i<len; i++){
            classes[i] = objects[i].getClass();
        }

        try {
            constructor = beanDefinition.getBeanClass().getDeclaredConstructor(classes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        constructor.setAccessible(true);

        try {
            object = constructor.newInstance(objects);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}
