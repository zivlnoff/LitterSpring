package cn.bugstack.springframework.bean;


import org.junit.Test;

public class UserService {
    private String msg;

    public UserService(String msg){
        this.msg = msg;
    }

    @Test
    public void queryUserInfo(){
        System.out.println(msg);
    }
}
