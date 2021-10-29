package cn.bugstack.springframework.beans.interaction;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.bugstack.springframework.beans.interaction.support.AbstractBeanDefinitionReader;
import com.sun.tools.javac.util.Assert;

import java.util.Map;

public class Center {
    private BeanDefinitionRegistry beanFactory;
    private AbstractBeanDefinitionReader beanDefinitionReader;

    public Center(BeanDefinitionRegistry beanFactory, AbstractBeanDefinitionReader beanDefinitionReader) {
        this.beanFactory = beanFactory;
        this.beanDefinitionReader = beanDefinitionReader;
    }

    public void loadResource(String location){
        Map<String, BeanDefinition> producedBeanDefinition = null;

        try {
            producedBeanDefinition = beanDefinitionReader.resource2BeanDefinition(location);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.checkNonNull(producedBeanDefinition, "producedBeanDefinition is null.");
        for(Map.Entry<String, BeanDefinition> entry: producedBeanDefinition.entrySet()) {
            beanFactory.beanDefinitionRegistry(entry.getKey(), entry.getValue());
        }
    }

    public void setBeanFactory(BeanDefinitionRegistry beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanDefinitionRegistry getBeanFactory() {
        return beanFactory;
    }

    public void setBeanDefinitionReader(AbstractBeanDefinitionReader beanDefinitionReader) {
        this.beanDefinitionReader = beanDefinitionReader;
    }

    public AbstractBeanDefinitionReader getBeanDefinitionReader() {
        return beanDefinitionReader;
    }
}
