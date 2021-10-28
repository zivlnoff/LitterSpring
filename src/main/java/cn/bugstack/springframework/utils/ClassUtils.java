package cn.bugstack.springframework.utils;

public class ClassUtils {
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader classLoader = null;
        //这里是考虑到自定义ClassLoader的异常
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        }

        //待配置
        catch (Throwable ex){
//            logger.debug("Cannot access thread context ClassLoader - falling back to system class loader", ex);
        }

        if(classLoader == null){
            classLoader = ClassUtils.class.getClassLoader();
        }

        return classLoader;
    }
}
