package cn.bugstack.springframework.beans.interaction.support;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.resolver.config.Resource;

import java.util.List;
import java.util.Map;

//当依赖或上升到关联到另一个接口，就值得抽象一层，而且适合定义变量（嗯，再想想）；
public interface BeanDefinitionReader {
    public abstract Map<String, BeanDefinition> resource2BeanDefinitions(String location) throws ClassNotFoundException;
}