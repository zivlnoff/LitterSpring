package cn.bugstack.springframework.beans.resolver.config;

import cn.bugstack.springframework.utils.ClassUtils;
import com.sun.tools.javac.util.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
    private final String path;
    private ClassLoader classLoader;

    //构造函数没返回值，，名字要和类相同，看来是累了😭
    public ClassPathResource(String path){
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader){
        Assert.checkNonNull(path, "path must not be null");
        this.path = path;
        this.classLoader = classLoader != null? classLoader: ClassUtils.getDefaultClassLoader();
    }


    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if(is == null){
            throw new FileNotFoundException(this.path + "does not exist.");
        }
        return is;
    }
}
