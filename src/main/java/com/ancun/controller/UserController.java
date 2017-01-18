package com.ancun.controller;

import com.ancun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关操作Controller
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月15日 16:29
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查用户是否在线
     * @param userTel  用户号码
     * @return
     */
    @RequestMapping(value = "/checkUserTelOnline/{userTel}", method = RequestMethod.POST)
    public @ResponseBody String checkUserTelOnline(@PathVariable String userTel) {
        return userService.checkUserTelOnline(userTel);
    }

    /**
     * 清除用户在线信息
     * @param userTel
     */
    @RequestMapping(value = "/removeUserOnline/{userTel}", method = RequestMethod.POST)
    public void removeUserOnline(@PathVariable String userTel) {
        userService.removeUserOnline(userTel);
    }
}
