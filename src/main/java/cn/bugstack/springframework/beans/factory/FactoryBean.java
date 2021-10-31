package cn.bugstack.springframework.beans.factory;

public interface FactoryBean<T>{
    T getObject();
    ClassLoader getClassLoader();
    Class<?> getType();
    boolean isSingleton();
}
