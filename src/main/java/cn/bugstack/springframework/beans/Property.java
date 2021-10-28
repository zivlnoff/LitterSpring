package cn.bugstack.springframework.beans;

import cn.bugstack.springframework.beans.factory.utils.ObjectUtils;

public class Property {
    private String name;
    private Class clazz;
    private Object value;

    Property(String name, Class clazz, Object value) {
        this.name = name;
        this.clazz = clazz;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getValue() {
        return value;
    }
}
