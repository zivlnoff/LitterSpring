package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.BeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    abstract protected Object createBeanObject(String name, BeanDefinition beanDefinition) throws BeansException;
    abstract protected BeanDefinition getBeanDefinition(String name);

    @Override
    public Object getBean(String name){
        Object object = getSingleton(name);
        if(object != null) {
            return object;
        }

        object = createBeanObject(name, getBeanDefinition(name));
        //习惯了在一个方法中写create & add方法， 但create方法实际并不是在这个类中完成的（这样描述可能表达不了我的意思），但应该将add放在真正创建的后面（如果不需要延时载入的话）。
//        addSingleton(name, object);
        return object;
    }
}
