package cn.bugstack.springframework.beans.service.support;

import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import org.dom4j.DocumentException;

import java.util.Map;

//当依赖或上升到关联到另一个接口，就值得抽象一层，而且适合定义变量（嗯，再想想）；
public interface BeanDefinitionReader {
    public abstract Map<String, BeanDefinition> resource2BeanDefinitions(String location) throws ClassNotFoundException, DocumentException;
}
