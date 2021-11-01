package cn.bugstack.springframework.beans.common;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        Properties properties = beanDefinition.getBeanDefinitionProperties();

        properties.addPropertyValue("company", "字节跳动");
    }
}
