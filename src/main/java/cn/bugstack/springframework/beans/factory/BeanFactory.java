package cn.bugstack.springframework.beans.factory;

public interface BeanFactory {
    //将属性注入的部分交付给Property以及BeanDefinition,getBean只与单例的创建及获取有关
    //组合模式设计
    Object getBean(String name, Object... objects);
}
