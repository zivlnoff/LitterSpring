package cn.bugstack.springframework.support;

import cn.bugstack.springframework.config.BeanDefinition;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegister implements SingleObjectRegister {
    private Map<String, Object> singleObjectMap = new ConcurrentHashMap<>();

    @Override
    public void singleObjectRegister(String name, Object object) {
        //test 单例
        System.out.println("单例初始化");

        singleObjectMap.put(name, object);
    }

    @Override
    public Object getBean(String name) {
        return singleObjectMap.get(name);
    }
}
