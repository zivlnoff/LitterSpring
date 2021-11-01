package cn.bugstack.springframework.beans.context.support;

import cn.bugstack.springframework.beans.context.ConfigurableApplicationContext;
import cn.bugstack.springframework.beans.context.Event;
import cn.bugstack.springframework.beans.context.EventMultiCaster;
import cn.bugstack.springframework.beans.context.config.ContextClosedEvent;
import cn.bugstack.springframework.beans.context.config.ContextRefreshedEvent;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import cn.bugstack.springframework.beans.factory.support.ApplicationContextAwareProcessor;

import java.util.Map;
import java.util.Set;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext{
    private EventMultiCaster eventMultiCaster;

    @Override
    public void refresh(Map<String, BeanDefinition> beanDefinitionMap) {
        //初始化容器->主要是定义注册BeanDefinition方法，对吗。
        refreshBeanFactory(beanDefinitionMap);

        //获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //添加Processor让bean实例化的时候能感知到ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //invokeBeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //设置事件监听广播器
        initEventMultiCaster();

        //注册事件监听器
        registerEventListener();

        //preInstantiateSingletons实例bean
        beanFactory.preInstantiateSingletons();

        //结束Refresh事件发生
        finishRefresh();
    }

    private void finishRefresh() {
        eventOccur(new ContextRefreshedEvent(this));
    }

    @Override
    public void eventOccur(Event event) {
        eventMultiCaster.multiCastEvent(event);
    }

    private void registerEventListener() {
        Map<String, EventListener> eventListenerMap = getBeanFactory().getBeansOfType(EventListener.class);
        for(EventListener eventListener: eventListenerMap.values()){
            eventMultiCaster.addEventListener(eventListener);
        }
    }

    private void initEventMultiCaster() {
        eventMultiCaster = new SimpleEventMultiCaster();
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
        //发布容器关闭事件
        eventOccur(new ContextClosedEvent(this));

        //执行销毁单例bean的销毁方法
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
