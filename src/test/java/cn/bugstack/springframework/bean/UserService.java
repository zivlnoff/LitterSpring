package cn.bugstack.springframework.bean;


import org.junit.Test;

public class UserService {
    private String signal;
    private UserDao userDao;

    public UserService() {}
    public UserService(String signal){
        this.signal = signal;
    }
    public UserService(String signal, UserDao userDao) {this.signal = signal; this.userDao = userDao;}


    @Test
    public void queryUserInfo(){
        System.out.println(userDao.getStatus(signal));
    }
}
