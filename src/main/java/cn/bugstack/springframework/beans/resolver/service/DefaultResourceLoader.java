package cn.bugstack.springframework.beans.resolver.service;

import cn.bugstack.springframework.beans.resolver.config.ClassPathResource;
import cn.bugstack.springframework.beans.resolver.config.FileSystemResource;
import cn.bugstack.springframework.beans.resolver.config.Resource;
import cn.bugstack.springframework.beans.resolver.config.UrlResource;
import com.sun.tools.javac.util.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {
        Assert.checkNonNull(location, "location must be not null");
        if(location.startsWith(CLASSPATH_URL_PREFIX)){
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else{
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
