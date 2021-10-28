package cn.bugstack.springframework.beans.factory.utils;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Field;

//此功能未用到，因为不一定都有空参构造函数，一开始设计不太明确
public class ObjectUtils {
    public static void SetFieldValue(Object object, BeanDefinition bean, String fieldName, Object value){
        Class beanClass = bean.getBeanClass();
        Field field = null;
        try {
            field = beanClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
