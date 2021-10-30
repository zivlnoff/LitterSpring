package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.context.ConfigurableApplicationContext;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//这里加一个ConfigurableListableBeanFactory合适吗？
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public void beanDefinitionRegistry(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    @Override
    public void beanDefinitionRegistry(Map<String, BeanDefinition> beanDefinitionMap) {
        this.beanDefinitionMap = beanDefinitionMap;
    }

    @Override
    public BeanDefinition getBeanDefinition(String name){
        //Null，可抛异常
        return beanDefinitionMap.get(name);
    }

    @Override
    public void preInstantiateSingletons() {
        //java这么强大的吗？这个Consumer<T>也忒智能了
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public Set<String> getBeanDefinitionNames() {
        return beanDefinitionMap.keySet();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();
        for(Map.Entry<String, BeanDefinition> entry: beanDefinitionMap.entrySet()){
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if(type.isAssignableFrom(beanDefinition.getBeanClass())){
                result.put(beanName, (T) getBean(beanName));
            }
        }
        return result;
    }
}
