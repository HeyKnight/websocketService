package com.ancun.service.impl;

import com.ancun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 用户实现类
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月15日 17:05
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public String checkUserTelOnline(String userTel) {
        ValueOperations<String, String> ops = template.opsForValue();

        String userStatus = "offline";
        if (! template.hasKey("websocketUserInfo:" + userTel))
            template.opsForSet().add("websocketUserInfo:" + userTel, userTel);
        else
            userStatus = "online";

        return userStatus;
    }

    @Override
    public void removeUserOnline(String userTel) {
        template.delete("websocketUserInfo:" + userTel);
    }
}
