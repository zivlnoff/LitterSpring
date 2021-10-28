package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeanReference;
import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InitiationStrategy initiationStrategy = new SimpleInitiationStrategy();

    //新修改必须在修改已有代码和扩展中做权衡
    @Override
    protected Object createBeanObject(String name, BeanDefinition beanDefinition) throws BeansException {
        Object object;

        Properties properties = beanDefinition.getBeanDefinitionProperties();
        if(properties != null) {
            Object[] objects = beanDefinition.getBeanDefinitionProperties().getPropertiesObject();
            Object[] arguments = new Object[objects.length];

            for (int i = 0; i < arguments.length; i++) {
                if (objects[i] instanceof BeanReference) {
                    arguments[i] = getBean(((BeanReference) objects[i]).getName());
                } else {
                    arguments[i] = objects[i];
                }
            }
            object = initiationStrategy.initialMethod(beanDefinition, arguments);
        }
        else{
            object = initiationStrategy.initialMethod(beanDefinition);
        }

        addSingleton(name, object);
        System.out.println("单例创建");

        return object;
    }
}
