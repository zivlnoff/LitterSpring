package cn.bugstack.springframework.annotation;

import cn.bugstack.springframework.beans.context.support.DefaultRefreshableApplicationContext;
import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bugstack.springframework.beans.resolver.service.DefaultResourceLoader;
import cn.bugstack.springframework.beans.service.Center;
import cn.bugstack.springframework.beans.service.support.XmlBeanDefinitionReader;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_scan() {
        Center center = new Center(new DefaultRefreshableApplicationContext(new DefaultListableBeanFactory()), new XmlBeanDefinitionReader(new DefaultResourceLoader()));
        center.loadResource("classpath:springAnnotation.xml");
        IUserService userService = center.getRefreshableApplicationContext().getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}
