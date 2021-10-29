package cn.bugstack.springframework.bean;


import org.junit.Test;

public class UserService {
    private String signal;
    private UserDao userDao;

    public UserService() {}
    public UserService(String signal){
        this.signal = signal;
    }
    private UserService(String signal, UserDao userDao) {this.signal = signal; this.userDao = userDao;}


    @Test
    public String queryUserInfo(){
        return userDao.getStatus(signal);
    }
}
