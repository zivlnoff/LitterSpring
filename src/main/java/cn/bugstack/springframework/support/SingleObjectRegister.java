package cn.bugstack.springframework.support;

import java.util.Objects;

public interface SingleObjectRegister {
    void singleObjectRegister(String name, Object object);
    Object getBean(String name);
}
