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

        //从这个地方看出设计有问题，如果非反射setField就必须存Class类型，如果不存一次抽象就找不到Class了
        if (objects.length != 0 && beanDefinition.getBeanDefinitionProperties() != null) {
            for (int i = 0; i < len; i++) {
                classes[i] = beanDefinition.getBeanDefinitionProperties().getPropertiesList().get(i).getClazz();
                objects[i] = classes[i].cast(objects[i]);
            }
        } else {
            for (int i = 0; i < len; i++) {
                classes[i] = objects[i].getClass();
            }
        }

        //如何优化代码
        try {
            constructor = beanDefinition.getBeanClass().getConstructor(classes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw new BeansException("No identical bean name");
        }

        try {
            object = constructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return object;
    }
}
