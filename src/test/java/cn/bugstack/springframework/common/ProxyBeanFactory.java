package cn.bugstack.springframework.common;

import cn.bugstack.springframework.bean.IUserDao;
import cn.bugstack.springframework.bean.UserDao;
import cn.bugstack.springframework.beans.factory.BeanFactory;
import cn.bugstack.springframework.beans.factory.FactoryBean;
import org.hamcrest.Factory;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.HashMap;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    IUserDao userDao = new UserDao();

    public ProxyBeanFactory() {
    }

    @Override
    public IUserDao getObject(){
        return (IUserDao) Proxy.newProxyInstance(getClassLoader(), new Class<?>[] {IUserDao.class}, (proxy, method, args) -> {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("10001", "小傅哥");
            hashMap.put("10002", "八杯水");
            hashMap.put("10003", "阿毛");

            //toString()没有被代理吗？
            System.out.println("UserDao的" + method.getName()+"方法被代理了");
            Object object = method.invoke(userDao, args);

            return object;
        });
    }

    @Override
    public ClassLoader getClassLoader() {
        return getType().getClassLoader();
    }

    @Override
    public Class<?> getType(){
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
