package cn.bugstack.springframework.beans.resolver.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//很容易想到抽象工厂实现资源加载
public interface Resource {
    //type
    //size
    //name
    //encode
    //...
    InputStream getInputStream() throws IOException;
}
