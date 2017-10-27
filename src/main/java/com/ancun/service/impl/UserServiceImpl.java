package com.ancun.service.impl;

import com.ancun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 用户相关实现类
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月15日 17:05
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate template;

    /**
     * 校验用户是否在线
     * @param userTel  用户号码
     * @return online在线 or offline不在线
     */
    @Override
    public String checkUserTelOnline(String userTel) {
        String userStatus = "offline";
        if (template.hasKey("websocketUserInfo:" + userTel))
            userStatus = "online";
        return userStatus;
    }

    /**
     * 校验phpSessionId是否在线
     * @param userTel  用户号码
     * @param phpSessionId  登陆的sessionId
     * @return online在线 or offline不在线
     */
    @Override
    public String checkPhpSessionIdOnline(String userTel, String phpSessionId) {
        String userStatus = "offline";
        if (template.hasKey("websocketUser:" + userTel) &&
                !template.opsForValue().get("websocketUser:" + userTel).equals(phpSessionId))
            userStatus = "online";
        return userStatus;
    }

    /**
     * 清除用户在线信息
     * @param userTel  用户号码
     */
    @Override
    public void removeUserOnline(String userTel) {
        template.delete("websocketUserInfo:" + userTel);
        template.delete("websocketUser:" + userTel);
    }
}
