package cn.bugstack.springframework.beans.factory;


public interface BeanClassLoaderAware extends Aware{
    void setBeanClassLoaderAware(ClassLoader classLoader);
}
