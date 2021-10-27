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
        beanFactory.beanDefinitionRegistry("userService",beanDefinition);

        //第一次获取bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();

        //第二次获取bean
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        userService2.queryUserInfo();
    }
}
