package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.factory.BeanFactory;
import cn.bugstack.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.bugstack.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Map;
import java.util.Set;

/**
 * Extension of the {@link BeanFactory} interface to be implemented by bean factories
 * that can enumerate all their bean instances, rather than attempting bean lookup
 * by name one by one as requested by clients. BeanFactory implementations that
 * preload all their bean definitions (such as XML-based factories) may implement
 * this interface.
 */

//实现预装载之后，给予client获取beanName的接口
public interface ListableBeanFactory extends BeanFactory {
    Set<String> getBeanDefinitionNames();

    //泛型方法，好好学一下
    <T> Map<String, T> getBeansOfType(Class<T> type);
}
