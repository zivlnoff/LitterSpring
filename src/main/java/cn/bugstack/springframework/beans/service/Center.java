package cn.bugstack.springframework.beans.service;

import cn.bugstack.springframework.beans.context.support.AbstractRefreshableApplicationContext;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.service.support.BeanDefinitionReader;
import com.sun.tools.javac.util.Assert;

import java.util.Map;

public class Center {
    private AbstractRefreshableApplicationContext refreshableApplicationContext;
    private BeanDefinitionReader beanDefinitionReader;

    public Center(AbstractRefreshableApplicationContext refreshableApplicationContext , BeanDefinitionReader beanDefinitionReader) {
        this.refreshableApplicationContext = refreshableApplicationContext;
        this.beanDefinitionReader = beanDefinitionReader;
    }

    public void loadResource(String location){
        Map<String, BeanDefinition> producedBeanDefinition = null;

        try {
            producedBeanDefinition = beanDefinitionReader.resource2BeanDefinitions(location);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.checkNonNull(producedBeanDefinition, "producedBeanDefinition is null.");

        refreshableApplicationContext.refresh(producedBeanDefinition);
    }

    public void setBeanDefinitionReader(BeanDefinitionReader beanDefinitionReader) {
        this.beanDefinitionReader = beanDefinitionReader;
    }

    public BeanDefinitionReader getBeanDefinitionReader() {
        return beanDefinitionReader;
    }

    public void setRefreshableApplicationContext(AbstractRefreshableApplicationContext refreshableApplicationContext) {
        this.refreshableApplicationContext = refreshableApplicationContext;
    }

    public AbstractRefreshableApplicationContext getRefreshableApplicationContext() {
        return refreshableApplicationContext;
    }
}
