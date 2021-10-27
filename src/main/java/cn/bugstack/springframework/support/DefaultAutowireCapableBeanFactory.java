package cn.bugstack.springframework.support;

import cn.bugstack.springframework.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultAutowireCapableBeanFactory extends AbstractAutowireCapableBeanFactory{
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    protected void register(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    @Override
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }
}
