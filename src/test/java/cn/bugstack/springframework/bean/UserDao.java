package cn.bugstack.springframework.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("1", "开始");
        map.put("2", "暂停");
        map.put("3", "结束");
    }

    public String getStatus(String signal){
        return map.get(signal);
    }
}
