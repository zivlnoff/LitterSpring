package cn.bugstack.springframework.beans.context.support;

import cn.bugstack.springframework.beans.context.ConfigurableApplicationContext;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext{
    @Override
    public void refresh(Map<String, BeanDefinition> beanDefinitionMap) {
        //初始化容器->主要是定义注册BeanDefinition方法，对吗。
        refreshBeanFactory(beanDefinitionMap);

        //获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //invokeBeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //所有的属性都是注入进去，没有任何组合的，太屌了。
        //4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //preInstantiateSingletons实例bean
        beanFactory.preInstantiateSingletons();
    }

    abstract protected void refreshBeanFactory(Map<String, BeanDefinition> beanDefinitionMap);
    abstract public ConfigurableListableBeanFactory getBeanFactory();

    //因为不是组合去解决这个调用问题，而是通过把BeanFactoryPostProcessor注册进去来解决
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor: beanFactoryPostProcessors.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor: beanPostProcessors.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public Set<String> getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> tClass) {
        return getBeanFactory().getBean(name, tClass);
    }
}
