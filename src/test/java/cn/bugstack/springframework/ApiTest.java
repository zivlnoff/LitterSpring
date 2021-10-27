package cn.bugstack.springframework;

import cn.bugstack.springframework.bean.UserService;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

public class ApiTest {
    @Test
    public void test_BeanFactory(){
        //初始化
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.beanDefinitionRegistry("userService1",beanDefinition);

        //第一次获取bean1
        UserService userService1 = (UserService) beanFactory.getBean("userService1", "mmm");
        userService1.queryUserInfo();

        //第一次获取bean2, 不存在对应名称，抛异常，而且有前提，2个不一样的Object不能有一样的name
        UserService userService2 = (UserService) beanFactory.getBean("userService2","kkk");
        userService2.queryUserInfo();
    }
}
