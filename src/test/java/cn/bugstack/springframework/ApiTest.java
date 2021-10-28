package cn.bugstack.springframework;

import cn.bugstack.springframework.bean.UserDao;
import cn.bugstack.springframework.bean.UserService;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;


public class ApiTest {
    @Test
    public void test_BeanFactory() {
        //初始化
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //注册UserDao
        beanFactory.beanDefinitionRegistry("userDao", new BeanDefinition(UserDao.class));

        //UserService 注入属性设置
        Properties properties = new Properties();
        properties.addPropertyValue("signal", "1");
        properties.addPropertyValue("userDao", beanFactory.getBean("userDao"));

        //UserService 注入 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, properties);

        //注册Bean
        beanFactory.beanDefinitionRegistry("userService", beanDefinition);

        //获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
