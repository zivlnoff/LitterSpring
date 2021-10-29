package cn.bugstack.springframework;

import cn.bugstack.springframework.bean.UserDao;
import cn.bugstack.springframework.bean.UserService;
import cn.bugstack.springframework.beans.BeanReference;
import cn.bugstack.springframework.beans.Properties;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bugstack.springframework.beans.interaction.Center;
import cn.bugstack.springframework.beans.resolver.config.ClassPathResource;
import cn.bugstack.springframework.beans.resolver.config.Resource;
import cn.bugstack.springframework.beans.resolver.service.DefaultResourceLoader;
import cn.bugstack.springframework.beans.resolver.service.ResourceLoader;
import cn.bugstack.springframework.beans.resolver.service.XmlBeanDefinitionReader;
import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ApiTest {
    private Center center;

    @Before
    public void initCenter(){
        center = new Center(new DefaultListableBeanFactory(), new XmlBeanDefinitionReader(new DefaultResourceLoader()));
    }

    @Test
    public void test_BeanFactory() {
        //初始化
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //注册UserDao
        beanFactory.beanDefinitionRegistry("userDao", new BeanDefinition(UserDao.class));

        //UserService 注入属性设置
        Properties properties = new Properties();
        properties.addPropertyValue("signal", "1");
        properties.addPropertyValue("userDao", new BeanReference("userDao"));

        //UserService 注入 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, properties);

        //注册Bean
        beanFactory.beanDefinitionRegistry("userService", beanDefinition);

        //获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");

        //分两步做是为了更好的测试，测试不会依赖具体类
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    //单独用来测试resourceLoader
    private ResourceLoader resourceLoader;

    @Before
    public void initResourceLoader(){
        resourceLoader = new DefaultResourceLoader();
    }


    //这里会涉及到查询order
    @Test
    public void test_classpath() throws IOException {
        Resource classpathResource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = classpathResource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException {
        Resource fileResource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = fileResource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.printf(content);
    }

    @Test
    public void test_url() throws IOException {
        Resource urlResource = resourceLoader.getResource("https://raw.githubusercontent.com/fuzhengwei/small-spring/main/small-spring-step-05/src/test/resources/important.properties");
        InputStream inputStream = urlResource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.printf(content);
    }

    @Test
    public void test_xml() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        center.loadResource("classpath:spring.xml");

        Class clazz = DefaultListableBeanFactory.class;
        Method getBean = clazz.getMethod("getBean", String.class);

        BeanDefinitionRegistry beanFactory = center.getBeanFactory();
        UserService userService = (UserService) getBean.invoke(beanFactory, "userService");

        //check
        String result = userService.queryUserInfo();
        System.out.println(result);
    }
}
