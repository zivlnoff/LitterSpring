package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.Property;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.utils.ObjectUtils;

import java.util.Map;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InitiationStrategy initiationStrategy = new SimpleInitiationStrategy();

    //新修改必须在修改已有代码和扩展中做权衡
    @Override
    protected Object createBeanObject(String name, BeanDefinition beanDefinition, Object... objects) throws BeansException {
        Object object;
        if(beanDefinition.getBeanDefinitionProperties() == null || objects.length != 0) {
            object = initiationStrategy.initialMethod(beanDefinition, objects);
            addSingleton(name, object);
            System.out.println("单例创建");
        }
        else{
            //多加了一个参数，哭了，找bug
//            object = getBean(name, beanDefinition, beanDefinition.getBeanDefinitionProperties().getPropertiesObject());
            object = getBean(name, beanDefinition.getBeanDefinitionProperties().getPropertiesObject());
        }
        return object;
    }
}
