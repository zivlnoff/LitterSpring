package cn.bugstack.springframework.beans;

import com.sun.tools.classfile.ConstantPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Property的成员可以用树状结构表示 是否可以采用组合结构性设计方案
//无论是引用类型还是基本类型，需要共同的接口定义，在其中声明一个通用的方法
//但是调用的方法并不在属性类里
//如果判断属性类型，可以用ClassLoader区别
//但是尽量不要用语言特性，语言特性可以人为变动？
public class Properties {
    private List<Property> properties = new ArrayList<>();

    public void addPropertyValue(String name, Object propertyValue){
        properties.add(new Property(name, propertyValue.getClass(), propertyValue));
    }

    public List<Property> getPropertiesList(){
        return properties;
        //传list还是array好呢？
    }

    public Object[] getPropertiesObject(){
        Object[] propertiesObject = new Object[properties.size()];
        for(int i = 0; i<propertiesObject.length; i++){
            propertiesObject[i] = properties.get(i).getValue();
        }
        return propertiesObject;
    }

    public Class[] getPropertiesClass(){
        Class[] classes = new Class[properties.size()];
        for(int i = 0; i<properties.size(); i++){
            classes[i] = properties.get(i).getClazz();
        }
        return classes;
    }
}
