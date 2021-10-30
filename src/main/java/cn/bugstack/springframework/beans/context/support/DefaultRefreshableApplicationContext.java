package cn.bugstack.springframework.beans.context.support;

import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;

public class DefaultRefreshableApplicationContext extends AbstractRefreshableApplicationContext{
    public DefaultRefreshableApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }
}
