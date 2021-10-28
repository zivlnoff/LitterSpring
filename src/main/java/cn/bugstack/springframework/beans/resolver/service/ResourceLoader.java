package cn.bugstack.springframework.beans.resolver.service;

import cn.bugstack.springframework.beans.resolver.config.Resource;

public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";
    Resource getResource(String path);
}
