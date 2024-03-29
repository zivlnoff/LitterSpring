package cn.bugstack.springframework.beans;

import cn.bugstack.springframework.beans.bean.UserService;
import cn.bugstack.springframework.beans.context.support.AbstractRefreshableApplicationContext;
import cn.bugstack.springframework.beans.context.support.DefaultRefreshableApplicationContext;
import cn.bugstack.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bugstack.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bugstack.springframework.beans.service.Center;
import cn.bugstack.springframework.beans.service.support.BeanDefinitionReader;
import cn.bugstack.springframework.beans.resolver.service.DefaultResourceLoader;
import cn.bugstack.springframework.beans.service.support.XmlBeanDefinitionReader;
import cn.bugstack.springframework.beans.common.MyBeanFactoryPostProcessor;
import cn.bugstack.springframework.beans.common.MyBeanPostProcessor;
import cn.bugstack.springframework.event.CustomEvent;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;


public class ApiTest {
    private Center center;

    private DefaultListableBeanFactory beanFactory;
    private BeanDefinitionReader beanDefinitionReader;

    @Before
    public void initCenter(){
        this.beanFactory = new DefaultListableBeanFactory();
        this.beanDefinitionReader = new XmlBeanDefinitionReader(new DefaultResourceLoader());
        this.center = new Center(new DefaultRefreshableApplicationContext(beanFactory), beanDefinitionReader);
    }

    //bean干预测试
    //这个接口不向外暴露，所以会有矛盾嗯嗯。
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() throws NoSuchFieldException, IllegalAccessException {

        // 2. 读取配置文件&注册Bean
        center.loadResource("classpath:spring.xml");

        //反射获取beanFactory
        Field field = center.getClass().getDeclaredField("refreshableApplicationContext");
        field.setAccessible(true);
        AbstractRefreshableApplicationContext abstractRefreshableApplicationContext = (AbstractRefreshableApplicationContext) field.get(center);
        ConfigurableListableBeanFactory beanFactory = abstractRefreshableApplicationContext.getBeanFactory();

        // 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean实例化之后，修改 Bean 属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // 5. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_xml1(){
        center.loadResource("classpath:spring.xml");
        UserService userService = center.getRefreshableApplicationContext().getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_xml2() {
        // 1.初始化 BeanFactory
        center.loadResource("classpath:springPostProcessor.xml");

        // 2. 获取Bean对象调用方法
        UserService userService = center.getRefreshableApplicationContext().getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_xml3() {
        // 1.初始化 BeanFactory
        center.loadResource("classpath:springInitDestroy.xml");
        center.getRefreshableApplicationContext().registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService userService = center.getRefreshableApplicationContext().getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_xml4() {
        // 1.初始化 BeanFactory
        center.loadResource("classpath:springInitDestroy.xml");
        center.getRefreshableApplicationContext().registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserService userService = center.getRefreshableApplicationContext().getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

        System.out.println("ApplicationContextAware："+userService.getApplicationContext());
        System.out.println("BeanFactoryAware：" + userService.getBeanFactory());
        System.out.println("BeanNameAware: " + userService.getBeanName());
        System.out.println("ClassLoaderAware: " + userService.getClassLoader());
    }

    @Test
    public void test_factory_bean() {
        // 1.初始化 BeanFactory
        center.loadResource("classpath:springFactoryBean.xml");
        center.getRefreshableApplicationContext().registerShutdownHook();

        // 2. 调用代理方法
        UserService userService = center.getRefreshableApplicationContext().getBean("userService", UserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void test_event() {
        // 1.初始化 BeanFactory
        center.loadResource("classpath:springEventListen.xml");

        AbstractRefreshableApplicationContext applicationContext = center.getRefreshableApplicationContext();
        // 2.发布事件
        applicationContext.eventOccur(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }

    @Test
    public void test_hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("close！")));
    }
}
