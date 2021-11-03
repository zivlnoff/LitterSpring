package cn.bugstack.springframework.beans.factory.support;

import cn.bugstack.springframework.beans.BeansException;
import cn.bugstack.springframework.beans.factory.DisposableBean;
import cn.bugstack.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonObject = new ConcurrentHashMap<>();

    private final Map<String, DisposableBeanAdapter> disposableBeans = new HashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        return singletonObject.get(beanName);
    }

    public void addSingleton(String name, Object singleObject){
        singletonObject.put(name, singleObject);
    }

//    public <T> Map<String, T> getBeanObjectsOfType(Class<T> type){
//        Map<String, T> beanObjectsOfTypeT = new HashMap<>();
//        for(Map.Entry<String, Object> entry: singletonObject.entrySet()){
//            String beanName = entry.getKey();
//            Object beanObject = entry.getValue();
//            if(beanObject.getClass() == type){
//                beanObjectsOfTypeT.put(beanName, (T) beanObject);
//            }
//        }
//        return beanObjectsOfTypeT;
//    }

    protected void registerDisposableBean(String beanName, DisposableBeanAdapter disposableBeanAdapter){
        disposableBeans.put(beanName, disposableBeanAdapter);
    }

    public void destroySingletons(){
        System.out.println("执行: destroySingletons");;
        for(DisposableBeanAdapter disposableBeanAdapter: disposableBeans.values()){
            try {
                disposableBeanAdapter.destroy();
            }
            catch (Exception e){
                throw new BeansException("Destroy method on bean threw an exception");
            }
        }
    }
}
