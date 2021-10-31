package cn.bugstack.springframework.bean;


import cn.bugstack.springframework.beans.context.ApplicationContext;
import cn.bugstack.springframework.beans.factory.*;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class UserService implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;
    private ClassLoader classLoader;
    private String beanName;

    @Override
    public void destroy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("执行: UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("执行: UserService.afterPropertiesSet");
    }

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanClassLoaderAware(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
