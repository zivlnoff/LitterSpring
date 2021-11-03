package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.annotation.StringValueResolver;
import cn.bugstack.springframework.beans.factory.BeanFactory;
import cn.bugstack.springframework.beans.factory.FactoryBean;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;
import cn.bugstack.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.bugstack.springframework.utils.ClassUtils;
import sun.swing.BeanInfoUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
    private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    abstract protected Object createBean(String name, BeanDefinition beanDefinition);

    abstract protected BeanDefinition getBeanDefinition(String name);

    @Override
    public Object getBean(String name) {
        Object object = getSingleton(name);
        if (object != null) {
            if(object instanceof FactoryBean){
                return getObjectFromFactoryBean((FactoryBean) object, name);
            }
            return object;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        object = createBean(name, beanDefinition);

        if(object instanceof FactoryBean){
            object = getObjectFromFactoryBean((FactoryBean) object, name);
        }

        return object;
    }

    @Override
    public <T> T getBean(String name, Class<T> tClass) {
        return (T) getBean(name);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    protected List<BeanPostProcessor> getBeanPostProcessors(){
        return beanPostProcessors;
    }

    protected ClassLoader getBeanClassLoader(){
        return classLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        embeddedValueResolvers.add(valueResolver);
    }

    public String resolveEmbeddedValue(String value){
        String result = value;
        for(StringValueResolver stringValueResolver: this.embeddedValueResolvers){
            result = stringValueResolver.resolveStringValue(result);
        }
        return result;
    }
}
