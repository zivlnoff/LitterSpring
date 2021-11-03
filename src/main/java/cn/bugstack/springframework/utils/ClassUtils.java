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

    /**
     * Check whether the specified class is a CGLIB-generated class.
     * @param clazz the class to check
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * Check whether the specified class name is a CGLIB-generated class.
     * @param className the class name to check
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }
}
