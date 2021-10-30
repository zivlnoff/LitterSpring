package cn.bugstack.springframework.beans.factory;

public interface BeanFactory {
    //暂时只满足先注册后get
    Object getBean(String name);
    <T> T getBean(String name, Class<T> tClass);
}
