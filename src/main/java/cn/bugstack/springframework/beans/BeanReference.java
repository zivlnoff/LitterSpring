package cn.bugstack.springframework.beans;

public class BeanReference {
    private String name;

    public BeanReference(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
