package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.context.ApplicationContext;

public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext applicationContext);
}
